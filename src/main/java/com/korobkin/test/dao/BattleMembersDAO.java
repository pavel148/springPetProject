package com.korobkin.test.dao;

import com.korobkin.test.dao.interfacesGenericDAO.GenericDAO;
import com.korobkin.test.models.BattleMembers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BattleMembersDAO implements GenericDAO<BattleMembers, Integer>{

    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public BattleMembersDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<BattleMembers> index() {
        return jdbcTemplate.query("SELECT * FROM BATTLE_MEMBERS", new BeanPropertyRowMapper<>(BattleMembers.class));
    }

    @Override
    public BattleMembers show(Integer id) {
        return jdbcTemplate.query("SELECT * FROM BATTLE_MEMBERS WHERE BATTLE_MEMBERS_ID=?", new Object[]{id}, new BeanPropertyRowMapper<>(BattleMembers.class))
                .stream().findAny().orElse(null);
    }

    @Override
    public void save(BattleMembers battleMembers) {
        jdbcTemplate.update("INSERT INTO BATTLE_MEMBERS(BATTLE_NAME, SHIP_NAME, RESULT) VALUES(?, ?, ?)", battleMembers.getBattle_name(),
                battleMembers.getShip_name(), battleMembers.getResult());
    }



    @Override
    public void update(Integer id, BattleMembers  updateBattleMembers) {
        jdbcTemplate.update("UPDATE BATTLE_MEMBERS SET BATTLE_NAME=?, SHIP_NAME=?, RESULT=? WHERE BATTLE_MEMBERS_ID=?", updateBattleMembers.getBattle_name(),
                updateBattleMembers.getShip_name(), updateBattleMembers.getResult(), id);
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update("DELETE FROM BATTLE_MEMBERS WHERE BATTLE_MEMBERS_ID=?", id);
    }


}
