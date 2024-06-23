package org.photobooth.restapi.service;

import org.entityframework.dev.Calculator;
import org.entityframework.error.EntityNotFoundException;
import org.entityframework.tools.RowResult;
import org.photobooth.restapi.http.data.MaterielData;
import org.photobooth.restapi.http.data.MaterielDataList;
import org.photobooth.restapi.model.ServComp;
import org.photobooth.restapi.model.Theme;
import org.photobooth.restapi.model.img.ImageTheme;
import org.photobooth.restapi.model.stat.AllTimeThemeStat;
import org.photobooth.restapi.util.AppProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ThemeService extends Service {
    public ThemeService() {
        super();
    }

    public List<Theme> getAllTheme () throws Exception {
        List<Theme> themes = getNgContext().findAll(Theme.class);
        for (Theme theme : themes) {
            theme.setImageThemes(getImageTheme(theme.getId_theme()));
            theme.setWorth(getThemeWorth(theme.getId_theme()));
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
