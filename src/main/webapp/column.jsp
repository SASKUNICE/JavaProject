<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Colonnes de la Table</title>
    <!-- Bootstrap et CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- FontAwesome pour les icônes -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
    <!-- Style personnalisé -->
    <link href="assets/css/column.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h1 class="text-center mb-4">Colonnes de la table <%= session.getAttribute("currentTable") %></h1>

    <form method="post" action="export">
        <!-- Tableau -->
        <table class="table table-bordered table-striped text-center">
            <thead class="table-primary">
                <tr>
                    <th>SÉLECTIONNER</th>
                    <th>NOM DE LA COLONNE</th>
                    <th>TYPE</th>
                </tr>
            </thead>
            <tbody>
                <%
                    // Récupérer les colonnes
                    java.util.List<String[]> columns = (java.util.List<String[]>) session.getAttribute("columns");
                    if (columns == null || columns.isEmpty()) {
                %>
                    <tr>
                        <td colspan="3">Aucune colonne trouvée.</td>
                    </tr>
                <%
                    } else {
                        for (String[] column : columns) {
                %>
                    <tr>
                        <td><input type="checkbox" name="selectedColumns" value="<%= column[0] %>"></td>
                        <td><%= column[0] %></td>
                        <td><%= column[1] %></td>
                    </tr>
                <%
                        }
                    }
                %>
            </tbody>
        </table>

        <!-- Format d'export -->
        <div class="d-flex justify-content-between mt-4">
            <div class="d-flex align-items-center">
                <label for="exportFormat" class="me-2">Format d'export :</label>
                <select name="exportFormat" id="exportFormat" class="form-select me-3">
                    <option value="CSV">CSV</option>
                    <option value="JSON">JSON</option>
                    <option value="XML">XML</option>
                    <option value="XLS">XLS</option>
                    <option value="PDF">PDF</option>
                </select>
            </div>
            <div>
                <input type="submit" value="Exporter" class="btn btn-primary me-2">
                <a href="table.jsp" class="btn btn-secondary">Retour aux tables</a>
            </div>
        </div>
    </form>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</body>
</html>
