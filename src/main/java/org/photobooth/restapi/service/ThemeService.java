package org.photobooth.restapi.service;

import org.entityframework.error.EntityNotFoundException;
import org.photobooth.restapi.http.data.MaterielData;
import org.photobooth.restapi.http.data.MaterielDataList;
import org.photobooth.restapi.model.Theme;
import org.photobooth.restapi.model.img.ImageTheme;
import org.photobooth.restapi.util.AppProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.sql.Timestamp;
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
        }
        return themes;
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
}
