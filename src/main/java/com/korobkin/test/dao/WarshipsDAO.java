package com.korobkin.test.dao;

import com.korobkin.test.dao.interfacesGenericDAO.GenericDAO;
import com.korobkin.test.models.Battles;
import com.korobkin.test.models.Warships;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class WarshipsDAO  implements GenericDAO<Warships, String> {

    private final JdbcTemplate jdbcTemplate;

    public WarshipsDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Warships> index() {
        return   jdbcTemplate.query("SELECT * FROM WARSHIPS", new BeanPropertyRowMapper<>(Warships.class));
    }

    @Override
    public Warships show(String name) {
        return jdbcTemplate.query("SELECT * FROM WARSHIPS WHERE NAME=?", new Object[]{name}, new BeanPropertyRowMapper<>(Warships.class))
                .stream().findAny().orElse(null);
    }

    @Override
    public void save(Warships warships) {
        jdbcTemplate.update("INSERT INTO WARSHIPS(NAME, CLASS_WARSHIPS, COMMISSION_DATE,  DECOMMISSION_DATE ) VALUES(?, ?, ?, ?)", warships.getName(),
                warships.getClass_warships(),warships.getCommissionDate(), warships.getDecommissionDate());
    }

    @Override
    public void update(String name, Warships updateWarships) {
        jdbcTemplate.update("UPDATE WARSHIPS SET NAME=?, CLASS_WARSHIPS=?, COMMISSION_DATE=?, DECOMMISSION_DATE=? WHERE NAME=?",
                updateWarships.getName(), updateWarships.getClass_warships(), updateWarships.getCommissionDate(), updateWarships.getDecommissionDate(), name);
    }

    @Override
    public void delete(String name) {
        jdbcTemplate.update("DELETE FROM WARSHIPS WHERE NAME=?", name);
    }

    // Для валидации уникальности названия сражения
    public Optional<Warships> getWarshipsByName(String name){
        return jdbcTemplate.query("SELECT * FROM WARSHIPS WHERE NAME=?", new Object[]{name},
                new BeanPropertyRowMapper<>(Warships.class)).stream().findAny();
    }
}
