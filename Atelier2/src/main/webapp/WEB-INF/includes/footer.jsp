<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>


<!-- 🌸 FOOTER STYLE ROSE CLAIR -->
<style>
  footer.main-footer {
    background: linear-gradient(135deg, #fff0f6, #ffe3ec);
    padding: 2rem 0;
    text-align: center;
    margin-top: 3rem;
    box-shadow: 0 -3px 12px rgba(255, 182, 193, 0.25);
    border-top: 2px solid #f8bbd0;
    font-family: 'Poppins', sans-serif;
  }

  /* Nom du site */
  footer .brand {
    font-size: 1.8rem;
    color: #d63384;
    font-weight: 700;
    letter-spacing: 0.5px;
  }

  /* Icônes sociales */
  footer .social-links {
    margin: 1rem 0;
  }

  footer .social-links a {
    margin: 0 12px;
    color: #d63384;
    font-size: 1.5rem;
    transition: transform 0.3s ease, color 0.3s ease;
  }

  footer .social-links a:hover {
    transform: translateY(-3px);
    color: #ad1457;
  }

  /* Texte du bas */
  footer p {
    color: #555;
    font-size: 0.9rem;
    margin-top: 0.5rem;
  }

  /* Animation d’apparition */
  @keyframes fadeUp {
    from { opacity: 0; transform: translateY(20px); }
    to { opacity: 1; transform: translateY(0); }
  }
  footer.main-footer {
    animation: fadeUp 0.6s ease forwards;
  }
</style>

<footer class="main-footer">
  <div class="brand">SalmaStore</div>

  <div class="social-links">
    <a href="https://instagram.com" target="_blank"><i class="fab fa-instagram"></i></a>
    <a href="https://facebook.com" target="_blank"><i class="fab fa-facebook"></i></a>
    <a href="https://x.com" target="_blank"><i class="fab fa-twitter"></i></a>
  </div>

  <p>&copy; 2025 SalmaStore. Tous droits réservés.</p>
</footer>

<!-- Font Awesome pour les icônes -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"/>
