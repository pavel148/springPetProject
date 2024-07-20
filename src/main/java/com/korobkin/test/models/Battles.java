package com.korobkin.test.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;


public class Battles {

    //конструктор по умолчанию нужен для Spring
    public Battles(){

    }

    public Battles(String battle_name, LocalDate battle_date){
        this.battle_name = battle_name;
        this.battle_date = battle_date;
    }
    @NotEmpty(message = "Название сражение не должно быть пустым")
    @Size(min=2, max=50, message = "Название сражение должно быть от 2 до 100 символов длиной")
    private String battle_name;
    @NotEmpty(message = "Дата не должна быть пустой")
    private LocalDate battle_date;

    public String getBattle_name() {
        return battle_name;
    }

    public void setBattle_name(String battle_name) {
        this.battle_name = battle_name;
    }

    public LocalDate getBattle_date() {
        return battle_date;
    }

    public void setBattle_date(LocalDate battle_date) {
        this.battle_date = battle_date;
    }
}
