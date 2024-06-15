package org.photobooth.restapi.service;

import org.entityframework.client.GenericEntity;
import org.photobooth.restapi.dao.DataAccess;

import java.sql.SQLException;
import java.util.List;

public class Service implements AutoCloseable {
    private final GenericEntity ngContext;

    public GenericEntity getNgContext() {
        return ngContext;
    }

    public Service() {
        try {
            this.ngContext = new GenericEntity(DataAccess.getConnection());
        } catch (Exception e) {
            throw new IllegalStateException("Erreur lors de l'initialisation du service", e);
        }
    }

    public void beginTransaction() throws Exception {
        getNgContext().setAutoCommit(false);
        getNgContext().setLogged(true);
    }

    public void endTransaction() throws Exception {
        getNgContext().commit();
    }

    @Override
    public void close() throws SQLException {
        getNgContext().closeConnection();
    }

    public void commit() throws SQLException {
        getNgContext().commit();
    }

    public void rollBack() throws SQLException {
        getNgContext().rollBack();
    }

    public <T> List<T> getAll(Class<T> tClass) throws Exception {
        return getNgContext().findAll(tClass);
    }
}
