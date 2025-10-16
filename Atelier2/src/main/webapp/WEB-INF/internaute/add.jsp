<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/includes/header-login.jsp"/>

<!-- Style interne pour un thème rose clair moderne -->
<style>
  body {
    background: linear-gradient(135deg, #ffe6f0, #fff5fa);
    font-family: 'Poppins', sans-serif;
    margin: 0;
    padding-top: 100px;
  }

  .register-container {
    max-width: 420px;
    margin: 60px auto;
    background: #fff;
    border-radius: 20px;
    padding: 2.5rem;
    box-shadow: 0 4px 20px rgba(255, 192, 203, 0.2);
    transition: all 0.3s ease;
  }

  .register-container:hover {
    transform: translateY(-5px);
    box-shadow: 0 6px 25px rgba(255, 182, 193, 0.3);
  }

  .register-container h2 {
    text-align: center;
    color: #d63384;
    font-size: 1.8rem;
    margin-bottom: 1.5rem;
  }

  .form-group {
    margin-bottom: 1.2rem;
  }

  .form-group label {
    display: block;
    margin-bottom: 0.4rem;
    color: #333;
    font-weight: 600;
  }

  .form-control {
    width: 100%;
    padding: 0.7rem 1rem;
    border: 1px solid #f8cce0;
    border-radius: 12px;
    font-size: 1rem;
    transition: border-color 0.3s ease, box-shadow 0.3s ease;
  }

  .form-control:focus {
    border-color: #f06292;
    box-shadow: 0 0 8px rgba(240, 98, 146, 0.3);
    outline: none;
  }

  .btn-primary {
    width: 100%;
    background: linear-gradient(135deg, #ff80ab, #f06292);
    border: none;
    color: white;
    padding: 0.8rem;
    border-radius: 12px;
    font-size: 1.1rem;
    cursor: pointer;
    transition: all 0.3s ease;
  }

  .btn-primary:hover {
    background: linear-gradient(135deg, #f06292, #ec407a);
    transform: translateY(-2px);
  }

  .login-link {
    text-align: center;
    margin-top: 1rem;
  }

  .login-link a {
    text-decoration: none;
    color: #d63384;
    font-weight: 500;
    transition: color 0.3s ease;
  }

  .login-link a:hover {
    color: #ad1457;
  }
</style>

<!-- Contenu principal -->
<div class="register-container">
  <h2>Créer un compte</h2>
  <form method="post" action="${pageContext.request.contextPath}/internaute?action=add" id="registerForm">
    <div class="form-group">
      <label for="nom">Nom</label>
      <input type="text" class="form-control" id="nom" name="nom" required />
    </div>

    <div class="form-group">
      <label for="email">Email</label>
      <input type="email" class="form-control" id="email" name="email" required />
    </div>

    <div class="form-group">
      <label for="motDePasse">Mot de passe</label>
      <input type="password" class="form-control" id="motDePasse" name="motDePasse" required minlength="6" />
    </div>

    <button type="submit" class="btn-primary">S'inscrire</button>
  </form>

  <div class="login-link">
    <p>Déjà un compte ? <a href="${pageContext.request.contextPath}/internaute?action=login">Se connecter</a></p>
  </div>
</div>

<jsp:include page="/WEB-INF/includes/footer.jsp"/>

<!-- Validation simple -->
<script>
  document.getElementById('registerForm').addEventListener('submit', function(e){
    const nom = document.getElementById('nom').value.trim();
    const email = document.getElementById('email').value.trim();
    const motDePasse = document.getElementById('motDePasse').value.trim();

    if (!nom || !email || !motDePasse) {
      e.preventDefault();
      alert('Veuillez remplir tous les champs.');
      return;
    }

    if (motDePasse.length < 6) {
      e.preventDefault();
      alert('Le mot de passe doit contenir au moins 6 caractères.');
    }
  });
</script>
