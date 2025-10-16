<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav class="navbar" style="display:flex;gap:1rem;align-items:center;justify-content:space-between;padding:0.75rem 1rem;background:#f8f9fa;border-bottom:1px solid #eee;">
  <div style="display:flex;gap:1rem;align-items:center;">
    <a href="${pageContext.request.contextPath}/accueil" style="text-decoration:none;color:#007bff;font-weight:600;">Accueil</a>
    <a href="${pageContext.request.contextPath}/panier?action=afficher" style="text-decoration:none;color:#007bff;">Panier</a>
  </div>
</nav>