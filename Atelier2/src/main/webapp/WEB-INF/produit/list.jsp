<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 15/10/2025
  Time: 13:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Profuiyd</h1>


<c:forEach var="p" items="${produits}">
    <tr>
        <td>${p.id}</td>
        <td>${p.nom}</td>
        <td>${p.description}</td>
        <td><fmt:formatNumber value="${p.prix}" type="currency" currencySymbol="DH"/></td>
        <td class="${p.stock < 10 ? 'stock-low' : 'stock-ok'}">${p.stock}</td>
        <td>
            <a href="${pageContext.request.contextPath}/panier?action=ajouter&id=${p.id}" class="btn btn-success">Ajouter au Panier</a>
            <a href="#" class="btn btn-danger delete-btn" data-id="${p.id}">Supprimer</a>
        </td>
    </tr>
</c:forEach>

</body>
</html>
