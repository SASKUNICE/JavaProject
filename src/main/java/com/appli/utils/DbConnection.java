package com.appli.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import jakarta.servlet.http.HttpSession;

public class DbConnection {

    private static final String URL_TEMPLATE = "jdbc:mysql://localhost:3306/";
    private static final String USERNAME = "root"; // Modifier selon votre configuration
    private static final String PASSWORD = "SOUAFsouaf123"; // Modifier selon votre configuration

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors du chargement du driver MySQL.");
        }
    }

    public static Connection getConnection(HttpSession session) throws SQLException {
        String dbName = (String) session.getAttribute("dbName");
        if (dbName == null || dbName.trim().isEmpty()) {
            throw new SQLException("Nom de la base de données invalide ou non défini.");
        }

        String dbUrl = URL_TEMPLATE + dbName;
        return DriverManager.getConnection(dbUrl, USERNAME, PASSWORD);
    }
}
