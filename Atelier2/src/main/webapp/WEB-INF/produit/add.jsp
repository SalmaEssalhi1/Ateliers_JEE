<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 15/10/2025
  Time: 13:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

</body>
</html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<jsp:include page="/WEB-INF/includes/header.jsp" />
<jsp:include page="/WEB-INF/includes/navbar.jsp" />

<main class="container">
  <div class="card form-card animate-fadein">
    <h2>Ajouter un produit</h2>
    <form action="${pageContext.request.contextPath}/produit?action=add" method="post" id="produitForm" autocomplete="off">
      <div class="form-group">
        <label for="nom">Nom</label>
        <input type="text" id="nom" name="nom" class="form-control" required>
      </div>
      <div class="form-group">
        <label for="description">Description</label>
        <input type="text" id="description" name="description" class="form-control" required>
      </div>
      <div class="form-group">
        <label for="prix">Prix</label>
        <input type="number" id="prix" name="prix" class="form-control" step="0.01" min="0" required>
      </div>
      <div class="form-group">
        <label for="stock">Stock</label>
        <input type="number" id="stock" name="stock" class="form-control" min="0" required>
      </div>
      <div class="form-actions">
        <button type="submit" class="btn btn-add">Ajouter</button>
        <a href="${pageContext.request.contextPath}/produit?action=list" class="btn btn-cancel">Annuler</a>
      </div>
    </form>
    <div id="form-message" class="form-message" style="display:none;"></div>
  </div>
</main>

<jsp:include page="/WEB-INF/includes/footer.jsp" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/main.js"></script>
