package com.korobkin.test.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class Warships {

    public Warships(){

    }
    public Warships(String name, String class_warships, LocalDate commissionDate, LocalDate decommissionDate) {
        this.name = name;
        this.class_warships = class_warships;
        this.commissionDate = commissionDate;
        this.decommissionDate = decommissionDate;
    }
    private String name;
    private String class_warships;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate commissionDate;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate decommissionDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClass_warships() {
        return class_warships;
    }

    public void setClass_warships(String class_warships) {
        this.class_warships = class_warships;
    }

    public LocalDate getCommissionDate() {
        return commissionDate;
    }

    public void setCommissionDate(LocalDate commissionDate) {
        this.commissionDate = commissionDate;
    }

    public LocalDate getDecommissionDate() {
        return decommissionDate;
    }

    public void setDecommissionDate(LocalDate decommissionDate) {
        this.decommissionDate = decommissionDate;
    }

    @Override
    public String toString() {
        return "Warships{" +
                "name='" + name + '\'' +
                ", warshipsClass='" + class_warships + '\'' +
                ", commissionDate=" + commissionDate +
                ", decommissionDate=" + decommissionDate +
                '}';
    }
}
