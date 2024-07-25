package com.korobkin.test.controllers;

import com.korobkin.test.controllers.interfaces.WarshipsController;
import com.korobkin.test.dao.WarshipsDAO;
import com.korobkin.test.models.Warships;
import com.korobkin.test.util.WarshipsValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/warships")
public class WarshipsControllerImpl implements WarshipsController {

    private final WarshipsDAO warshipsDAO;
    private final WarshipsValidator warshipsValidator;

    @Autowired
    public WarshipsControllerImpl(WarshipsDAO warshipsDAO, WarshipsValidator warshipsValidator, JdbcTemplate jdbcTemplate) {
        this.warshipsDAO = warshipsDAO;
        this.warshipsValidator = warshipsValidator;
    }

    //@Override
    @GetMapping
    public String index(Model model) {
        model.addAttribute("warships", warshipsDAO.index());
        return "page/warships/index";
    }

    @Override
    @GetMapping("/{name}")
    public String show(@PathVariable("name")  String name, Model model) {
        model.addAttribute("warships", warshipsDAO.show(name));
        return "page/warships/show";
    }

    @Override
    @GetMapping("/new")
    public String newWarship(@ModelAttribute("warships")  Warships warships) {
        return "page/warships/new";
    }


    @Override
    public String create(@ModelAttribute("warships") @Valid Warships warship, BindingResult result) {
        warshipsValidator.validate(warship, result);

        if (result.hasErrors())
            return "page/warships/new";

        warshipsDAO.save(warship);
        return "redirect:/warships";
    }

    @Override
    public String edit(Model model, @PathVariable("name") String name) {
        model.addAttribute("warships", warshipsDAO.show(name));
        return "page/warships/edit";
    }

    @Override
    public String update(@ModelAttribute("warships") @Valid Warships warship, BindingResult result, @PathVariable("name")  String name) {
        warshipsValidator.validate(warship, result, name);
        if (result.hasErrors())
            return "page/warships/edit";

        warshipsDAO.update(name, warship);
        return "redirect:/warships";
    }

    @Override
    public String delete(@PathVariable("name") String name) {
        warshipsDAO.delete(name);
        return "redirect:/warships";
    }
}
