package com.korobkin.test.controllers.interfaces;

import com.korobkin.test.models.BattleMembers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
@RequestMapping("/battle-members")
public interface BattleMembersController {

    @GetMapping
    String index(Model model);

    @GetMapping("/{battle_members_id}")
    String show(@PathVariable("battle_members_id") int id, Model model);

    @GetMapping("/new")
    String newBattleMembers(Model model);

    @PostMapping
    String create(@ModelAttribute("battleMembers") @Valid BattleMembers battleMember, BindingResult result, Model model);

    @GetMapping("/{battle_members_id}/edit")
    String edit(@PathVariable("battle_members_id") int id, Model model);

    @PatchMapping("/{battle_members_id}")
    String update(@ModelAttribute("battleMembers") @Valid BattleMembers battleMember, BindingResult result, @PathVariable("battle_members_id") int id, Model model);

    @DeleteMapping("/{battle_members_id}")
    String delete(@PathVariable("battle_members_id") int id);
}
