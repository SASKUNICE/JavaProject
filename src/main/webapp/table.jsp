<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Tables disponibles</title>
    <!-- Bootstrap et CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- FontAwesome pour les icônes -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <!-- Titre principal -->
    <h1 class="text-center mb-4">Tables disponibles dans la base <%= session.getAttribute("dbName") %></h1>

    <!-- Tableau pour afficher les tables -->
    <table class="table table-bordered table-striped text-center">
        <thead class="table-primary">
            <tr>
                <th>#</th>
                <th>NOM DE LA TABLE</th>
                <th>ACTIONS</th>
            </tr>
        </thead>
        <tbody>
            <% 
                // Récupérer la liste des tables depuis la session
                java.util.List<String> tables = (java.util.List<String>) session.getAttribute("tables");
                if (tables != null && !tables.isEmpty()) {
                    int index = 1; // Ajouter un index pour les lignes
                    for (String table : tables) { 
            %>
                <tr>
                    <td><%= index++ %></td>
                    <td><%= table %></td>
                    <td>
                        <!-- Redirection vers la page des colonnes -->
                         <a class="btn btn-primary btn-sm" href="columns?table=<%= table %>">
						    <i class="fas fa-eye"></i> Voir les colonnes
						</a>

                    </td>
                </tr>
            <% 
                    } 
                } else { 
            %>
                <!-- Message si aucune table n'est trouvée -->
                <tr>
                    <td colspan="3" class="text-center text-danger">Aucune table trouvée.</td>
                </tr>
            <% } %>
        </tbody>
    </table>

    <!-- Bouton retour -->
    <div class="d-flex justify-content-end mt-4">
        <a href="database.jsp" class="btn btn-secondary">
            <i class="fas fa-arrow-left"></i> Changer la base
        </a>
    </div>
</div>

<!-- Scripts Bootstrap -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
