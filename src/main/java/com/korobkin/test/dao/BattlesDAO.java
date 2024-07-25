package com.korobkin.test.dao;

import com.korobkin.test.dao.interfacesGenericDAO.GenericDAO;
import com.korobkin.test.models.Battles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class BattlesDAO implements GenericDAO<Battles, String> {

    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public BattlesDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Battles> index() {
        return jdbcTemplate.query("SELECT * FROM BATTLES", new BeanPropertyRowMapper<>(Battles.class));
    }

    @Override
    public Battles show(String name) {
        return jdbcTemplate.query("SELECT * FROM BATTLES WHERE BATTLE_NAME=?", new Object[]{name},new BeanPropertyRowMapper<>(Battles.class))
                .stream().findAny().orElse(null);
    }

    @Override
    public void save(Battles battles) {
        jdbcTemplate.update("INSERT INTO BATTLES(BATTLE_NAME, BATTLE_DATE) VALUES(?, ?)", battles.getBattle_name(),
                battles.getBattle_date());
    }

    @Override
    public void update(String name, Battles battles) {
        jdbcTemplate.update("UPDATE BATTLES SET BATTLE_NAME=?, BATTLE_DATE=? WHERE BATTLE_NAME=?", battles.getBattle_name(),
                battles.getBattle_date(), name);
    }

    @Override
    public void delete(String name) {
        jdbcTemplate.update("DELETE FROM BATTLES WHERE BATTLE_NAME=?", name);
    }

    // Для валидации уникальности названия сражения
    public Optional<Battles> getBattlesByName(String name){
        return jdbcTemplate.query("SELECT * FROM BATTLES WHERE BATTLE_NAME=?", new Object[]{name},
                new BeanPropertyRowMapper<>(Battles.class)).stream().findAny();
    }

    public LocalDate getBattleDateByName(String battle_name) {
        return jdbcTemplate.queryForObject("SELECT BATTLE_DATE FROM BATTLES WHERE BATTLE_NAME=?", new Object[]{battle_name}, LocalDate.class);
    }


}
