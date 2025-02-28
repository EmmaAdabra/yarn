package com.adb.any_post.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CreateDbConnection {

//    Class.forName("org.postgresql.Driver");
    private static final String USER_NAME = "postgres";
    private static final String PASSWORD = "script101";
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/facebook_clone?charset=UTF-8";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return DriverManager.getConnection(JDBC_URL, USER_NAME, PASSWORD);
    }
}
