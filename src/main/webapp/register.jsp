<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Créer un compte</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="d-flex align-items-center justify-content-center vh-100 bg-light">

<div class="card p-4 shadow-sm" style="width: 22rem;">
    <h2 class="text-center mb-4">Créer un compte</h2>

    <%-- Messages d'erreur ou de succès --%>
    <% String error = request.getParameter("error");
       String success = request.getParameter("success");
       if (error != null) { %>
           <div class="alert alert-danger"><%= error %></div>
    <% } else if (success != null) { %>
           <div class="alert alert-success"><%= success %></div>
    <% } %>

    <!-- Formulaire d'inscription -->
    <form action="${pageContext.request.contextPath}/register" method="post">
        <div class="mb-3">
            <label for="username" class="form-label">Nom d'utilisateur</label>
            <input type="text" id="username" name="username" class="form-control" required>
        </div>
        <div class="mb-3">
            <label for="password" class="form-label">Mot de passe</label>
            <input type="password" id="password" name="password" class="form-control" required>
        </div>
        <button type="submit" class="btn btn-primary w-100">Créer le compte</button>
    </form>

    <!-- Retour à la connexion -->
    <div class="text-center mt-3">
        <a href="login.jsp" class="btn btn-secondary w-100">Retour à la connexion</a>
    </div>
</div>

</body>
</html>
