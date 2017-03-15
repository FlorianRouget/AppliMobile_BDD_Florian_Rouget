package com.example.florian.applimobile_bdd_florianrouget;

/**
 * Created by florian on 15/03/2017.
 */

public class Chapitre {
    private int id;
    private String nom;
    private String description;
    //Vos getters et setters
    public int getChapterId(){return id;}
    public void setChapterId(int nv){ this.id = nv;}

    public String getChapterName(){return nom;}
    public void setChapterName(String nv){ this.nom = nv;}

    public String getChapterDesc(){return description;}
    public void setChapterDesc(String nv){ this.description = nv;}

    //Surcharger la méthode toString qui vous permet d’afficher les membres de l’instance Chapitre
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Nom du Chapitre : "+nom+"\n"+"Description du Chapitre : "+description);
        return sb.toString();
    }
}
