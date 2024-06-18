package org.photobooth.restapi.http.data;

public class MaterielAddData {
    private String id_materiel;
    private int last_quantite;
    private int new_quantite;

    public MaterielAddData() {}

    public String getId_materiel() {
        return id_materiel;
    }

    public void setId_materiel(String id_materiel) {
        this.id_materiel = id_materiel;
    }

    public int getLast_quantite() {
        return last_quantite;
    }

    public void setLast_quantite(int last_quantite) {
        this.last_quantite = last_quantite;
    }

    public int getNew_quantite() {
        return new_quantite;
    }

    public void setNew_quantite(int new_quantite) {
        this.new_quantite = new_quantite;
    }

    
}
