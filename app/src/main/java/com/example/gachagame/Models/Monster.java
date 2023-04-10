package com.example.gachagame.Models;

public class Monster {
    private int hp;
    private int atk;
    private int id;
    private String name;
    private int imageResourceId;

    public Monster(int hp, int atk, int id, String name, int imageResourceId) {
        this.hp = hp;
        this.atk = atk;
        this.id = id;
        this.name = name;
        this.imageResourceId = imageResourceId;
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

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }
}
