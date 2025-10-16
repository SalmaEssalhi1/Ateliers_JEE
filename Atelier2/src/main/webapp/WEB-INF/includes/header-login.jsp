<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!-- 🌸 HEADER STYLE ROSE CLAIR -->
<style>
  @import url('https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap');

  header.main-header {
    width: 100%;
    background: linear-gradient(135deg, #fff0f6, #ffe3ec);
    box-shadow: 0 2px 12px rgba(255, 182, 193, 0.25);
    padding: 1.2rem 0;
    border-bottom: 2px solid #f8bbd0;
  }

  header .content {
    max-width: 1200px;
    margin: 0 auto;
    display: flex;
    justify-content: center;
    align-items: center;
    text-align: center;
    flex-direction: column;
  }

  header .brand {
    font-size: 2.2rem;
    font-family: 'Poppins', sans-serif;
    color: #d63384;
    font-weight: 700;
    letter-spacing: 1px;
  }

  header .subtitle {
    color: #555;
    font-size: 1rem;
    margin-top: 0.3rem;
    font-weight: 400;
  }

  /* Animation d’apparition */
  @keyframes fadeDown {
    from { opacity: 0; transform: translateY(-20px); }
    to { opacity: 1; transform: translateY(0); }
  }
  header.main-header {
    animation: fadeDown 0.6s ease forwards;
  }
</style>

<header class="main-header">
  <div class="content">
    <div class="brand">SalmaStore</div>
    <div class="subtitle">Découvrez l’univers raffiné de SalmaStore</div>
  </div>
</header>
