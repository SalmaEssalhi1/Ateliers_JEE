<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="ma.fstt.entities.Commande" %>
<%@ page import="ma.fstt.entities.LigneCommande" %>
<%@ page import="ma.fstt.dao.LigneCommandeDAO" %>
<%@ page import="ma.fstt.entities.Produit" %>

<%
    List<Commande> commandes = (List<Commande>) request.getAttribute("commandes");
%>

<html>
<head>
    <title>Gestion des Commandes</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">

    <style>
        body { font-family: 'Roboto', sans-serif; background: linear-gradient(135deg, #6a11cb, #2575fc); min-height: 100vh; color: #fff; padding: 20px; }
        h1 { text-align: center; margin-bottom: 40px; text-shadow: 2px 2px 8px rgba(0,0,0,0.3); }
        table { width: 100%; border-collapse: collapse; background: #4a00e0; border-radius: 10px; overflow: hidden; margin-bottom: 40px; box-shadow: 0 4px 20px rgba(0,0,0,0.3);}
        th, td { padding: 15px; text-align: left; border-bottom: 1px solid rgba(255,255,255,0.2); }
        th { background: linear-gradient(135deg, #8e2de2, #4a00e0); font-size: 1.1em; }
        tr:hover { background-color: rgba(255,255,255,0.1); transition: 0.3s; }
        button { padding: 8px 15px; border: none; border-radius: 5px; cursor: pointer; font-weight: bold; }
        .details-btn { background: #00c6ff; color: #fff; }

        .add-form { background: linear-gradient(145deg, #8e2de2, #4a00e0); padding: 20px; border-radius: 20px; max-width: 500px; margin: 0 auto; box-shadow: 0 4px 20px rgba(0,0,0,0.3); }
        .add-form input { width: 100%; padding: 10px; margin-bottom: 15px; border-radius: 10px; border: none; }
        .add-form button { width: 100%; background: #28a745; color: #fff; font-size: 16px; }
        .add-form button:hover { background: #218838; }

        .back-btn { display: block; margin: 30px auto 0 auto; background: #6f42c1; color: #fff; border-radius: 10px; padding: 12px 20px; font-size: 16px; text-decoration: none; text-align: center; width: 220px; transition: all 0.3s; }
        .back-btn:hover { background: #5a32a3; transform: scale(1.05); }

        .toast { position: fixed; bottom: 30px; right: 30px; background: rgba(0,0,0,0.8); color: #fff; padding: 15px 20px; border-radius: 10px; display: none; font-size: 14px; z-index: 2000; }

        /* MODAL */
        .modal { display: none; position: fixed; top: 0; left: 0; width: 100%; height: 100%; background: rgba(0,0,0,0.7); z-index: 1000; }
        .modal-content { background: #4a00e0; margin: 5% auto; padding: 20px; border-radius: 15px; width: 80%; max-width: 900px; position: relative; color: #fff; max-height: 80vh; overflow-y: auto; }
        .close { position: absolute; top: 15px; right: 20px; font-size: 28px; font-weight: bold; cursor: pointer; color: #fff; }
        .close:hover { color: #ff0000; }

        .modal table { margin-top: 20px; }
        .modal input { width: 48%; padding: 8px; margin-right: 5px; border-radius: 8px; border: none; }
        .modal button.add-line { background: #007bff; color: #fff; margin-top: 10px; }
        .modal button.add-line:hover { background: #0056b3; }
        .modal button.delete-line { background: #dc3545; color: #fff; }
        .modal button.delete-line:hover { background: #c82333; }
        .total { text-align: right; margin-top: 15px; font-size: 18px; font-weight: bold; }
        .form-container { margin-top: 30px; padding-top: 20px; border-top: 2px solid rgba(255,255,255,0.2); }
        .form-container h3 { margin-bottom: 15px; }

    </style>
</head>
<body>

<h1>Liste des Commandes</h1>

<table id="commandeTable">
    <thead>
    <tr>
        <th>ID</th>
        <th>Date</th>
        <th>Client</th>
        <th>Prix Total (DH)</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <% if (commandes != null) {
        for (Commande c : commandes) { %>
    <tr data-id="<%= c.getIdCommande() %>">
        <td><%= c.getIdCommande() %></td>
        <td><%= c.getDateCommande() %></td>
        <td><%= c.getIdClient().getIdClient() %></td>
        <td><%= c.getPrixTotal() %></td>
        <td><button class="details-btn" data-id="<%= c.getIdCommande() %>" data-client="<%= c.getIdClient().getIdClient() %>">Détails</button></td>
    </tr>
    <%  } } %>
    </tbody>
</table>

<div class="add-form">
    <h2>Ajouter une commande</h2>
    <input type="date" id="newDateCommande" required>
    <input type="number" id="newClientId" placeholder="ID Client" required>
    <button id="addCommandeBtn">Ajouter</button>
</div>

<a href="<%= request.getContextPath() %>/index.jsp" class="back-btn">⬅ Retour à l'accueil</a>

<!-- MODAL -->
<div id="detailsModal" class="modal">
    <div class="modal-content">
        <span class="close">&times;</span>
        <h2 id="modalTitle"></h2>
        <table id="modalTable">
            <thead>
            <tr>
                <th>ID</th>
                <th>Produit ID</th>
                <th>Nom Produit</th>
                <th>Quantité</th>
                <th>Prix Unitaire</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody></tbody>
        </table>
        <div class="total">Total : <span id="modalTotal">0</span> DH</div>

        <div class="form-container">
            <h3>Ajouter un produit à la commande</h3>
            <input type="number" id="newProductId" placeholder="Produit ID" min="1">
            <input type="number" id="newQuantity" placeholder="Quantité" min="1">
            <button class="add-line">Ajouter la ligne</button>
        </div>
    </div>
</div>

<div class="toast" id="toast"></div>

<script>
    function showToast(message, color = '#28a745') {
        $("#toast").css("background", color).text(message).fadeIn(300);
        setTimeout(() => { $("#toast").fadeOut(300); }, 2500);
    }

    $(document).ready(function() {
        // MODAL
        var modal = $("#detailsModal");
        var span = $(".close");

        // FONCTION POUR CHARGER LES LIGNES DE COMMANDE
        function loadCommandeLines(idCommande, clientId) {
            $("#modalTable tbody").empty();
            $("#modalTotal").text("0");

            console.log("Chargement des lignes pour commande:", idCommande, "client:", clientId);

            $.ajax({
                url: '<%= request.getContextPath() %>/lignes',
                type: 'GET',
                data: {
                    clientId: clientId,
                    commandeId: idCommande
                },
                dataType: 'json',
                success: function(data){
                    console.log("Lignes chargées:", data);

                    // Si la réponse est un objet erreur
                    if (data && typeof data === 'object' && data.error) {
                        showToast("Erreur de chargement: " + data.error, "#dc3545");
                        $("#modalTable tbody").html("<tr><td colspan='6' style='text-align:center; padding:20px; color:#ff6b6b;'>"+data.error+"</td></tr>");
                        $("#modalTotal").text("0.00");
                        return;
                    }

                    // Vérifier si data est un tableau
                    if (!Array.isArray(data)) {
                        console.warn("Les données reçues ne sont pas un tableau:", data);
                        data = [];
                    }

                    if (data.length === 0) {
                        $("#modalTable tbody").html("<tr><td colspan='6' style='text-align:center; padding:20px;'>Aucun produit dans cette commande</td></tr>");
                        $("#modalTotal").text("0.00");
                        return;
                    }

                    var total = 0;
                    data.forEach(function(ligne){
                        var row = "<tr>";
                        row += "<td>"+ligne.idLigne+"</td>";
                        row += "<td>"+ligne.idProduit+"</td>";
                        row += "<td>"+ligne.nomProduit+"</td>";
                        row += "<td>"+ligne.quantite+"</td>";
                        row += "<td>"+ligne.prixUnitaire+"</td>";
                        row += "<td><button class='delete-line' data-id='"+ligne.idLigne+"'>Supprimer</button></td>";
                        row += "</tr>";
                        $("#modalTable tbody").append(row);
                        total += ligne.quantite * ligne.prixUnitaire;
                    });
                    $("#modalTotal").text(total.toFixed(2));
                },
                error: function(xhr, status, error){
                    console.error("Erreur chargement lignes:", {
                        status: xhr.status,
                        statusText: xhr.statusText,
                        responseText: xhr.responseText,
                        error: error
                    });

                    // Afficher un message approprié selon le type d'erreur
                    if (xhr.status === 404) {
                        $("#modalTable tbody").html("<tr><td colspan='6' style='text-align:center; padding:20px;'>Aucun produit dans cette commande</td></tr>");
                        $("#modalTotal").text("0.00");
                    } else {
                        showToast("Erreur de chargement: " + (xhr.responseText || "Erreur serveur"), "#dc3545");
                        $("#modalTable tbody").html("<tr><td colspan='6' style='text-align:center; padding:20px; color:#ff6b6b;'>Erreur de chargement</td></tr>");
                    }
                }
            });
        }

        // AJOUT COMMANDE
        $("#addCommandeBtn").click(function() {
            var date = $("#newDateCommande").val();
            var idClient = $("#newClientId").val();
            if(!date || !idClient) {
                showToast("Veuillez remplir tous les champs !", "#dc3545");
                return;
            }

            $.post('<%= request.getContextPath() %>/commandes', {
                action:'add',
                dateCommande:date,
                idClient:idClient
            }, function() {
                showToast("Commande ajoutée !");
                setTimeout(() => location.reload(), 1000);
            }).fail(function(xhr){
                showToast("Erreur: " + xhr.responseText, "#dc3545");
            });
        });

        // OUVERTURE DU MODAL
        $(".details-btn").click(function() {
            var idCommande = $(this).data("id");
            var clientId = $(this).data("client");

            // Stocker les IDs dans le modal pour une récupération fiable
            $("#detailsModal").data("commande-id", idCommande);
            $("#detailsModal").data("client-id", clientId);

            $("#modalTitle").text("Commande #" + idCommande + " – Client #" + clientId);

            // Vider les champs du formulaire
            $("#newProductId").val("");
            $("#newQuantity").val("");

            // Charger les lignes de commande
            loadCommandeLines(idCommande, clientId);

            modal.show();
        });

        // FERMETURE DU MODAL
        span.click(function(){ modal.hide(); });
        $(window).click(function(event){
            if(event.target.id === "detailsModal"){
                modal.hide();
            }
        });

        // AJOUT LIGNE PRODUIT
        $(document).on("click", ".add-line", function(){
            var idProduit = $("#newProductId").val();
            var quantite = $("#newQuantity").val();

            // Récupérer les IDs depuis les data attributes du modal
            var idCommande = $("#detailsModal").data("commande-id");
            var clientId = $("#detailsModal").data("client-id");

            // Validation des champs
            if(!idProduit || !quantite){
                showToast("Remplissez tous les champs !", "#dc3545");
                return;
            }

            // Validation des IDs récupérés
            if(!idCommande || !clientId){
                showToast("Erreur: IDs de commande manquants !", "#dc3545");
                console.error("IDs manquants - Commande:", idCommande, "Client:", clientId);
                return;
            }

            // Validation des valeurs numériques
            if(parseInt(idProduit) < 1 || parseInt(quantite) < 1){
                showToast("Les valeurs doivent être positives !", "#dc3545");
                return;
            }

            // Prix unitaire par défaut (0 pour utiliser le prix du produit dans le Servlet)
            var prixUnitaire = 0;

            console.log("Envoi requête ajout ligne:", {
                action: 'add-line',
                clientId: clientId,
                commandeId: idCommande,
                produitId: idProduit,
                quantite: quantite,
                prix_unitaire: prixUnitaire
            });

            $.post('<%= request.getContextPath() %>/lignes', {
                action: 'add-line',
                clientId: clientId,
                commandeId: idCommande,
                produitId: idProduit,
                quantite: quantite,
                prix_unitaire: prixUnitaire
            }, function(response){
                showToast("Produit ajouté avec succès !");

                // Vider les champs
                $("#newProductId").val("");
                $("#newQuantity").val("");

                // Recharger les lignes de commande dans le modal
                loadCommandeLines(idCommande, clientId);
            }).fail(function(xhr){
                var errorMsg = xhr.responseText || "Erreur inconnue";
                showToast("Erreur: " + errorMsg, "#dc3545");
                console.error("Erreur AJAX:", xhr);
            });
        });

        // SUPPRIMER LIGNE PRODUIT (delegation)
        $("#modalTable").on("click", ".delete-line", function(){
            var idLigne = $(this).data("id");

            // Récupérer les IDs depuis les data attributes du modal
            var idCommande = $("#detailsModal").data("commande-id");
            var clientId = $("#detailsModal").data("client-id");

            // Validation des IDs
            if(!idCommande || !clientId){
                showToast("Erreur: IDs de commande manquants !", "#dc3545");
                return;
            }

            if(!confirm("Voulez-vous vraiment supprimer cette ligne ?")){
                return;
            }

            console.log("Suppression ligne:", idLigne);

            $.post('<%= request.getContextPath() %>/lignes', {
                action: 'delete-line',
                clientId: clientId,
                commandeId: idCommande,
                ligneId: idLigne
            }, function(){
                showToast("Ligne supprimée avec succès !");

                // Recharger les lignes de commande dans le modal
                loadCommandeLines(idCommande, clientId);
            }).fail(function(xhr){
                var errorMsg = xhr.responseText || "Erreur inconnue";
                showToast("Erreur: " + errorMsg, "#dc3545");
            });
        });
    });
</script>

</body>
</html>