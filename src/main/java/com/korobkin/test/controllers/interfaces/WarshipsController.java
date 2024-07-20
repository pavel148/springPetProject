package com.korobkin.test.controllers.interfaces;

import com.korobkin.test.models.Warships;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/warships")
public interface WarshipsController {

    @GetMapping()
    String index(Model model);

    @GetMapping("/{name}")
    String show(@PathVariable("name") String name, Model model);

    @GetMapping("/new")
    String newWarship(@ModelAttribute("warships") Warships warships);

    @PostMapping
    String create(@ModelAttribute("warships") @Valid Warships warships, BindingResult result);

    @GetMapping("/{name}/edit")
    String edit(Model model, @PathVariable("name") String name);

    @PatchMapping("/{name}")
    String update(@ModelAttribute("warships") @Valid Warships warships, BindingResult result, @PathVariable("name") String name);

    @DeleteMapping("/{name}")
    String delete(@PathVariable("name") String name);
}