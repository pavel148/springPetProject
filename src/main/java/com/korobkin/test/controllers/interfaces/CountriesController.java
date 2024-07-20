package com.korobkin.test.controllers.interfaces;

import com.korobkin.test.models.Countries;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/countries")
public interface CountriesController {
    @GetMapping()
    String index(Model model);

    @GetMapping("/{name}")
    String show(@PathVariable("name") String name, Model model);

    @GetMapping("/new")
    String newCountry(@ModelAttribute("countries") Countries country);

    @PostMapping
    String create(@ModelAttribute("countries") @Valid Countries country, BindingResult result);

    @GetMapping("/{name}/edit")
    String edit(Model model, @PathVariable("name") String name);

    @PatchMapping("/{name}")
    String update(@ModelAttribute("countries") @Valid Countries country, BindingResult result, @PathVariable("name") String name);

    @DeleteMapping("/{name}")
    String delete(@PathVariable("name") String name);
}
