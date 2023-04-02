package com.example.gachagame.Models;

public class Joueur extends Entity{

    private String pseudo;
    private Inventory inventory;
    private int gold;
    private int diamion;
    private Compétence[] listeCompétence;
    private int hp;
    private int atk;
    private int xp;
    private int id;


    public Joueur(String pseudo, int hp, int atk, int id) {
        super(hp,atk,id,pseudo);
        this.listeCompétence = new Compétence[4];
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public Compétence[] getListeCompétence() {
        return listeCompétence;
    }

    public void setListeCompétence(Compétence[] listeCompétence) {
        this.listeCompétence = listeCompétence;
    }

    public void addCompétence(Compétence compétence,int index) {
        this.listeCompétence[index] = compétence;
    }
}