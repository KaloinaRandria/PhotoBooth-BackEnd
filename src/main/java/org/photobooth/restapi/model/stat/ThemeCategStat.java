package org.photobooth.restapi.model.stat;

import java.util.*;

public class ThemeCategStat {
    private String id_categorie_theme;
    private String categorie_theme;
    private int nombre_reservations;

    public ThemeCategStat() {}

    public ThemeCategStat(String id, String categorieTheme, int round) {
        setId_categorie_theme(id);
        setCategorie_theme(categorieTheme);
        setNombre_reservations(round);
    }

    public String getId_categorie_theme() {
        return id_categorie_theme;
    }

    public void setId_categorie_theme(String id_categorie_theme) {
        this.id_categorie_theme = id_categorie_theme;
    }

    public String getCategorie_theme() {
        return categorie_theme;
    }

    public void setCategorie_theme(String categorie_theme) {
        this.categorie_theme = categorie_theme;
    }

    public int getNombre_reservations() {
        return nombre_reservations;
    }

    public void setNombre_reservations(int nombre_reservations) {
        this.nombre_reservations = nombre_reservations;
    }

    public static List<ThemeCategStat> suggestRanking(List<ThemeCategStat> classement2022, List<ThemeCategStat> classement2023) {
        Map<String, ThemeCategStat> mergedStats = new HashMap<>();
        Map<String, Integer> rankingSums = new HashMap<>();
        Map<String, Integer> rankingCounts = new HashMap<>();

        // Add rankings from 2022
        for (int i = 0; i < classement2022.size(); i++) {
            ThemeCategStat stat = classement2022.get(i);
            String id = stat.getId_categorie_theme();
            int rank = i + 1;

            mergedStats.put(id, stat);
            rankingSums.put(id, rankingSums.getOrDefault(id, 0) + rank);
            rankingCounts.put(id, rankingCounts.getOrDefault(id, 0) + 1);
        }

        // Add rankings from 2023
        for (int i = 0; i < classement2023.size(); i++) {
            ThemeCategStat stat = classement2023.get(i);
            String id = stat.getId_categorie_theme();
            int rank = i + 1;

            if (!mergedStats.containsKey(id)) {
                mergedStats.put(id, stat);
            }
            rankingSums.put(id, rankingSums.getOrDefault(id, 0) + rank);
            rankingCounts.put(id, rankingCounts.getOrDefault(id, 0) + 1);
        }

        // Calculate average rankings and create the list
        List<ThemeCategStat> suggestedRanking = new ArrayList<>();
        for (Map.Entry<String, ThemeCategStat> entry : mergedStats.entrySet()) {
            String id = entry.getKey();
            ThemeCategStat stat = entry.getValue();
            double averageRank = (double) rankingSums.get(id) / rankingCounts.get(id);

            ThemeCategStat newStat = new ThemeCategStat(id, stat.getCategorie_theme(), (int) Math.round(averageRank));
            suggestedRanking.add(newStat);
        }

        // Sort by average ranking
        suggestedRanking.sort(Comparator.comparingInt(ThemeCategStat::getNombre_reservations));

        return suggestedRanking;
    }
}
