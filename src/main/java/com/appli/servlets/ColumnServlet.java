package com.appli.servlets;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.appli.utils.DbConnection;

@WebServlet("/columns")
public class ColumnServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false); // Utiliser la session existante

        // Vérifier si la session est valide
        if (session == null || session.getAttribute("dbName") == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Session invalide ou expirée.");
            return;
        }

        // Récupérer le nom de la table depuis la requête
        String tableName = request.getParameter("table");

        // Vérifier si la table est spécifiée
        if (tableName == null || tableName.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Nom de la table non spécifié.");
            return;
        }

        // Stocker la table choisie dans la session
        session.setAttribute("currentTable", tableName);

        // Récupérer la base de données depuis la session
        String dbName = (String) session.getAttribute("dbName");

        List<String[]> columns = new ArrayList<>();
        try (Connection conn = DbConnection.getConnection(session)) { // Utiliser DbConnection
        	String sql = "SELECT COLUMN_NAME, DATA_TYPE " +
                    "FROM INFORMATION_SCHEMA.COLUMNS " +
                    "WHERE TABLE_NAME = ? AND TABLE_SCHEMA = ?";

        	 try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        	        stmt.setString(1, tableName); // Nom de la table
        	        stmt.setString(2, dbName);    // Nom de la base de données
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        columns.add(new String[]{rs.getString("COLUMN_NAME"), rs.getString("DATA_TYPE")});
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur : " + e.getMessage());
            return;
        }

        // Stocker les colonnes dans la session
        session.setAttribute("columns", columns);

        // Rediriger vers column.jsp pour afficher les colonnes
        response.sendRedirect("column.jsp");
    }
}
