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

  .profil-container {
    max-width: 700px;
    margin: 3rem auto;
    background: #fff;
    padding: 2rem;
    border-radius: 16px;
    box-shadow: 0 4px 20px rgba(255, 182, 193, 0.4);
    transition: all 0.3s ease;
  }

  .profil-container:hover {
    transform: translateY(-3px);
    box-shadow: 0 6px 24px rgba(255, 182, 193, 0.5);
  }

  h2 {
    color: #d63384;
    text-align: center;
    margin-bottom: 2rem;
    font-size: 1.8rem;
    letter-spacing: 0.5px;
  }

  .info {
    margin: 1rem 0;
    font-size: 1.1rem;
    color: #555;
    background: #fff0f6;
    padding: 0.8rem 1rem;
    border-radius: 10px;
    border-left: 4px solid #f48fb1;
    transition: background 0.3s;
  }

  .info:hover {
    background: #ffe6f2;
  }

  b {
    color: #c2185b;
  }

  .actions {
    margin-top: 2rem;
    display: flex;
    justify-content: center;
    gap: 1rem;
  }

  .btn {
    padding: 0.6rem 1rem;
    border-radius: 10px;
    text-decoration: none;
    font-weight: 500;
    transition: all 0.3s ease;
  }

  .btn-rose {
    background: #f06292;
    color: #fff;
  }

  .btn-rose:hover {
    background: #e91e63;
  }

  .btn-grey {
    background: #f8bbd0;
    color: #fff;
  }

  .btn-grey:hover {
    background: #d81b60;
  }
</style>

<div class="profil-container">
  <h2>👩 Mon Profil</h2>

  <c:set var="u" value="${internaute}" />

  <div class="info">
    <b>Nom :</b> ${u.nom}
  </div>
  <div class="info">
    <b>Email :</b> ${u.email}
  </div>

  <div class="actions">
    <a href="${pageContext.request.contextPath}/internaute?action=editForm&id=${u.id}" class="btn btn-rose">✏️ Modifier mes informations</a>
    <a href="${pageContext.request.contextPath}/internaute?action=changePasswordForm" class="btn btn-grey">🔒 Changer mon mot de passe</a>
  </div>
</div>

<jsp:include page="/WEB-INF/includes/footer.jsp"/>
