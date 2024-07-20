package com.korobkin.test.controllers.interfaces;

import com.korobkin.test.models.Battles;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/battles")
public interface BattlesController {
    @GetMapping()
    String index(Model model);

    @GetMapping("/{battle_name}")
    String show(@PathVariable("battle_name") String name, Model model);

    @GetMapping("/new")
    String newBattle(@ModelAttribute("battles") Battles battle);

    @PostMapping
    String create(@ModelAttribute("battles") @Valid Battles battle, BindingResult result);

    @GetMapping("/{battle_name}/edit")
    String edit(Model model, @PathVariable("battle_name") String name);

    @PatchMapping("/{battle_name}")
    String update(@ModelAttribute("battles") @Valid Battles battle, BindingResult result, @PathVariable("battle_name") String name);

    @DeleteMapping("/{battle_name}")
    String delete(@PathVariable("battle_name") String name);
}
