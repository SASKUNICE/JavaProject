package com.appli.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/database")
public class DatabaseServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Récupérer la session existante (créée lors de la connexion)
        HttpSession session = request.getSession(false); // false : ne crée pas de nouvelle session

        // Vérifier si la session existe
        if (session == null || session.getAttribute("username") == null) { 
            // Rediriger vers la page de login si pas de session active
            response.sendRedirect("login.jsp");
            return;
        }

        // Récupérer le nom de la base de données
        String dbName = request.getParameter("dbName");

        // Vérifier si la base est valide
        if (dbName == null || dbName.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Nom de la base de données non spécifié.");
            return;
        }

        // **NOUVELLE CONDITION : Bloquer l'accès à la base 'users'**
        if (dbName.equalsIgnoreCase("users")) {
            // Message d'erreur si la base est interdite
            response.sendRedirect("database.jsp?error=Accès interdit à cette base de données !");
            return;
        }

        // Stocker le nom de la base dans la session existante
        session.setAttribute("dbName", dbName);

        // Redirection vers la TableServlet avec le paramètre dbName
        response.sendRedirect("tables?dbName=" + dbName);
    }
}
