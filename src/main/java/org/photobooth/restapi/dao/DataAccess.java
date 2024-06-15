package org.photobooth.restapi.dao;

import org.entityframework.dev.Driver;

import java.sql.Connection;
import java.sql.SQLException;

public class DataAccess {
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        return Driver.getPGConnection("postgres", "postgres", "photobooth");
    }
}
