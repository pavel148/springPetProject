package com.korobkin.test.controllers;

import com.korobkin.test.controllers.interfaces.BattleMembersController;
import com.korobkin.test.dao.BattleMembersDAO;
import com.korobkin.test.dao.BattlesDAO;
import com.korobkin.test.dao.WarshipsDAO;
import com.korobkin.test.models.BattleMembers;
import com.korobkin.test.models.Battles;
import com.korobkin.test.models.Warships;
import com.korobkin.test.util.BattleMembersValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class BattleMembersControllerImpl implements BattleMembersController {

    private final BattleMembersDAO battleMembersDAO;
    private final BattlesDAO battlesDAO;
    private final WarshipsDAO warshipsDAO;
    private final BattleMembersValidator battleMembersValidator;

    @Autowired
    public BattleMembersControllerImpl(BattleMembersDAO battleMembersDAO, BattlesDAO battlesDAO, WarshipsDAO warshipsDAO, BattleMembersValidator battleMembersValidator) {
        this.battleMembersDAO = battleMembersDAO;
        this.battlesDAO = battlesDAO;
        this.warshipsDAO = warshipsDAO;
        this.battleMembersValidator = battleMembersValidator;
    }

    @Override
    @GetMapping
    public String index(Model model) {
        model.addAttribute("battleMembers", battleMembersDAO.index());
        return "page/battle-members/index";
    }

    @Override
    @GetMapping("/{battle_members_id}")
    public String show(@PathVariable("battle_members_id") int id, Model model) {
        model.addAttribute("battleMembers", battleMembersDAO.show(id));
        return "page/battle-members/show";
    }

    @Override
    @GetMapping("/new")
    public String newBattleMembers(Model model) {
        model.addAttribute("battleMembers", new BattleMembers());
        model.addAttribute("battles", battlesDAO.index());
        model.addAttribute("warships", warshipsDAO.index());
        return "page/battle-members/new";
    }

    @Override
    @PostMapping
    public String create(@ModelAttribute("battleMembers") @Valid BattleMembers battleMember, BindingResult result, Model model) {
        battleMembersValidator.validate(battleMember, result);

        if (result.hasErrors()) {
            model.addAttribute("battles", battlesDAO.index());
            model.addAttribute("warships", warshipsDAO.index());
            return "page/battle-members/new";
        }

        battleMembersDAO.save(battleMember);
        return "redirect:/battle-members";
    }

    @Override
    @GetMapping("/{battle_members_id}/edit")
    public String edit(@PathVariable("battle_members_id") int id, Model model) {
        model.addAttribute("battleMembers", battleMembersDAO.show(id));
        model.addAttribute("battles", battlesDAO.index());
        model.addAttribute("warships", warshipsDAO.index());
        return "page/battle-members/edit";
    }

    @Override
    @PatchMapping("/{battle_members_id}")
    public String update(@ModelAttribute("battleMembers") @Valid BattleMembers battleMember, BindingResult result, @PathVariable("battle_members_id") int id, Model model) {
        battleMembersValidator.validate(battleMember, result);

        if (result.hasErrors()) {
            model.addAttribute("battles", battlesDAO.index());
            model.addAttribute("warships", warshipsDAO.index());
            return "page/battle-members/edit";
        }

        battleMembersDAO.update(id, battleMember);
        return "redirect:/battle-members";
    }

    @Override
    @DeleteMapping("/{battle_members_id}")
    public String delete(@PathVariable("battle_members_id") int id) {
        battleMembersDAO.delete(id);
        return "redirect:/battle-members";
    }


}