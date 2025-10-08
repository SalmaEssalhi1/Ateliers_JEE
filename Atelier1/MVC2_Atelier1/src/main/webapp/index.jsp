<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Gestion des Commandes</title>
  <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">
  <style>
    /* Global */
    body {
      margin: 0;
      font-family: 'Roboto', sans-serif;
      background: linear-gradient(135deg, #6a11cb, #2575fc);
      min-height: 100vh;
      display: flex;
      flex-direction: column;
      align-items: center;
      color: #fff;
    }

    h1 {
      margin-top: 40px;
      font-size: 3em;
      text-shadow: 2px 2px 8px rgba(0,0,0,0.3);
    }

    .container {
      display: flex;
      flex-wrap: wrap;
      justify-content: center;
      gap: 40px;
      margin-top: 60px;
      width: 90%;
    }

    .card {
      background: linear-gradient(145deg, #8e2de2, #4a00e0);
      width: 280px;
      height: 200px;
      border-radius: 20px;
      box-shadow: 0 10px 20px rgba(0,0,0,0.3);
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      text-align: center;
      transition: transform 0.3s, box-shadow 0.3s;
      cursor: pointer;
      position: relative;
      overflow: hidden;
    }

    .card:hover {
      transform: translateY(-10px);
      box-shadow: 0 20px 30px rgba(0,0,0,0.5);
    }

    .card h2 {
      margin: 0;
      font-size: 1.8em;
      margin-bottom: 10px;
      color: #fff;
      text-shadow: 1px 1px 6px rgba(0,0,0,0.4);
    }

    .card p {
      font-size: 1em;
      margin-bottom: 20px;
      color: #ddd;
    }

    .card button {
      padding: 10px 25px;
      border: none;
      border-radius: 50px;
      background: #ff6ec7;
      color: #fff;
      font-size: 16px;
      font-weight: bold;
      transition: all 0.3s;
    }

    .card button:hover {
      background: #ff3cac;
      transform: scale(1.1);
    }

    /* Responsive */
    @media (max-width: 900px) {
      .container {
        flex-direction: column;
        align-items: center;
      }
    }
  </style>
</head>
<body>

<h1>Gestion des Commandes</h1>

<div class="container">
  <!-- Gestion des Clients -->
  <div class="card" onclick="location.href='${pageContext.request.contextPath}/clients'">
    <h2>Clients</h2>
    <p>Gérer les informations des clients</p>
    <button>Voir détails</button>
  </div>

  <!-- Gestion des Produits -->
  <div class="card" onclick="location.href='${pageContext.request.contextPath}/produits'">
    <h2>Produits</h2>
    <p>Gérer les produits disponibles</p>
    <button>Voir détails</button>
  </div>

  <!-- Gestion des Commandes -->
  <div class="card" onclick="location.href='${pageContext.request.contextPath}/commandes'">
    <h2>Commandes</h2>
    <p>Consulter et gérer les commandes</p>
    <button>Voir détails</button>
  </div>
</div>

</body>
</html>
