package com.example.gachagame.Models;

public class Compétence {

    private String nom_comp;
    private String type_comp;
    private String comp_desc;

    public Compétence(String nom_comp, String type_comp, String comp_desc) {
        this.nom_comp = nom_comp;
        this.type_comp = type_comp;
        this.comp_desc = comp_desc;
    }

    public String getNom_comp() {
        return nom_comp;
    }

    public void setNom_comp(String nom_comp) {
        this.nom_comp = nom_comp;
    }

    public String getType_comp() {
        return type_comp;
    }

    public void setType_comp(String type_comp) {
        this.type_comp = type_comp;
    }

    public String getComp_desc() {
        return comp_desc;
    }

    public void setComp_desc(String comp_desc) {
        this.comp_desc = comp_desc;
    }
}
