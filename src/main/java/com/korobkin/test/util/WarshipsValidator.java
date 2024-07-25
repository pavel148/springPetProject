package com.korobkin.test.util;

import com.korobkin.test.dao.WarshipsDAO;
import com.korobkin.test.models.Warships;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

@Component
public class WarshipsValidator implements Validator {

    private final WarshipsDAO warshipsDAO;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

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
        validateDates(warships, errors);
        validateUniqueName(warships, errors);
    }

    public void validate(Object target, Errors errors, String pathName) {
        Warships warships = (Warships) target;
        validateDates(warships, errors);
        validateUniqueName(warships, errors, pathName);
    }

    private void validateDates(Warships warships, Errors errors) {
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
    }

    private void validateUniqueName(Warships warships, Errors errors) {
        if (warshipsDAO.getWarshipsByName(warships.getName()).isPresent()) {
            errors.rejectValue("name", "", "Корабль с таким именем уже существует");
        }
    }

    private void validateUniqueName(Warships warships, Errors errors, String pathName) {
        if (!Objects.equals(warships.getName(), pathName) && warshipsDAO.getWarshipsByName(warships.getName()).isPresent()) {
            errors.rejectValue("name", "", "Корабль с таким именем уже существует");
        }
    }


}
