package com.example.assoevreux.Annonce;

import com.google.firebase.Timestamp;

public class Annonce {
    private String titre;
    private String description;
    private Timestamp datePublication;
    public String nomAssociation;

    public Annonce(String titre, String description, Timestamp datePublication, String nomAssociation) {
        this.titre = titre;
        this.description = description;
        this.datePublication = datePublication;
        this.nomAssociation = nomAssociation;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDatePublication() {
        return datePublication;
    }

    public void setDatePublication(Timestamp datePublication) {
        this.datePublication = datePublication;
    }

    public String getNomAssociation() {
        return nomAssociation;
    }

    public void setNomAssociation(String nomAssociation) {
        this.nomAssociation = nomAssociation;
    }
}
