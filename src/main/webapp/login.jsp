<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Connexion</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- FontAwesome pour les icônes -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
</head>
<body class="d-flex align-items-center justify-content-center vh-100 bg-light">

<div class="card p-4 shadow-sm" style="width: 22rem;">
    <h2 class="text-center mb-4">Connexion</h2>

    <!-- Formulaire de connexion -->
    <form action="login" method="post">
        <!-- Nom d'utilisateur -->
        <div class="mb-3">
            <label for="username" class="form-label">Nom d'utilisateur</label>
            <input type="text" id="username" name="username" class="form-control" required>
        </div>

        <!-- Mot de passe -->
        <div class="mb-3">
            <label for="password" class="form-label">Mot de passe</label>
            <input type="password" id="password" name="password" class="form-control" required>
        </div>

        <!-- Bouton de connexion -->
        <button type="submit" class="btn btn-primary w-100">
            <i class="fas fa-sign-in-alt"></i> Se connecter
        </button>
    </form>

    <!-- Bouton pour créer un compte -->
    <div class="text-center mt-3">
        <a href="register.jsp" class="btn btn-secondary w-100">
            <i class="fas fa-user-plus"></i> Créer un compte
        </a>
    </div>
</div>

</body>
</html>
