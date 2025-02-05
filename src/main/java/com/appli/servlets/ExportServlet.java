package com.appli.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.*;
import com.appli.utils.DbConnection;
import com.appli.utils.ExportTo;

// Annotation pour le mapping
@WebServlet("/export")
public class ExportServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Vérifier la session
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("dbName") == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Session invalide ou expirée.");
            return;
        }

        // Récupérer les paramètres
        String[] selectedColumns = request.getParameterValues("selectedColumns");
        String format = request.getParameter("exportFormat");
        String tableName = (String) session.getAttribute("currentTable");

        // Vérification des paramètres
        if (selectedColumns == null || selectedColumns.length == 0 || format == null || tableName == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Paramètres manquants.");
            return;
        }

        // Construction de la requête SQL dynamique
        String columns = String.join(", ", selectedColumns); // Joindre les colonnes sélectionnées
        String query = "SELECT " + columns + " FROM " + tableName;

        // Connexion à la base de données et exécution de la requête
        try (Connection conn = DbConnection.getConnection(session); // Utilise DbConnection pour se connecter
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            // Générer le fichier en fonction du format choisi (Méthodes ExportTo à implémenter plus tard)
            switch (format) {
                case "CSV":
                    ExportTo.exportToCSV(rs, response); // Méthode à implémenter
                    break;
                case "JSON":
                    ExportTo.exportToJSON(rs, response); // Méthode à implémenter
                    break;
                case "XML":
                    ExportTo.exportToXML(rs, response); // Méthode à implémenter
                    break;
                case "XLS":
                    ExportTo.exportToXLS(rs, response); // Méthode à implémenter
                    break;
                case "PDF":
                    ExportTo.exportToPDF(rs, response); // Méthode à implémenter
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Format non pris en charge !");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur SQL : " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur : " + e.getMessage());
        }
    }
}
