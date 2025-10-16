<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/includes/header.jsp"/>

<style>
  body {
    font-family: 'Poppins', sans-serif;
    background: linear-gradient(135deg, #ffe6f2, #fce4ec);
    margin: 0;
    padding: 0;
  }

  .password-container {
    max-width: 600px;
    margin: 3rem auto;
    background: #fff;
    padding: 2rem;
    border-radius: 16px;
    box-shadow: 0 4px 20px rgba(255, 182, 193, 0.4);
    transition: all 0.3s ease;
  }

  .password-container:hover {
    transform: translateY(-3px);
    box-shadow: 0 6px 24px rgba(255, 182, 193, 0.5);
  }

  h2 {
    color: #d63384;
    text-align: center;
    margin-bottom: 1.5rem;
    font-size: 1.8rem;
  }

  .password-container label {
    display: block;
    font-weight: 500;
    margin-bottom: 0.3rem;
    color: #c2185b;
  }

  .password-container input {
    width: 100%;
    padding: 0.6rem 0.75rem;
    border: 1px solid #ccc;
    border-radius: 12px;
    font-size: 1rem;
    transition: all 0.3s ease;
  }

  .password-container input:focus {
    border-color: #f06292;
    box-shadow: 0 0 8px rgba(240, 98, 146, 0.3);
    outline: none;
  }

  .btn-rose {
    display: inline-block;
    margin-top: 1rem;
    padding: 0.6rem 1.2rem;
    background: #f06292;
    color: #fff;
    border: none;
    border-radius: 12px;
    cursor: pointer;
    font-weight: 500;
    transition: all 0.3s ease;
  }

  .btn-rose:hover {
    background: #e91e63;
  }

  .message {
    padding: 0.75rem 1rem;
    border-radius: 12px;
    margin: 0.5rem 0;
    text-align: center;
    font-weight: 500;
  }

  .error {
    background: #fdecea;
    color: #b71c1c;
    border: 1px solid #f5c6cb;
  }

  .success {
    background: #e7f7ec;
    color: #1e7e34;
    border: 1px solid #b6e5c3;
  }
</style>

<div class="password-container">
  <h2>🔒 Changer mon mot de passe</h2>

  <c:if test="${not empty error}">
    <div class="message error">${error}</div>
  </c:if>
  <c:if test="${param.updated eq 'true'}">
    <div class="message success">Mot de passe mis à jour ✅</div>
  </c:if>

  <form method="post" action="${pageContext.request.contextPath}/internaute" id="passwordForm">
    <input type="hidden" name="action" value="changePassword"/>

    <div style="margin-top:1rem;">
      <label>Nouveau mot de passe</label>
      <input type="password" name="newPassword" minlength="6" required/>
    </div>
    <div style="margin-top:1rem;">
      <label>Confirmer le mot de passe</label>
      <input type="password" name="confirmPassword" minlength="6" required/>
    </div>

    <button type="submit" class="btn-rose">🔑 Mettre à jour</button>
  </form>
</div>

<script>
  document.getElementById('passwordForm').addEventListener('submit', function(e) {
    const pwd = this.newPassword.value.trim();
    const confirmPwd = this.confirmPassword.value.trim();

    if (pwd.length < 6) {
      alert('Le mot de passe doit comporter au moins 6 caractères.');
      e.preventDefault();
      return;
    }
    if (pwd !== confirmPwd) {
      alert('Les mots de passe ne correspondent pas.');
      e.preventDefault();
    }
  });
</script>

<jsp:include page="/WEB-INF/includes/footer.jsp"/>
