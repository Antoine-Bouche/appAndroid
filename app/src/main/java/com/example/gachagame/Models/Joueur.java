package com.example.gachagame.Models;

public class Joueur {

    private int id;
    private int hp;
    private int atk;
    private int gold;

    private final int hpDuDepart = 50;
    private final int atkDuDepart = 5;

    public Joueur(int id, int hp, int atk) {
        this.id = id;
        this.hp = hp;
        this.atk = atk;
        this.gold = 0;
    }

    public Joueur(int id, int hp, int atk,int gold) {
        this.id = id;
        this.hp = hp;
        this.atk = atk;
        this.id = id;
        this.gold = gold;
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

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getHpDuDepart() {
        return hpDuDepart;
    }

    public int getAtkDuDepart() {
        return atkDuDepart;
    }
}
