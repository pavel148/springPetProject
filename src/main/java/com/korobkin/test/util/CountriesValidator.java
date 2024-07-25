package com.korobkin.test.util;

import com.korobkin.test.dao.CountriesDAO;
import com.korobkin.test.models.Battles;
import com.korobkin.test.models.Countries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

@Component
public class CountriesValidator implements Validator {

    private final CountriesDAO countriesDAO;

    @Autowired
    public CountriesValidator(CountriesDAO countriesDAO) {
        this.countriesDAO = countriesDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Countries.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Countries countries=(Countries) target;
        if (countriesDAO.getCountriesByName(countries.getName()).isPresent())
            errors.rejectValue("name", "", "Страна с таким именем уже существует");

    }

    public void validate(Object target, Errors errors, String pathCountries_name) {
        Countries countries=(Countries) target;
        if (!Objects.equals(countries.getName(), pathCountries_name) && countriesDAO.getCountriesByName(countries.getName()).isPresent()) {
            errors.rejectValue("name", "", "Страна с таким именем уже существует");
            }
        }
}


