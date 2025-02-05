<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>S�lection de la base de donn�es</title>
    <link rel="stylesheet" href="assets/css/database.css"> <!-- Ajout du lien CSS -->
    <script src="assets/js/database.js"></script>
    
</head>
<body>
    <div class="container"> <!-- Ajout d'un conteneur pour le style -->
        <h1>Choisir une base de donn�es</h1>
        <form action="${pageContext.request.contextPath}/database" method="get">
            <label for="dbName">Nom de la base :</label>
            <input type="text" id="dbName" name="dbName" required placeholder="Ex: CompanyDB">
            <button type="submit">Afficher les tables</button>
        </form>
    </div>
</body>
</html>
