package com.example.gachagame.Models;

public class Joueur {

    private int id;
    private int hp;
    private int atk;
    private Compétence[] listeCompétence;
    private int gold;

    public Joueur(int id, int hp, int atk) {
        this.id = id;
        this.hp = hp;
        this.atk = atk;
        this.id = id;
        this.listeCompétence = new Compétence[4];
        this.gold = 0;
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

    public Compétence[] getListeCompétence() {
        return listeCompétence;
    }

    public void setListeCompétence(Compétence[] listeCompétence) {
        this.listeCompétence = listeCompétence;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }
}