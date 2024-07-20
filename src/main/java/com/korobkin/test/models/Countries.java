package com.korobkin.test.models;

import javax.validation.constraints.NotEmpty;

public class Countries {

    public Countries(){

    }
    public Countries(String name, String side){
        this.name = name;
        this.side = side;
    }

    @NotEmpty
    private String name;

    @NotEmpty
    private String side;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }
}
