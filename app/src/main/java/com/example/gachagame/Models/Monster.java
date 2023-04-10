package com.example.gachagame.Models;

public class Monster {

    private int id;
    private String name;
    private int hp;
    private int atk;
    private int gold;
    private int imageResourceId;

    public Monster(int id, String name, int hp, int atk, int gold, int imageResourceId) {
        this.id = id;
        this.name = name;
        this.hp = hp;
        this.atk = atk;
        this.gold = gold;
        this.imageResourceId = imageResourceId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

    @Override
    public String toString() {
        return name + " : " +
                " hp=" + hp +
                ", atk=" + atk +
                '\n' ;
    }
}
