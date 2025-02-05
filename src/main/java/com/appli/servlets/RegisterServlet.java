package com.appli.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    // Informations de connexion
    private static final String URL = "jdbc:mysql://localhost:3306/users"; // Base "users"
    private static final String USER = "root"; // Ton utilisateur MySQL
    private static final String PASSWORD = "SOUAFsouaf123"; // Ton mot de passe MySQL

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Récupérer les paramètres
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Vérifier si les champs sont vides
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            response.sendRedirect("register.jsp?error=Champs requis !");
            return;
        }

        // Connexion directe à la base "users"
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            // Hachage du mot de passe

            // Requête SQL pour insérer l'utilisateur
            String sql = "INSERT INTO utilisateurs (username, password) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            // Exécution de la requête
            stmt.executeUpdate();

            // Redirection vers la page de connexion avec un message de succès
            response.sendRedirect("login.jsp?success=Compte créé !");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("register.jsp?error=Erreur lors de l'inscription.");
        }
    }
}
