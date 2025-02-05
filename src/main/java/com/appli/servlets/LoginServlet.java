package com.appli.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    // Informations de connexion à la base de données
    private static final String DB_URL = "jdbc:mysql://localhost:3306/users"; // Remplace "users" par ta base
    private static final String DB_USER = "root"; // Remplace par ton utilisateur
    private static final String DB_PASSWORD = "SOUAFsouaf123"; // Remplace par ton mot de passe

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Récupérer les informations saisies
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // 2. Vérification des champs vides
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            response.sendRedirect("login.jsp?error=Veuillez remplir tous les champs.");
            return;
        }

        // Connexion classique
        try {
            // 3. Connexion à la base de données
            Class.forName("com.mysql.cj.jdbc.Driver"); // Charger le driver MySQL
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

                // 4. Préparer la requête SQL
                String sql = "SELECT password FROM utilisateurs WHERE username = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, username);

                ResultSet rs = stmt.executeQuery();

                // 5. Vérification des résultats
                if (rs.next()) {
                    // Mot de passe récupéré depuis la base de données
                    String dbPassword = rs.getString("password");

                    // Comparer les mots de passe
                    if (password.equals(dbPassword)) { // Comparaison simple
                        // 6. Créer une session utilisateur
                        HttpSession session = request.getSession();
                        session.setAttribute("username", username);
                        session.setMaxInactiveInterval(30 * 60); // Timeout 30 minutes

                        // Rediriger vers la page des bases de données
                        response.sendRedirect("database.jsp");
                    } else {
                        // Mot de passe incorrect
                        response.sendRedirect("login.jsp?error=Mot de passe incorrect.");
                    }
                } else {
                    // Aucun utilisateur trouvé
                    response.sendRedirect("login.jsp?error=Utilisateur introuvable.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("login.jsp?error=Erreur de base de données.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            response.sendRedirect("login.jsp?error=Driver non trouvé.");
        }
    }
}
