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

    h2 {
        color: #d63384;
        text-align: center;
        margin-bottom: 2rem;
        font-size: 1.8rem;
    }

    .panier-container {
        max-width: 1000px;
        margin: 2rem auto;
        background: #fff;
        padding: 2rem;
        border-radius: 16px;
        box-shadow: 0 4px 20px rgba(255, 182, 193, 0.4);
        transition: all 0.3s ease-in-out;
    }

    .panier-container:hover {
        transform: translateY(-3px);
        box-shadow: 0 6px 24px rgba(255, 182, 193, 0.5);
    }

    table {
        width: 100%;
        border-collapse: collapse;
        margin-top: 1rem;
    }

    th {
        background-color: #f8bbd0;
        color: #fff;
        text-align: center;
        padding: 0.75rem;
        font-weight: 600;
    }

    td {
        padding: 0.75rem;
        text-align: center;
        border-bottom: 1px solid #f1f1f1;
        color: #555;
    }

    a {
        text-decoration: none;
        transition: 0.3s;
    }

    .btn {
        display: inline-block;
        padding: 0.5rem 0.75rem;
        border-radius: 8px;
        font-weight: 500;
        text-align: center;
    }

    .btn-rose {
        background: #f06292;
        color: #fff;
    }

    .btn-rose:hover {
        background: #e91e63;
    }

    .btn-outline {
        background: #ffe6f2;
        color: #d63384;
        border: 1px solid #f48fb1;
    }

    .btn-outline:hover {
        background: #f8bbd0;
        color: #fff;
    }

    .qty-btn {
        background: #fce4ec;
        padding: 0.25rem 0.5rem;
        border-radius: 6px;
        color: #d63384;
        font-weight: bold;
    }

    .qty-btn:hover {
        background: #f8bbd0;
    }

    .empty {
        padding: 1rem;
        border: 2px dashed #f8bbd0;
        border-radius: 10px;
        text-align: center;
        color: #d63384;
        background: #fff0f5;
        font-size: 1rem;
    }

    .footer-actions {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-top: 1.5rem;
    }

    .total {
        font-weight: bold;
        color: #c2185b;
        font-size: 1.2rem;
    }
</style>

<div class="panier-container">
    <h2>🛍️ Mon Panier</h2>

    <c:choose>
        <c:when test="${empty panier.lignes}">
            <div class="empty">Votre panier est vide pour le moment 💖</div>
        </c:when>

        <c:otherwise>
            <table>
                <thead>
                <tr>
                    <th>Produit</th>
                    <th>Prix unitaire</th>
                    <th>Quantité</th>
                    <th>Total</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="ligne" items="${panier.lignes}">
                    <tr>
                        <td>${ligne.produit.nom}</td>
                        <td>${ligne.produit.prix} DH</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/panier?action=dec&id=${ligne.produit.id}" class="qty-btn">−</a>
                            <span style="margin: 0 8px;">${ligne.quantite}</span>
                            <a href="${pageContext.request.contextPath}/panier?action=inc&id=${ligne.produit.id}" class="qty-btn">+</a>
                        </td>
                        <td>${ligne.produit.prix * ligne.quantite} DH</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/panier?action=supprimer&id=${ligne.produit.id}" style="color:#e91e63;font-weight:bold;">🗑 Supprimer</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
                <tfoot>
                <tr>
                    <td colspan="3" class="total">Total général</td>
                    <td class="total">${panier.total} DH</td>
                    <td></td>
                </tr>
                </tfoot>
            </table>

            <div class="footer-actions">
                <a href="${pageContext.request.contextPath}/accueil" class="btn btn-outline">← Continuer mes achats</a>
                <a href="${pageContext.request.contextPath}/panier?action=vider" class="btn btn-rose">🗑 Vider le panier</a>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<jsp:include page="/WEB-INF/includes/footer.jsp"/>
