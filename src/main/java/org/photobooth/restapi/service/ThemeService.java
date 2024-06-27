package org.photobooth.restapi.service;

import io.micrometer.core.instrument.MultiGauge;
import org.entityframework.dev.Calculator;
import org.entityframework.dev.GenericSorter;
import org.entityframework.error.EntityNotFoundException;
import org.entityframework.tools.RowResult;
import org.photobooth.restapi.http.data.MaterielData;
import org.photobooth.restapi.http.data.MaterielDataList;
import org.photobooth.restapi.model.Depense;
import org.photobooth.restapi.model.Notification;
import org.photobooth.restapi.model.ServComp;
import org.photobooth.restapi.model.Theme;
import org.photobooth.restapi.model.img.ImageTheme;
import org.photobooth.restapi.model.stat.AllTimeThemeStat;
import org.photobooth.restapi.util.AppProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.*;

public class ThemeService extends Service {
    public ThemeService() {
        super();
    }

    public List<Theme> getAllTheme () throws Exception {
        List<Theme> themes = getNgContext().findWhen(Theme.class , "ORDER by date_debut asc");
        for (Theme theme : themes) {
            theme.setImageThemes(getImageTheme(theme.getId_theme()));
            theme.setWorth(getThemeWorth(theme.getId_theme()));
            setVisit(theme);
            theme.setType("success");
            theme.setArrow("top-right");
            if(theme.getNbPersonne() < 30) {
                theme.setType("danger");
                theme.setArrow("bottom-right");
            }
        }
        GenericSorter.sort(themes, "nbVisit");
        Collections.reverse(themes);
        return themes;
    }

    private void setVisit(Theme theme) throws Exception {
        String query = "SELECT * FROM v_stat_theme where id_theme = ?";
        RowResult rs = getNgContext().execute(query, theme.getId_theme());
        if(rs.next()) {
            Long l = (Long) rs.get(2);
            theme.setNbPersonne(l.intValue());
            Long l2 = (Long) rs.get(3);
            theme.setNbVisit(l2.intValue());
        }
    }

    public List<Theme> getAllTheme(Date start, Date end) throws Exception {
        List<Theme> themes = getNgContext().findWhereArgs(Theme.class, "date_debut >= ? AND date_fin <= ?", start, end);
        for (Theme theme : themes) {
            theme.setImageThemes(getImageTheme(theme.getId_theme()));
            theme.setWorth(getThemeWorth(theme.getId_theme()));

            String q = "SELECT" +
                    "        t.id_theme," +
                    "        COALESCE(SUM(r.nb_personne), 0) AS total_personnes," +
                    "        COUNT(r.id_reservation) AS visit" +
                    "    FROM" +
                    "        theme t" +
                    "            LEFT JOIN" +
                    "        reservation r ON t.id_theme = r.id_theme AND r.isConfirmed = true AND r.date_reservation BETWEEN ? AND ? WHERE t.id_theme = ?" +
                    "    GROUP BY" +
                    "        t.id_theme" +
                    "    ORDER BY" +
                    "        visit DESC";

            RowResult rs = getNgContext().execute(q, start, end, theme.getId_theme());
            if(rs.next()) {
                Long l = (Long) rs.get(2);
                theme.setNbPersonne(l.intValue());
                Long l2 = (Long) rs.get(3);
                theme.setNbVisit(l2.intValue());
            }
        }
        return themes;
    }

    private double getThemeWorth(String idTheme) throws Exception {
        RowResult result = getNgContext().execute("SELECT * FROM v_theme_worth WHERE id_theme = ?", idTheme);
        if (result.next()) {
            BigDecimal bd = (BigDecimal) result.get("worth");
            return bd.doubleValue();
        }
        return 0.0;
    }

    public String save(MultipartFile file , Theme theme, MaterielDataList materielDataList,  ApplicationContext applicationContext) throws Exception {
        try {
            getNgContext().setAutoCommit(false);
            long millis = System.currentTimeMillis();
            String modifiedName = modifyFileName(Objects.requireNonNull(file.getOriginalFilename()), millis);
            String relativeRoot = "/image/theme/" + modifiedName;

            String filePath = AppProperties.getSavedFileLocation(applicationContext) + relativeRoot;
            File savedFile = new File(filePath);
            file.transferTo(savedFile);

            String idTheme = (String) getNgContext().save(theme);

            ImageTheme imageTheme = new ImageTheme();
            imageTheme.setId_theme(idTheme);
            imageTheme.setDate_inserion(new Timestamp(millis));

            String root = "/files" + relativeRoot;
            imageTheme.setImage_url(root);

            getNgContext().save(imageTheme);

            for(MaterielData md : materielDataList.getMaterielDataList()) {
                md.setId_theme(idTheme);
                getNgContext().save(md);
                getNgContext().executeUpdate("update materiel set quantite = ? where id_materiel = ?", md.getReste(), md.getId_materiel());
            }

            double worth = getThemeWorth(idTheme);

            Depense depense = new Depense();
            depense.setLibele("Achat Theme : " + theme.getIntitule() + "(" + idTheme + ")");
            depense.setMontant(worth);
            depense.setDate_insertion(new Date(new java.util.Date().getTime()));
            getNgContext().save(depense);

            Notification notification2 = new Notification();
            notification2.setLibele("New expense added for theme : " + depense.getMontant());
            notification2.setType("warning");
            notification2.setIcon("mdi mdi-help-circle-outline");
            getNgContext().save(notification2);

            Notification notification = new Notification();
            notification.setLibele("New theme added : " + theme.getIntitule() + "(" + idTheme + ")");
            notification.setType("info");
            notification.setIcon("mdi mdi-bookmark-plus-outline");
            getNgContext().save(notification);

            getNgContext().commit();
            getNgContext().setAutoCommit(true);
            return idTheme;
        } catch (Exception e) {
            getNgContext().rollBack();
            getNgContext().commit();
            getNgContext().setAutoCommit(true);
            throw e;
        }
    }

    private static String modifyFileName(String fileName, long millis) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0) {
            String name = fileName.substring(0, dotIndex);
            String extension = fileName.substring(dotIndex);
            return name + "__" + millis + extension;
        } else {
            return fileName + "__" + millis;
        }
    }

    public void update(Theme theme) throws Exception {
        getNgContext().update(theme);

        Notification notification = new Notification();
        notification.setLibele("Update theme : " + theme.getId_theme());
        notification.setType("secondary");
        notification.setIcon("mdi mdi-lead-pencil");
        getNgContext().save(notification);
    }

    private List<ImageTheme> getImageTheme(String id_theme) throws Exception {
        return getNgContext().findWhere(ImageTheme.class, "id_theme = '" + id_theme + "'");
    }

    public Optional<Theme> getThemeById(String id) throws Exception {
        try {
            Theme theme = getNgContext().findById(id, Theme.class);
            return Optional.ofNullable(theme);
        } catch (EntityNotFoundException e) {
            return Optional.empty();
        }
    }

    public AllTimeThemeStat getStat(String idTheme) throws Exception {
        List<ServComp> servComps = getNgContext().findAll(ServComp.class);
        Theme theme = getNgContext().findById(idTheme, Theme.class);

        List<String> labels = new ArrayList<>();
        List<Object> data = new ArrayList<>();
        List<String> colors = new ArrayList<>();
        for (ServComp servComp : servComps) {
            labels.add(servComp.getIntitule());

            String query = "SELECT COUNT(*) as isa from reservation where id_service = ? and id_theme = ? and isConfirmed = ?";
            RowResult rs = getNgContext().execute(query, servComp.getId_comp_service(), theme.getId_theme(), true);
            if(rs.next()) {
                data.add(rs.get(1));
            }
            colors.add(servComp.getColor());
        }

        AllTimeThemeStat allTimeThemeStat = new AllTimeThemeStat();
        allTimeThemeStat.setLabels(labels);
        allTimeThemeStat.setData(data);
        allTimeThemeStat.setColors(colors);

        String query = "SELECT SUM(prix) as somme from reservation where id_theme = ? and isConfirmed = ?";
        RowResult rs = getNgContext().execute(query, theme.getId_theme(), true);
        if (rs.next()) {
            allTimeThemeStat.setGain(rs.get(1));
        } else {
            allTimeThemeStat.setGain(0);
        }

        long nb = 0;
        for (Object o : data) {
           nb = nb + (long) o;
        }
        allTimeThemeStat.setUsedCount(nb);

        return allTimeThemeStat;
    }

}
