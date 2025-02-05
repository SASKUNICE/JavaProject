package com.appli.servlets;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.appli.utils.DbConnection;

@WebServlet("/tables")
public class TableServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Utiliser la session existante
        HttpSession session = request.getSession(false); // Ne pas créer de nouvelle session

        // Vérifier si la session est valide
        if (session == null || session.getAttribute("dbName") == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Session invalide ou expirée.");
            return;
        }

        // Récupérer le nom de la base depuis la session
        String dbName = (String) session.getAttribute("dbName");

        try (Connection conn = DbConnection.getConnection(session)) {
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet rs = metaData.getTables(dbName, null, "%", new String[]{"TABLE"});

            List<String> tables = new ArrayList<>();
            while (rs.next()) {
                tables.add(rs.getString("TABLE_NAME"));
            }

            // Stocker la liste des tables dans la session
            session.setAttribute("tables", tables);

            // Rediriger vers table.jsp
            response.sendRedirect("table.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors de la récupération des tables.");
        }
    }

}
