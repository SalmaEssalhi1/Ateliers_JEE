<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- 🌸 STYLE HEADER ROSE CLAIR -->
<style>
  @import url('https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap');

  header.main-header {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    background: linear-gradient(135deg, #fff0f6, #ffe3ec);
    box-shadow: 0 3px 10px rgba(255, 182, 193, 0.25);
    z-index: 1000;
    border-bottom: 2px solid #f8bbd0;
    font-family: 'Poppins', sans-serif;
    animation: fadeDown 0.6s ease forwards;
  }

  header .content {
    display: flex;
    justify-content: space-between;
    align-items: center;
    max-width: 1200px;
    margin: 0 auto;
    padding: 0.8rem 1.5rem;
    gap: 2rem;
  }

  /* LOGO */
  header .logo {
    display: flex;
    align-items: center;
    gap: 0.6rem;
    text-decoration: none;
  }

  header .logo i {
    font-size: 2rem;
    color: #d63384;
  }

  header .logo span {
    font-size: 1.9rem;
    color: #d63384;
    font-weight: 600;
  }

  /* BARRE DE RECHERCHE */
  header form.search-bar {
    flex: 1;
    max-width: 480px;
    display: flex;
    align-items: center;
    gap: 0.5rem;
  }

  header form.search-bar input {
    width: 100%;
    padding: 0.7rem 1rem;
    border-radius: 12px;
    border: 1px solid #f8bbd0;
    background: #fff8fb;
    font-size: 1rem;
    transition: all 0.3s ease;
  }

  header form.search-bar input:focus {
    border-color: #f06292;
    box-shadow: 0 0 0 3px rgba(240, 98, 146, 0.15);
    outline: none;
  }

  header form.search-bar button {
    background: linear-gradient(135deg, #f06292, #ec407a);
    border: none;
    color: #fff;
    padding: 0.6rem 1rem;
    border-radius: 12px;
    cursor: pointer;
    transition: background 0.3s ease, transform 0.2s ease;
  }

  header form.search-bar button:hover {
    background: linear-gradient(135deg, #ec407a, #d81b60);
    transform: translateY(-1px);
  }

  /* PANIER + PROFIL */
  header .actions {
    display: flex;
    align-items: center;
    gap: 2rem;
  }

  /* Panier */
  header .cart {
    position: relative;
    color: #d63384;
    font-size: 1.6rem;
    text-decoration: none;
    transition: transform 0.2s ease;
  }

  header .cart:hover {
    transform: scale(1.1);
  }

  header .cart span {
    position: absolute;
    top: -8px;
    right: -12px;
    background: #d63384;
    color: #fff;
    font-size: 0.85rem;
    padding: 2px 6px;
    border-radius: 50%;
    font-weight: bold;
  }

  /* Profil */
  .profile-menu {
    position: relative;
  }

  .profile-menu button {
    background: none;
    border: none;
    cursor: pointer;
    display: flex;
    align-items: center;
    gap: 0.4rem;
    font-size: 1.1rem;
    color: #d63384;
    font-weight: 500;
    transition: color 0.3s ease;
  }

  .profile-menu button:hover {
    color: #ad1457;
  }

  .profile-dropdown {
    display: none;
    position: absolute;
    right: 0;
    top: 110%;
    background: #ffffff;
    border-radius: 12px;
    box-shadow: 0 4px 12px rgba(255, 182, 193, 0.25);
    padding: 1rem;
    min-width: 200px;
    z-index: 100;
    animation: fadeIn 0.3s ease forwards;
  }

  .profile-dropdown a {
    display: flex;
    align-items: center;
    gap: 0.6rem;
    color: #333;
    text-decoration: none;
    padding: 0.5rem 0;
    font-size: 0.95rem;
    transition: color 0.3s ease;
  }

  .profile-dropdown a:hover {
    color: #d63384;
  }

  /* Animation */
  @keyframes fadeIn {
    from { opacity: 0; transform: translateY(5px); }
    to { opacity: 1; transform: translateY(0); }
  }
  @keyframes fadeDown {
    from { opacity: 0; transform: translateY(-20px); }
    to { opacity: 1; transform: translateY(0); }
  }

  /* Responsive */
  @media (max-width: 768px) {
    header .content {
      flex-direction: column;
      align-items: stretch;
      gap: 1rem;
    }
    header form.search-bar {
      max-width: 100%;
    }
  }
</style>

<header class="main-header">
  <div class="content">

    <!-- Logo -->
    <a href="${pageContext.request.contextPath}/accueil" class="logo">
      <i class="fa-solid fa-store"></i>
      <span>SalmaStore</span>
    </a>

    <!-- Barre de recherche -->
    <form method="get" action="${pageContext.request.contextPath}/accueil" class="search-bar">
      <input type="text" name="q" value="${searchQuery}" placeholder="Rechercher un produit..." />
      <button type="submit"><i class="fa-solid fa-search"></i></button>
    </form>

    <!-- Panier & Profil -->
    <div class="actions">
      <!-- Panier -->
      <a href="${pageContext.request.contextPath}/panier?action=afficher" class="cart">
        <i class="fa-solid fa-cart-shopping"></i>
        <span>
          <c:out value="${sessionScope.panier != null ? sessionScope.panier.lignes.size() : 0}"/>
        </span>
      </a>

      <!-- Profil -->
      <div class="profile-menu">
        <button id="profileBtn">
          <i class="fa-solid fa-user"></i>
          <span><c:out value="${sessionScope.internaute != null ? sessionScope.internaute.nom : 'Profil'}"/></span>
          <i class="fa-solid fa-chevron-down" style="font-size:0.85rem;"></i>
        </button>
        <div id="profileDropdown" class="profile-dropdown">
          <a href="${pageContext.request.contextPath}/internaute?action=profile">
            <i class="fa-solid fa-user-gear"></i> Profil
          </a>
          <a href="${pageContext.request.contextPath}/internaute?action=changePasswordForm">
            <i class="fa-solid fa-lock"></i> Changer mot de passe
          </a>
          <a href="${pageContext.request.contextPath}/internaute?action=logout">
            <i class="fa-solid fa-right-from-bracket"></i> Se déconnecter
          </a>
        </div>
      </div>
    </div>
  </div>
</header>

<!-- Font Awesome pour les icônes -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"/>

<!-- 🌸 Script JS pour menu déroulant -->
<script>
  document.addEventListener('DOMContentLoaded', function() {
    const btn = document.getElementById('profileBtn');
    const dropdown = document.getElementById('profileDropdown');

    if (btn && dropdown) {
      btn.addEventListener('click', function(e) {
        e.stopPropagation();
        dropdown.style.display = dropdown.style.display === 'block' ? 'none' : 'block';
      });
      document.addEventListener('click', function() {
        dropdown.style.display = 'none';
      });
    }
  });
</script>
