package com.korobkin.test.controllers;

import com.korobkin.test.controllers.interfaces.BattlesController;
import com.korobkin.test.dao.BattlesDAO;
import com.korobkin.test.models.Battles;
import com.korobkin.test.util.BattlesValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;

@Controller
public class BattlesControllerImpl implements BattlesController {

    private final BattlesDAO battlesDAO;
    private final BattlesValidator battlesValidator;
    @Autowired
    public BattlesControllerImpl(BattlesDAO battlesDAO, BattlesValidator battlesValidator) {
        this.battlesDAO = battlesDAO;
        this.battlesValidator = battlesValidator;
    }

    @Override
    public String index(Model model) {
        model.addAttribute("battles", battlesDAO.index());
        return "page/battles/index";
    }

    @Override
    public String show(@PathVariable("battle_name") String battle_name, Model model) {
        model.addAttribute("battles", battlesDAO.show(battle_name));
        return "page/battles/show";
    }

    @Override
    public String newBattle(@ModelAttribute("battles") Battles battle) {
        return "page/battles/new";
    }

    @Override
    public String create(@ModelAttribute("battles") @Valid Battles battle, BindingResult result) {
        battlesValidator.validate(battle, result);

        if (result.hasErrors())
            return "page/battles/new";

        battlesDAO.save(battle);
        return "redirect:/battles";
    }

    @Override
    public String edit(Model model, @PathVariable("battle_name") String battle_name) {
        Battles battle = battlesDAO.show(battle_name);
        model.addAttribute("battles", battlesDAO.show(battle_name));
        model.addAttribute("originalBattleName", battle_name);
        return "page/battles/edit";
    }

    @Override
    public String update(@ModelAttribute("battles") @Valid Battles battle, BindingResult result,@PathVariable("battle_name") String battle_name) {
        battlesValidator.validate(battle, result, battle_name);
        if (result.hasErrors())
            return "page/battles/edit";

        battlesDAO.update(battle_name, battle);
        return "redirect:/battles";
    }



    @Override
    public String delete(@PathVariable("battle_name") String battle_name) {
        battlesDAO.delete(battle_name);
        return "redirect:/battles";
    }
}
