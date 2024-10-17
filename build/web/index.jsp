<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link rel="stylesheet" href="/mini-projet/assets/css/login1.css">
</head>
<body>
<div class="container" id="container">
    <div class="form-container sign-up-container">
    </div>
    <div class="form-container sign-in-container">
        <form action="/mini-projet/Login_servlet" method="post">
            <h1>Connexion</h1>
            <div class="social-container">
                    <a href="#" class="social"><i class="fab fa-facebook-f"></i></a>
                    <a href="#" class="social"><i class="fab fa-google-plus-g"></i></a>
                    <a href="#" class="social"><i class="fab fa-linkedin-in"></i></a>
            </div>
            <span>Veuillez remplir les champs</span>
            <input type="email" name="email" placeholder="Veuillez entrer votre Email" />
            <input type="password" name="mdp" placeholder="Veuillez entrer votre Mot de passe" />
            <a href="#">Mot de passe oublié ?</a>
            <button>Se connecter</button>
        </form>
    </div>
    <div class="overlay-container">
        <div class="overlay">
            <div class="overlay-panel overlay-left">
                    <h1>Gestion</h1>
                    <p></p>
                    <button class="ghost" id="signIn">Client</button>
            </div>
            <div class="overlay-panel overlay-right">
                    <h1>Gestion</h1>
                    <p></p>
                    <button class="ghost" id="signUp">Administrateur</button>
            </div>
            <div class="overlay">
                <div class="overlay-panel overlay-left">
                    <h1>Gestion</h1>
                    <p></p>
                    <button class="ghost" id="signIn">Client</button>
                </div>
            </div>
    </div>
</div>

<footer>

</footer>

<script>
    const signUpButton = document.getElementById('signUp');
	const signInButton = document.getElementById('signIn');
	const container = document.getElementById('container');

	signUpButton.addEventListener('click', () => {
		container.classList.add("right-panel-active");
	});

	signInButton.addEventListener('click', () => {
		container.classList.remove("right-panel-active");
});

</script>
</body>
</html>
