<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="ma.fstt.entities.Client" %>
<%@ page import="ma.fstt.service.ClientService" %>
<%
    ClientService clientService = new ClientService();
    List<Client> clients = clientService.findAll();
%>
<html>
<head>
    <title>Nos Clients</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            background: linear-gradient(135deg, #6a11cb, #2575fc);
            min-height: 100vh;
            color: #fff;
            padding: 30px;
        }

        h1 {
            text-align: center;
            margin-bottom: 40px;
            text-shadow: 2px 2px 8px rgba(0,0,0,0.3);
        }

        /* ======= Bouton retour ======= */
        .back-btn {
            display: inline-block;
            background: linear-gradient(90deg, #8e2de2, #4a00e0);
            color: white;
            font-weight: bold;
            padding: 10px 25px;
            border-radius: 30px;
            border: none;
            cursor: pointer;
            transition: all 0.3s ease;
            text-decoration: none;
            box-shadow: 0px 4px 15px rgba(0,0,0,0.3);
            margin-bottom: 30px;
        }

        .back-btn:hover {
            transform: scale(1.05);
            background: linear-gradient(90deg, #a64bf4, #5b00ff);
        }

        /* ======= Tableau ======= */
        table {
            width: 100%;
            border-collapse: collapse;
            background: #4a00e0;
            border-radius: 10px;
            overflow: hidden;
            margin-bottom: 40px;
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
        }

        button {
            padding: 8px 15px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-weight: bold;
            transition: all 0.3s ease;
        }

        .delete-btn {
            background: #ff4d4d;
            color: #fff;
        }

        .delete-btn:hover {
            background: #ff1a1a;
            transform: scale(1.05);
        }

        .update-btn {
            background: #ffc107;
            color: #000;
        }

        .update-btn:hover {
            background: #ffdb4d;
            transform: scale(1.05);
        }

        /* ======= Formulaire d'ajout ======= */
        .add-form {
            background: linear-gradient(145deg, #8e2de2, #4a00e0);
            padding: 20px;
            border-radius: 20px;
            max-width: 500px;
            margin: 0 auto;
            box-shadow: 0px 4px 20px rgba(0,0,0,0.2);
        }

        .add-form input {
            width: 100%;
            padding: 10px;
            margin-bottom: 5px;
            border-radius: 10px;
            border: none;
        }

        .add-form span {
            display: block;
            margin-bottom: 10px;
            font-size: 0.9em;
            color: #ff6b81;
        }

        .add-form button {
            width: 100%;
            background: #28a745;
            color: #fff;
            font-size: 16px;
            margin-top: 10px;
        }

        .add-form button:hover {
            background: #218838;
        }

        /* ======= Modale update ======= */
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
            background: #6a11cb;
            padding: 20px;
            border-radius: 20px;
            width: 400px;
            text-align: center;
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

<!-- Bouton retour -->
<a href="${pageContext.request.contextPath}/index.jsp" class="back-btn">← Retour à l'accueil</a>

<h1>Nos Clients</h1>

<!-- Tableau des clients -->
<table id="clientTable">
    <thead>
    <tr>
        <th>ID</th>
        <th>Nom</th>
        <th>Prénom</th>
        <th>Email</th>
        <th>Adresse</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <% for(Client c : clients) { %>
    <tr data-id="<%= c.getIdClient() %>">
        <td><%= c.getIdClient() %></td>
        <td class="nom"><%= c.getNom() %></td>
        <td class="prenom"><%= c.getPrenom() %></td>
        <td class="email"><%= c.getEmail() %></td>
        <td class="adresse"><%= c.getAdresse() %></td>
        <td>
            <button class="update-btn">Modifer</button>
            <button class="delete-btn">Supprimer</button>
        </td>
    </tr>
    <% } %>
    </tbody>
</table>

<!-- Formulaire ajout client -->
<div class="add-form">
    <h2>Ajouter un nouveau client</h2>
    <input type="text" id="newNom" placeholder="Nom" required>
    <input type="text" id="newPrenom" placeholder="Prénom" required>
    <input type="email" id="newEmail" placeholder="Email" required>
    <span id="emailMsg"></span>
    <input type="text" id="newAdresse" placeholder="Adresse" required>
    <button id="addClientBtn">Ajouter</button>
</div>

<!-- Modale update -->
<div class="modal" id="updateModal">
    <div class="modal-content">
        <h2>Modifier Client</h2>
        <input type="hidden" id="updateId">
        <input type="text" id="updateNom" placeholder="Nom">
        <input type="text" id="updatePrenom" placeholder="Prénom">
        <input type="email" id="updateEmail" placeholder="Email">
        <input type="text" id="updateAdresse" placeholder="Adresse">
        <br>
        <button id="saveUpdateBtn">Enregistrer</button>
        <button id="closeModalBtn">Annuler</button>
    </div>
</div>

<script>
    $(document).ready(function() {

        // Validation email en temps réel
        $("#newEmail").on("input", function() {
            var email = $(this).val();
            var pattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if(!pattern.test(email)) {
                $("#emailMsg").text("Format d'email invalide !");
            } else {
                $("#emailMsg").text("");
            }
        });

        // Ajouter nouveau client
        $("#addClientBtn").click(function() {
            var nom = $("#newNom").val();
            var prenom = $("#newPrenom").val();
            var email = $("#newEmail").val();
            var adresse = $("#newAdresse").val();
            var pattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

            if(!nom || !prenom || !email || !adresse) {
                alert("Veuillez remplir tous les champs !");
                return;
            }

            if(!pattern.test(email)) {
                alert("Veuillez entrer un email valide !");
                return;
            }

            $.ajax({
                url: '${pageContext.request.contextPath}/clients',
                type: 'POST',
                data: {action: 'add', nom: nom, prenom: prenom, email: email, adresse: adresse},
                success: function() {
                    location.reload();
                },
                error: function() {
                    alert("Erreur lors de l'ajout du client !");
                }
            });
        });

        // Supprimer client
        $(".delete-btn").click(function() {
            var row = $(this).closest("tr");
            var id = row.data("id");

            if(confirm("Voulez-vous vraiment supprimer ce client ?")) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/clients',
                    type: 'POST',
                    data: {action: 'delete', idClient: id},
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
            $("#updateNom").val(row.find(".nom").text());
            $("#updatePrenom").val(row.find(".prenom").text());
            $("#updateEmail").val(row.find(".email").text());
            $("#updateAdresse").val(row.find(".adresse").text());
            $("#updateModal").fadeIn();
        });

        // Fermer modale
        $("#closeModalBtn").click(function() {
            $("#updateModal").fadeOut();
        });

        // Enregistrer modification
        $("#saveUpdateBtn").click(function() {
            var id = $("#updateId").val();
            var nom = $("#updateNom").val();
            var prenom = $("#updatePrenom").val();
            var email = $("#updateEmail").val();
            var adresse = $("#updateAdresse").val();
            var pattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

            if(!pattern.test(email)) {
                alert("Veuillez entrer un email valide !");
                return;
            }

            $.ajax({
                url: '${pageContext.request.contextPath}/clients',
                type: 'POST',
                data: {action: 'update', idClient: id, nom: nom, prenom: prenom, email: email, adresse: adresse},
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
