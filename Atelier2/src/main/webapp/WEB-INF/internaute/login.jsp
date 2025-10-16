<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/WEB-INF/includes/header-login.jsp"/>

<!-- 🌸 STYLE MINIMALISTE ROSE CLAIR -->
<style>
    @import url('https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap');

    body {
        background: #fff6fa;
        font-family: 'Poppins', sans-serif;
        color: #333;
        margin: 0;
        padding: 0;
    }

    /* Conteneur principal */
    .container {
        max-width: 400px;
        margin: 80px auto;
        background: #ffffff;
        padding: 2.5rem;
        border-radius: 16px;
        box-shadow: 0 6px 20px rgba(255, 182, 193, 0.25);
        transition: all 0.3s ease;
    }

    .container:hover {
        transform: translateY(-4px);
        box-shadow: 0 10px 24px rgba(255, 182, 193, 0.35);
    }

    /* Titre */
    h2 {
        text-align: center;
        color: #d63384;
        font-weight: 600;
        margin-bottom: 1.8rem;
    }

    /* Champs du formulaire */
    .form-group {
        margin-bottom: 1.2rem;
    }

    label {
        display: block;
        margin-bottom: 0.5rem;
        font-weight: 500;
        color: #555;
    }

    input.form-control {
        width: 100%;
        padding: 0.8rem 1rem;
        border: 1px solid #f5c2e7;
        border-radius: 10px;
        font-size: 0.95rem;
        background-color: #fff8fb;
        transition: all 0.3s ease;
    }

    input.form-control:focus {
        border-color: #f06292;
        box-shadow: 0 0 0 3px rgba(240, 98, 146, 0.2);
        outline: none;
    }

    /* Bouton */
    button.btn {
        width: 100%;
        padding: 0.9rem;
        border: none;
        border-radius: 10px;
        background: linear-gradient(135deg, #f06292, #ec407a);
        color: #fff;
        font-weight: 500;
        font-size: 1rem;
        cursor: pointer;
        transition: all 0.3s ease;
    }

    button.btn:hover {
        background: linear-gradient(135deg, #ec407a, #d81b60);
        transform: translateY(-2px);
    }

    /* Message d’erreur */
    .alert {
        border-radius: 8px;
        padding: 0.9rem;
        background-color: #ffe3ec;
        color: #b71c1c;
        font-size: 0.9rem;
        text-align: center;
        margin-top: 1rem;
    }

    /* Lien créer un compte */
    .create-account {
        text-align: center;
        margin-top: 1.2rem;
    }

    .create-account a {
        color: #d63384;
        text-decoration: none;
        font-weight: 500;
        transition: color 0.3s ease;
    }

    .create-account a:hover {
        color: #ad1457;
    }

    /* Animation douce à l'apparition */
    @keyframes fadeIn {
        from { opacity: 0; transform: translateY(20px); }
        to { opacity: 1; transform: translateY(0); }
    }
    .container {
        animation: fadeIn 0.6s ease forwards;
    }
</style>

<!-- 🌸 CONTENU -->
<div class="container">
    <h2>Connexion</h2>
    <form method="post" action="${pageContext.request.contextPath}/internaute" id="loginForm">
        <input type="hidden" name="action" value="login">

        <div class="form-group">
            <label for="email">Adresse e-mail</label>
            <input type="email" class="form-control" id="email" name="email" placeholder="exemple@email.com" required />
        </div>

        <div class="form-group">
            <label for="motDePasse">Mot de passe</label>
            <input type="password" class="form-control" id="motDePasse" name="motDePasse" placeholder="********" required />
        </div>

        <c:if test="${not empty loginError}">
            <div class="alert">${loginError}</div>
        </c:if>

        <button type="submit" class="btn">Se connecter</button>
    </form>

    <div class="create-account">
        <a href="${pageContext.request.contextPath}/internaute?action=addForm">Créer un compte</a>
    </div>
</div>

<jsp:include page="/WEB-INF/includes/footer.jsp"/>

<!-- 🌸 Script validation simple -->
<script>
    $(function(){
        $('#loginForm').on('submit', function(e){
            if($('#email').val().trim() === '' || $('#motDePasse').val().trim() === ''){
                e.preventDefault();
                alert('Veuillez remplir tous les champs.');
            }
        });
    });
</script>
