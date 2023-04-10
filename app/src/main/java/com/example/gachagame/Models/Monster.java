package com.example.gachagame.Models;

public class Monster {
    private int hp;
    private int atk;
    private int id;
    private String name;

    public Monster(int hp, int atk, int id, String name) {
        this.hp =hp;
        this.atk = atk;
        this.id = id;
        this.name =name;
    }

    public int getAtk() {
        return atk;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
