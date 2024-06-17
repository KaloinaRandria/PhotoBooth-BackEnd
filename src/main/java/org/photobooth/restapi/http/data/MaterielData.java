package org.photobooth.restapi.http.data;

import org.entityframework.tools.Col;
import org.entityframework.tools.Primary;
import org.entityframework.tools.Table;

@Table(name = "materiel_theme")
public class MaterielData {
    @Primary(isSequence = true, sequenceName = "materiel_theme_seq", prefixe = "MAT_TH")
    @Col(name = "id_materiel_theme")
    private String id_mat_data;
    @Col(name = "quantite")
    private int count;
    private String id_materiel;
    @Col(isTransient = true)
    private int reste;
    private String id_theme;

    public MaterielData() {}
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public String getId_materiel() {
        return id_materiel;
    }
    public void setId_materiel(String id_materiel) {
        this.id_materiel = id_materiel;
    }
    public int getReste() {
        return reste;
    }
    public void setReste(int reste) {
        this.reste = reste;
    }
    public String getId_mat_data() {
        return id_mat_data;
    }
    public void setId_mat_data(String id_mat_data) {
        this.id_mat_data = id_mat_data;
    }
    public String getId_theme() {
        return id_theme;
    }
    public void setId_theme(String id_theme) {
        this.id_theme = id_theme;
    }

    
}
