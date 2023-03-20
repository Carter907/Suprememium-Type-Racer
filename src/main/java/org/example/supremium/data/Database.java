package org.example.supremium.data;

import org.example.supremium.AppStart;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public enum Database {
    INSTANCE("data/app.db");

    private volatile Connection conn;

    private String path;
    Database(String path) {
        this.path = path;
    }

    public synchronized Connection getConnection() throws SQLException {

        if (conn == null) {
            conn = DriverManager.getConnection("jdbc:sqlite:"+AppStart.class.getResource(path));
        }
        return conn;
    }
}
