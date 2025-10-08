<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="ma.fstt.entities.Produit" %>

<%
    List<Produit> produits = (List<Produit>) request.getAttribute("produits");
%>

<html>
<head>
    <title>Nos Produits</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">

    <style>
        body {
            font-family: 'Roboto', sans-serif;
            background: linear-gradient(135deg, #6a11cb, #2575fc);
            min-height: 100vh;
            color: #fff;
            padding: 20px;
        }

        h1 {
            text-align: center;
            margin-bottom: 40px;
            text-shadow: 2px 2px 8px rgba(0,0,0,0.3);
        }

        table {
            width: 100%;
            border-collapse: collapse;
            background: #4a00e0;
            border-radius: 10px;
            overflow: hidden;
            margin-bottom: 40px;
            box-shadow: 0 4px 20px rgba(0,0,0,0.3);
        }

        th, td {
            padding: 15px;
            text-align: left;
            border-bottom: 1px solid rgba(255,255,255,0.2);
        }

        th {
            background: linear-gradient(135deg, #8e2de2, #4a00e0);
            font-size: 1.1em;
        }

        tr:hover {
            background-color: rgba(255,255,255,0.1);
            transition: 0.3s;
        }

        button {
            padding: 8px 15px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-weight: bold;
        }

        .delete-btn {
            background: #ff4d4d;
            color: #fff;
        }

        .update-btn {
            background: #ffc107;
            color: #000;
        }

        .add-form {
            background: linear-gradient(145deg, #8e2de2, #4a00e0);
            padding: 20px;
            border-radius: 20px;
            max-width: 500px;
            margin: 0 auto;
            box-shadow: 0 4px 20px rgba(0,0,0,0.3);
        }

        .add-form input {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border-radius: 10px;
            border: none;
        }

        .add-form button {
            width: 100%;
            background: #28a745;
            color: #fff;
            font-size: 16px;
        }

        .add-form button:hover {
            background: #218838;
        }

        .back-btn {
            display: block;
            margin: 30px auto 0 auto;
            background: #6f42c1;
            color: #fff;
            border-radius: 10px;
            padding: 12px 20px;
            font-size: 16px;
            text-decoration: none;
            text-align: center;
            width: 220px;
            transition: all 0.3s;
        }

        .back-btn:hover {
            background: #5a32a3;
            transform: scale(1.05);
        }

        /* Modale update */
        .modal {
            display: none;
            position: fixed;
            top: 0; left: 0;
            width: 100%; height: 100%;
            background: rgba(0,0,0,0.7);
            justify-content: center;
            align-items: center;
        }

        .modal-content {
            background: linear-gradient(135deg, #6a11cb, #2575fc);
            padding: 20px;
            border-radius: 20px;
            width: 400px;
            text-align: center;
            color: #fff;
            box-shadow: 0 4px 25px rgba(0,0,0,0.5);
        }

        .modal input {
            width: 90%;
            padding: 10px;
            margin-bottom: 15px;
            border-radius: 10px;
            border: none;
        }

        .modal button {
            margin: 5px;
        }
    </style>
</head>
<body>

<h1>Nos Produits</h1>

<!-- Tableau des produits -->
<table id="produitTable">
    <thead>
    <tr>
        <th>ID</th>
        <th>Nom Produit</th>
        <th>Prix (DH)</th>
        <th>Stock</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <% if (produits != null) {
        for (Produit p : produits) { %>
    <tr data-id="<%= p.getIdProduit() %>">
        <td><%= p.getIdProduit() %></td>
        <td class="nomProduit"><%= p.getNomProduit() %></td>
        <td class="prix"><%= p.getPrix() %></td>
        <td class="stock"><%= p.getStock() %></td>
        <td>
            <button class="update-btn">Modifier</button>
            <button class="delete-btn">Supprimer</button>
        </td>
    </tr>
    <%  }
    } %>
    </tbody>
</table>

<!-- Formulaire ajout produit -->
<div class="add-form">
    <h2>Ajouter un nouveau produit</h2>
    <input type="text" id="newNomProduit" placeholder="Nom du produit" required>
    <input type="number" id="newPrix" placeholder="Prix" required>
    <input type="number" id="newStock" placeholder="Stock" required>
    <button id="addProduitBtn">Ajouter</button>
</div>

<!-- Modale update -->
<div class="modal" id="updateModal">
    <div class="modal-content">
        <h2>Modifier Produit</h2>
        <input type="hidden" id="updateId">
        <input type="text" id="updateNomProduit" placeholder="Nom Produit">
        <input type="number" id="updatePrix" placeholder="Prix">
        <input type="number" id="updateStock" placeholder="Stock">
        <br>
        <button id="saveUpdateBtn">Enregistrer</button>
        <button id="closeModalBtn">Annuler</button>
    </div>
</div>

<!-- Bouton retour -->
<a href="${pageContext.request.contextPath}/index.jsp" class="back-btn">⬅ Retour à l'accueil</a>

<script>
    $(document).ready(function() {

        // Ajouter nouveau produit
        $("#addProduitBtn").click(function() {
            var nomProduit = $("#newNomProduit").val();
            var prix = $("#newPrix").val();
            var stock = $("#newStock").val();

            if(!nomProduit || !prix || !stock) {
                alert("Veuillez remplir tous les champs !");
                return;
            }

            $.ajax({
                url: '${pageContext.request.contextPath}/produits',
                type: 'POST',
                data: {action: 'add', nomProduit: nomProduit, prix: prix, stock: stock},
                success: function() {
                    location.reload();
                },
                error: function() {
                    alert("Erreur lors de l'ajout du produit !");
                }
            });
        });

        // Supprimer produit
        $(".delete-btn").click(function() {
            var row = $(this).closest("tr");
            var id = row.data("id");

            if(confirm("Voulez-vous vraiment supprimer ce produit ?")) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/produits',
                    type: 'POST',
                    data: {action: 'delete', idProduit: id},
                    success: function() {
                        row.remove();
                    },
                    error: function() {
                        alert("Erreur lors de la suppression !");
                    }
                });
            }
        });

        // Ouvrir modale update
        $(".update-btn").click(function() {
            var row = $(this).closest("tr");
            $("#updateId").val(row.data("id"));
            $("#updateNomProduit").val(row.find(".nomProduit").text());
            $("#updatePrix").val(row.find(".prix").text());
            $("#updateStock").val(row.find(".stock").text());
            $("#updateModal").fadeIn();
        });

        // Fermer modale
        $("#closeModalBtn").click(function() {
            $("#updateModal").fadeOut();
        });

        // Enregistrer modification
        $("#saveUpdateBtn").click(function() {
            var id = $("#updateId").val();
            var nomProduit = $("#updateNomProduit").val();
            var prix = $("#updatePrix").val();
            var stock = $("#updateStock").val();

            $.ajax({
                url: '${pageContext.request.contextPath}/produits',
                type: 'POST',
                data: {action: 'update', idProduit: id, nomProduit: nomProduit, prix: prix, stock: stock},
                success: function() {
                    location.reload();
                },
                error: function() {
                    alert("Erreur lors de la mise à jour !");
                }
            });
        });
    });
</script>

</body>
</html>
