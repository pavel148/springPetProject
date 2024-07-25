package com.korobkin.test.util;

import com.korobkin.test.dao.BattlesDAO;
import com.korobkin.test.models.Battles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

@Component
public class BattlesValidator implements Validator {

    private final BattlesDAO battlesDAO;

    @Autowired
    public BattlesValidator(BattlesDAO battlesDAO) {
        this.battlesDAO = battlesDAO;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return Battles.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Battles battles=(Battles) target;
        if (battlesDAO.getBattlesByName(battles.getBattle_name()).isPresent())
            errors.rejectValue("battle_name", "", "Такое сражение уже существует");

    }

    public void validate(Object target, Errors errors, String pathBattle_name) {
        Battles battles=(Battles) target;
        if (!Objects.equals(battles.getBattle_name(), pathBattle_name) && battlesDAO.getBattlesByName(battles.getBattle_name()).isPresent()) {
            errors.rejectValue("battle_name", "", "Такое сражение уже существует");
        }
    }



}
