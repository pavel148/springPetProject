package com.korobkin.test.util;

import com.korobkin.test.dao.BattleMembersDAO;
import com.korobkin.test.dao.BattlesDAO;
import com.korobkin.test.dao.WarshipsDAO;
import com.korobkin.test.models.BattleMembers;
import com.korobkin.test.models.Warships;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@Component
public class BattleMembersValidator implements Validator {
    private final BattlesDAO battlesDAO;
    private final BattleMembersDAO battleMembersDAO;
    private final WarshipsDAO warshipsDAO;

    public BattleMembersValidator(BattlesDAO battlesDAO, BattleMembersDAO battleMembersDAO, WarshipsDAO warshipsDAO) {
        this.battlesDAO = battlesDAO;
        this.battleMembersDAO = battleMembersDAO;
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

        if (ship == null)  return;
        LocalDate commissionDate = ship.getCommissionDate();
        LocalDate decommissionDate = ship.getDecommissionDate();
        LocalDate battleDate = battlesDAO.getBattleDateByName(battleMembers.getBattle_name());

        validateCommissionDate(errors, commissionDate, battleDate);
        validateDecommissionDate(errors, decommissionDate, battleDate);
        validateSinkingDate(errors, battleMembers, decommissionDate, battleDate);
        validateUniqueShipInBattle(errors, battleMembers);
    }

    private void validateCommissionDate(Errors errors, LocalDate commissionDate, LocalDate battleDate) {
        if (commissionDate != null && battleDate.isBefore(commissionDate)) {
            errors.rejectValue("battle_name", "", "Корабль не может принимать участие в бою до даты ввода в эксплуатацию.");
        }
    }

    private void validateDecommissionDate(Errors errors, LocalDate decommissionDate, LocalDate battleDate) {
        if (decommissionDate != null && battleDate.isAfter(decommissionDate)) {
            errors.rejectValue("battle_name", "", "Корабль не может принимать участие в бою после даты списания.");
        }
    }

    private void validateSinkingDate(Errors errors, BattleMembers battleMembers, LocalDate decommissionDate, LocalDate battleDate) {
        if ("затонувший".equals(battleMembers.getResult()) && decommissionDate != null && !decommissionDate.equals(battleDate)) {
            errors.rejectValue("result", "", "Обычно, если корабль затонул, дата его вывода из эксплуатации и дата сражения, в котором он затонул, должны совпадать.");
        }
    }

    private void validateUniqueShipInBattle(Errors errors, BattleMembers battleMembers) {
        BattleMembers existingBattleMember = battleMembersDAO.checkUniqueShip_nameInBattle(battleMembers.getBattle_name(), battleMembers.getShip_name());
        if (existingBattleMember != null && existingBattleMember.getBattle_members_id() != battleMembers.getBattle_members_id()) {
            String errorMessage = String.format("Корабль '%s' уже добавлен в битву '%s'", battleMembers.getShip_name(), battleMembers.getBattle_name());
            errors.rejectValue("result", "", errorMessage);
        }
    }
}
