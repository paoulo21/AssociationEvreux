package com.example.myapplication.Association;

import java.util.List;

public class Association {
    public String nom;
    public String president;
    public String projet;
    public String telephone;
    public String email;
    public String adresse;
    public String imageURL;
    public String siteWeb;
    public String description;
    public List<String> actions;
    public String territoireIntervention;
    public String publicCible;
    public List<String> projetsSpecifiques;
    public String categorie;
    public String sousCategorie;

    public Association(String nom, String president, String adresse, String description, String imageURL) {
        this.nom = nom;
        this.president = president;
        this.adresse = adresse;
        this.description = description;
        this.imageURL = imageURL;
        telephone = "02323424243";
        email = "test@mail.com";
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPresident() {
        return president;
    }

    public void setPresident(String president) {
        this.president = president;
    }

    public String getProjet() {
        return projet;
    }

    public void setProjet(String projet) {
        this.projet = projet;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getSiteWeb() {
        return siteWeb;
    }

    public void setSiteWeb(String siteWeb) {
        this.siteWeb = siteWeb;
    }

    public String getAdresse(){
        return adresse;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getActions() {
        return actions;
    }

    public void setActions(List<String> actions) {
        this.actions = actions;
    }

    public String getTerritoireIntervention() {
        return territoireIntervention;
    }

    public void setTerritoireIntervention(String territoireIntervention) {
        this.territoireIntervention = territoireIntervention;
    }

    public List<String> getProjetsSpecifiques() {
        return projetsSpecifiques;
    }

    public void setProjetsSpecifiques(List<String> projetsSpecifiques) {
        this.projetsSpecifiques = projetsSpecifiques;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getPublicCible() {
        return publicCible;
    }

    public void setPublicCible(String publicCible) {
        this.publicCible = publicCible;
    }

    public String getSousCategorie() {
        return sousCategorie;
    }

    public void setSousCategorie(String sousCategorie) {
        this.sousCategorie = sousCategorie;
    }
}