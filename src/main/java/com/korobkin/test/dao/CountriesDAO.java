package com.korobkin.test.dao;

import com.korobkin.test.dao.interfacesGenericDAO.GenericDAO;
import com.korobkin.test.models.Countries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CountriesDAO implements GenericDAO<Countries,String> {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CountriesDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Countries> index() {
        return jdbcTemplate.query("SELECT * FROM COUNTRIES", new BeanPropertyRowMapper<>(Countries.class));
    }

    public Countries show(String name) {
        return jdbcTemplate.query("SELECT * FROM COUNTRIES WHERE NAME=?", new Object[]{name}, new BeanPropertyRowMapper<>(Countries.class))
                .stream().findAny().orElse(null);
    }

    public void save(Countries countries) {
        jdbcTemplate.update("INSERT INTO COUNTRIES(NAME, SIDE) VALUES(?, ?)", countries.getName(),
                countries.getSide());
    }

    public void update(String name, Countries updatedCountries) {
        jdbcTemplate.update("UPDATE COUNTRIES SET NAME=?, SIDE=? WHERE NAME=?", updatedCountries.getName(),
                updatedCountries.getSide(), name);
    }

    public void delete(String name) {
        jdbcTemplate.update("DELETE FROM COUNTRIES WHERE NAME=?", name);
    }

    // Для валидации уникальности названия страны
    public Optional<Countries> getCountriesByName(String name) {
        return jdbcTemplate.query("SELECT * FROM COUNTRIES WHERE NAME=?", new Object[]{name},
                new BeanPropertyRowMapper<>(Countries.class)).stream().findAny();
    }
}
