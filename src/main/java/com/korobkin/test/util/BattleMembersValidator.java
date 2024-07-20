package com.korobkin.test.util;

import com.korobkin.test.dao.BattlesDAO;
import com.korobkin.test.dao.WarshipsDAO;
import com.korobkin.test.models.BattleMembers;
import com.korobkin.test.models.Battles;
import com.korobkin.test.models.Warships;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@Component
public class BattleMembersValidator implements Validator {
    private final BattlesDAO battlesDAO;
    private final WarshipsDAO warshipsDAO;

    public BattleMembersValidator(BattlesDAO battlesDAO, WarshipsDAO warshipsDAO) {
        this.battlesDAO = battlesDAO;
        this.warshipsDAO = warshipsDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return BattleMembers.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        BattleMembers battleMembers = (BattleMembers) target;
        Warships ship = warshipsDAO.show(battleMembers.getShip_name());

        if (ship != null) {
            LocalDate commissionDate = ship.getCommissionDate();
            LocalDate decommissionDate = ship.getDecommissionDate();
            LocalDate battleDate = battlesDAO.getBattleDateByName(battleMembers.getBattle_name());

            if (commissionDate != null && battleDate.isBefore(commissionDate)) {
                errors.rejectValue("battle_name", "", "Корабль не может принимать участие в бою до даты ввода в эксплуатацию.");
            }

            if (decommissionDate != null && battleDate.isAfter(decommissionDate)) {
                errors.rejectValue("battle_name", "", "Корабль не может принимать участие в бою после даты списания.");
            }
        }
    }
}
