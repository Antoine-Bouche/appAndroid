package com.example.gachagame.Models;

public class Joueur {

    private String pseudo;
    private Compétence[] listeCompétence;

    public Joueur(String pseudo) {
        this.pseudo = pseudo;
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