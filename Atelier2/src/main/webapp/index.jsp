<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/includes/header.jsp"/>

<style>
    body {
        background: linear-gradient(135deg, #ffe6f0, #fff5fa);
        font-family: 'Poppins', sans-serif;
        margin: 0;
        padding-top: 100px;
    }

    .container {
        max-width: 1200px;
        margin: 2rem auto;
        padding: 1rem;
    }

    .alert-success {
        background: #fde7f2;
        color: #c2185b;
        border: 1px solid #f8bbd0;
        padding: 0.75rem 1rem;
        border-radius: 12px;
        margin: 0 auto 1.5rem;
        max-width: 600px;
        text-align: center;
        font-weight: 500;
    }

    .welcome {
        font-size: 1.2rem;
        color: #555;
        margin-bottom: 1.5rem;
        text-align: right;
    }

    .welcome b {
        color: #d63384;
    }

    h2.page-title {
        color: #d63384;
        text-align: center;
        margin-bottom: 2rem;
        font-size: 2rem;
        font-weight: bold;
    }

    h3.vitrine-title {
        color: #333;
        font-size: 1.4rem;
        margin: 2rem 0 1rem;
        border-left: 5px solid #f06292;
        padding-left: 10px;
    }

    .produits-grid {
        display: flex;
        flex-wrap: wrap;
        gap: 2rem;
        justify-content: center;
    }

    .produit-card {
        background: #fff;
        border-radius: 20px;
        box-shadow: 0 4px 15px rgba(240, 98, 146, 0.1);
        padding: 1.5rem;
        width: 260px;
        text-align: center;
        transition: all 0.3s ease;
    }

    .produit-card:hover {
        transform: translateY(-6px);
        box-shadow: 0 6px 25px rgba(240, 98, 146, 0.2);
    }

    .produit-card .nom {
        font-size: 1.2rem;
        font-weight: bold;
        color: #d63384;
        margin-bottom: 0.5rem;
    }

    .produit-card .desc {
        color: #666;
        font-size: 0.95rem;
        margin-bottom: 0.5rem;
    }

    .produit-card .prix {
        color: #f06292;
        font-size: 1.1rem;
        margin-bottom: 0.5rem;
    }

    .produit-card .stock {
        color: #999;
        font-size: 0.9rem;
        margin-bottom: 0.5rem;
    }

    .produit-card button {
        width: 100%;
        padding: 0.6rem;
        background: linear-gradient(135deg, #ff80ab, #f06292);
        color: #fff;
        border: none;
        border-radius: 12px;
        font-size: 1rem;
        cursor: pointer;
        transition: all 0.3s ease;
    }

    .produit-card button:hover {
        background: linear-gradient(135deg, #f06292, #ec407a);
        transform: translateY(-2px);
    }

    .no-results {
        text-align: center;
        color: #d63384;
        margin-top: 1.5rem;
        font-size: 1.1rem;
    }
</style>

<div class="container">
    <c:if test="${param.added eq 'true'}">
        <div class="alert-success">Produit ajouté au panier 💕</div>
    </c:if>

    <c:if test="${not empty sessionScope.internaute}">
        <div class="welcome">
            Heureuse de vous retrouver sur <b>SalmaStore</b>, ${sessionScope.internaute.nom} 🌸 !
        </div>
    </c:if>

    <h2 class="page-title">Explorez Notre Univers</h2>

    <c:forEach var="vitrine" items="${vitrines}">
        <h3 class="vitrine-title">${vitrine.nom}</h3>
        <div class="produits-grid">
            <c:forEach var="produit" items="${vitrine.produits}">
                <div class="produit-card">
                    <div class="nom">${produit.nom}</div>
                    <div class="desc">${produit.description}</div>
                    <div class="prix">${produit.prix} DH</div>
                    <div class="stock">Stock : ${produit.stock}</div>
                    <form method="post" action="${pageContext.request.contextPath}/panier" style="margin-top:0.75rem;">
                        <input type="hidden" name="action" value="ajouter"/>
                        <input type="hidden" name="id" value="${produit.id}"/>
                        <button type="submit">Ajouter au panier</button>
                    </form>
                </div>
            </c:forEach>
        </div>
    </c:forEach>

    <c:if test="${noResults}">
        <div class="no-results">Aucun produit trouvé pour votre recherche 💔</div>
    </c:if>
</div>

<jsp:include page="/WEB-INF/includes/footer.jsp"/>
