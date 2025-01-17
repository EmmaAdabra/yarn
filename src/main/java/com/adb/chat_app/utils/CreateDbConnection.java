package com.adb.chat_app.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CreateDbConnection {
    private static final String USER_NAME = "postgres";
    private static final String PASSWORD = "script101";
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/facebook_clone";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USER_NAME, PASSWORD);
    }
}
