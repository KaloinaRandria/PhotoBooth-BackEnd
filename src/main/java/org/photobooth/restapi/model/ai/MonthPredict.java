package org.photobooth.restapi.model.ai;

import org.photobooth.restapi.model.Categorie_theme;
import org.photobooth.restapi.model.stat.ThemeCategStat;

import java.util.List;

public class MonthPredict {
    public String month;
    public double benefice;
    public List<ThemeCategStat> suggestion;

    public MonthPredict() {}

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public double getBenefice() {
        return benefice;
    }

    public void setBenefice(double benefice) {
        this.benefice = benefice;
    }

    public List<ThemeCategStat> getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(List<ThemeCategStat> suggestion) {
        this.suggestion = suggestion;
    }
}
