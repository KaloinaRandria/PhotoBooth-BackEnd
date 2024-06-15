package org.photobooth.restapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.entityframework.tools.Col;
import org.entityframework.tools.Primary;
import org.entityframework.tools.Table;

import java.sql.Date;

@Table(name = "membre")
public class Membre {

    @Primary(isSequence = true, sequenceName = "id_membre_seq", prefixe = "MBR_")
    private String id_membre;
    @Col(name = "id_role", reference = "id_role")
    private Role role;
    private String nom;
    private String prenom;
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date_de_naissance;
    private String username;
    private String mail;
    private String mot_de_passe;
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date_embauche;
    @Col(name = "id_poste", reference = "id_poste")
    private Poste poste;
    @Col(isTransient = true)
    private Salaire salaire;

    public Membre() { }

    public String getId_membre() {
        return id_membre;
    }

    public void setId_membre(String id_membre) {
        this.id_membre = id_membre;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Date getDate_de_naissance() {
        return date_de_naissance;
    }

    public void setDate_de_naissance(Date date_de_naissance) {
        this.date_de_naissance = date_de_naissance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMot_de_passe() {
        return mot_de_passe;
    }

    public void setMot_de_passe(String mot_de_passe) {
        this.mot_de_passe = mot_de_passe;
    }

    public Date getDate_embauche() {
        return date_embauche;
    }

    public void setDate_embauche(Date date_embauche) {
        this.date_embauche = date_embauche;
    }

    public Poste getPoste() {
        return poste;
    }

    public void setPoste(Poste poste) {
        this.poste = poste;
    }

    public Salaire getSalaire() {
        return salaire;
    }

    public void setSalaire(Salaire salaire) {
        this.salaire = salaire;
    }
}
