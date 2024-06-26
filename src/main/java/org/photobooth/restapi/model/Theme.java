package org.photobooth.restapi.model;

import org.entityframework.tools.Col;
import org.entityframework.tools.Primary;
import org.entityframework.tools.Table;
import org.photobooth.restapi.model.img.ImageTheme;

import java.sql.Date;
import java.util.List;

@Table(name = "theme")
public class Theme {
    @Primary(isSequence = true, sequenceName = "theme_seq", prefixe = "TH_")
    private String id_theme;
    @Col(name = "id_salle", reference = "id_salle")
    private Salle salle;
    @Col(name = "id_categorie_theme", reference = "id_categorie_theme")
    private Categorie_theme categorie_theme;
    private String intitule;
    private Date date_debut;
    private Date date_fin;
    private String description;
    @Col(isTransient = true)
    private List<ImageTheme> imageThemes;
    @Col(isTransient = true)
    private double worth;
    @Col(isTransient = true)
    private int nbVisit;
    @Col(isTransient = true)
    private int nbPersonne;
    @Col(isTransient = true)
    private String type;
    @Col(isTransient = true)
    private String arrow;

    public Theme() {
    }

    public String getId_theme() {
        return id_theme;
    }

    public void setId_theme(String id_theme) {
        this.id_theme = id_theme;
    }

    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    public Categorie_theme getCategorie_theme() {
        return categorie_theme;
    }

    public void setCategorie_theme(Categorie_theme categorie_theme) {
        this.categorie_theme = categorie_theme;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public Date getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(Date date_debut) {
        this.date_debut = date_debut;
    }

    public Date getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(Date date_fin) {
        this.date_fin = date_fin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ImageTheme> getImageThemes() {
        return imageThemes;
    }

    public void setImageThemes(List<ImageTheme> imageThemes) {
        this.imageThemes = imageThemes;
    }

    public double getWorth() {
        return worth;
    }

    public void setWorth(double worth) {
        this.worth = worth;
    }

    public int getNbVisit() {
        return nbVisit;
    }

    public void setNbVisit(int nbVisit) {
        this.nbVisit = nbVisit;
    }

    public int getNbPersonne() {
        return nbPersonne;
    }

    public void setNbPersonne(int nbPersonne) {
        this.nbPersonne = nbPersonne;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getArrow() {
        return arrow;
    }

    public void setArrow(String arrow) {
        this.arrow = arrow;
    }
}
