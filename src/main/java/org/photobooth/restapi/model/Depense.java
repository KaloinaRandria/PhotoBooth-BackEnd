package org.photobooth.restapi.model;

import org.entityframework.tools.Col;
import org.entityframework.tools.Primary;

import java.sql.Date;

public class Depense {
   @Primary(auto = true)
   @Col(name = "id")
   private int idDepense;
   private double montant;
   private String libele;

  private Date data_insertion;

    public int getIdDepense() {
        return idDepense;
    }

    public void setIdDepense(int idDepense) {
        this.idDepense = idDepense;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public String getLibele() {
        return libele;
    }

    public void setLibele(String libele) {
        this.libele = libele;
    }

    public Date getData_insertion() {
        return data_insertion;
    }

    public void setData_insertion(Date data_insertion) {
        this.data_insertion = data_insertion;
    }
}
