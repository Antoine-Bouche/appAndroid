package com.example.gachagame.Models;

public class Joueur {

    private String pseudo;
    private String email;
    private String mot_de_passe;
    private Compétence[] listeCompétence;

    public Joueur(String pseudo, String email, String mot_de_passe) {
        this.pseudo = pseudo;
        this.email = email;
        this.mot_de_passe = mot_de_passe;
        this.listeCompétence = new Compétence[4];
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMot_de_passe() {
        return mot_de_passe;
    }

    public void setMot_de_passe(String mot_de_passe) {
        this.mot_de_passe = mot_de_passe;
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
