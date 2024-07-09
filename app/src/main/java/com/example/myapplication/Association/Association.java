package com.example.myapplication.Association;

import java.util.List;

public class Association {
    public String nom;
    public String president;
    public String directrice;
    public String projet;
    public String telephone;
    public String email;
    public String adresse;
    public String siteWeb;
    public String rue;
    public String codePostal;
    public String ville;
    public String description;
    public List<String> actions;
    public String territoireIntervention;
    public String publicCible;
    public List<String> projetsSpecifiques;
    public String categorie;
    public String sousCategorie;

    public Association(String nom, String president, String adresse) {
        this.nom = nom;
        this.president = president;
        this.adresse = adresse;
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

    public String getDirectrice() {
        return directrice;
    }

    public void setDirectrice(String directrice) {
        this.directrice = directrice;
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

    public String getSiteWeb() {
        return siteWeb;
    }

    public void setSiteWeb(String siteWeb) {
        this.siteWeb = siteWeb;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public String getAdresse(){
        return rue + " " + codePostal + " " + ville;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
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

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
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