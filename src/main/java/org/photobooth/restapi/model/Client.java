package org.photobooth.restapi.model;

import org.entityframework.tools.Primary;
import org.entityframework.tools.Table;

@Table(name = "client")
public class Client
{
    @Primary(isSequence = true, sequenceName = "client_seq", prefixe = "CLT_")
    private String id_client;
    private  String nom;

    private String prenom;

    private String email;

    private String num_telephone;

    public  Client(){}
    public String getId_client() {
        return id_client;
    }

    public void setId_client(String id_client) {
        this.id_client = id_client;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNum_telephone() {
        return num_telephone;
    }

    public void setNum_telephone(String num_telephone) {
        this.num_telephone = num_telephone;
    }
}
