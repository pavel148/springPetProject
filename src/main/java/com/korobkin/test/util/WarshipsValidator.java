package com.korobkin.test.util;

import com.korobkin.test.dao.WarshipsDAO;
import com.korobkin.test.models.Warships;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@Component
public class WarshipsValidator implements Validator {

    private final WarshipsDAO warshipsDAO;
    @Autowired
    public WarshipsValidator(WarshipsDAO warshipsDAO) {
        this.warshipsDAO = warshipsDAO;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return Warships.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Warships warships = (Warships) target;
        LocalDate commissionDate = warships.getCommissionDate();
        LocalDate decommissionDate = warships.getDecommissionDate();

        if (commissionDate != null && decommissionDate != null) {
            if (commissionDate.isAfter(decommissionDate)) {
                errors.rejectValue("commissionDate", "", "Дата ввода в эксплуатацию не может быть позже даты вывода из эксплуатации.");
            }
            if (commissionDate.isEqual(decommissionDate)) {
                errors.rejectValue("commissionDate", "", "Дата ввода в эксплуатацию и дата вывода из эксплуатации не могут быть в один и тот же день.");
            }
        }

        if (warshipsDAO.getWarshipsByName(warships.getName()).isPresent())
            errors.rejectValue("name", "", "Корабль с таким именем уже существует");

    }
}
