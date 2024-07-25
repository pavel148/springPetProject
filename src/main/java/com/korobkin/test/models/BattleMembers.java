package com.korobkin.test.models;


public class BattleMembers {

    public BattleMembers(){

    }


    public BattleMembers(String battle_name, String ship_name, String result) {
        this.battle_name = battle_name;
        this.ship_name = ship_name;
        this.result = result;
    }
    private int battle_members_id;
    private String battle_name;

    private String ship_name;

    public void setBattle_members_id(int battle_members_id) {
        this.battle_members_id = battle_members_id;
    }

    private String result;

    public String getBattle_name() {
        return battle_name;
    }

    public void setBattle_name(String battle_name) {
        this.battle_name = battle_name;
    }

    public String getShip_name() {
        return ship_name;
    }

    public void setShip_name(String ship_name) {
        this.ship_name = ship_name;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getBattle_members_id() {
        return battle_members_id;
    }
}