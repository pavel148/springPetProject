package com.korobkin.test.controllers;


import com.korobkin.test.controllers.interfaces.CountriesController;
import com.korobkin.test.dao.CountriesDAO;
import com.korobkin.test.models.Countries;
import com.korobkin.test.util.CountriesValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;


@Controller
public class CountriesControllerImpl implements CountriesController {

    private final CountriesDAO countriesDAO;
    private final CountriesValidator countriesValidator;

    @Autowired
    public CountriesControllerImpl(CountriesDAO countriesDAO, CountriesValidator countriesValidator) {
        this.countriesDAO = countriesDAO;
        this.countriesValidator = countriesValidator;
    }


    @Override
    public String index(Model model) {
        model.addAttribute("countries", countriesDAO.index());
        return "page/countries/index";
    }

    @Override
    public String show(@PathVariable("name") String name, Model model) {
        model.addAttribute("countries", countriesDAO.show(name));
        return "page/countries/show";
    }

    @Override
    public String newCountry(@ModelAttribute("countries") Countries country) {
        return "page/countries/new";
    }

    @Override
    public String create(@ModelAttribute("countries") @Valid Countries country, BindingResult result) {
        countriesValidator.validate(country, result);

        if (result.hasErrors())
            return "page/countries/new";

        countriesDAO.save(country);
        return "redirect:/countries";
    }

    @Override
    public String edit(Model model, @PathVariable("name") String name) {
        model.addAttribute("countries", countriesDAO.show(name));
        return "page/countries/edit";
    }

    @Override
    public String update(@ModelAttribute("countries") @Valid Countries country, BindingResult result, @PathVariable("name") String name) {
        if (result.hasErrors())
            return "page/countries/edit";

        countriesDAO.update(name, country);
        return "redirect:/countries";
    }

    @Override
    public String delete(@PathVariable("name") String name) {
        countriesDAO.delete(name);
        return "redirect:/countries";
    }
}
