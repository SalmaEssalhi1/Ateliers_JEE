package ma.fstt.controller;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ma.fstt.dao.LigneCommandeDAO;
import ma.fstt.entities.Client;
import ma.fstt.entities.Commande;
import ma.fstt.entities.LigneCommande;
import ma.fstt.service.ClientService;
import ma.fstt.service.CommandeService;
import ma.fstt.entities.Produit;
import ma.fstt.service.ProduitService;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

@WebServlet(value = "/lignes")
public class LigneCommandeController extends HttpServlet {

    @Inject
    private CommandeService commandeService;

    @Inject
    private ClientService clientService;

    @Inject
    private LigneCommandeDAO ligneCommandeDAO;

    @Inject
    private ProduitService produitService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String clientParam = req.getParameter("clientId");
        String commandeParam = req.getParameter("commandeId");

        System.out.println("=== doGet /lignes ===");
        System.out.println("clientId: " + clientParam);
        System.out.println("commandeId: " + commandeParam);

        // Si on veut renvoyer le JSON des lignes (requête AJAX)
        if (commandeParam != null && !commandeParam.trim().isEmpty()) {
            resp.setContentType("application/json;charset=UTF-8");

            try {
                int commandeId = Integer.parseInt(commandeParam);
                System.out.println("Recherche des lignes pour commande: " + commandeId);

                List<LigneCommande> lignes = ligneCommandeDAO.findByCommande(commandeId);
                System.out.println("Nombre de lignes trouvées: " + (lignes != null ? lignes.size() : "null"));

                // Vérifier que lignes n'est pas null
                if (lignes == null) {
                    lignes = new ArrayList<>();
                    System.out.println("Liste nulle, initialisation à liste vide");
                }

                StringBuilder json = new StringBuilder();
                json.append("[");

                for (int i = 0; i < lignes.size(); i++) {
                    try {
                        LigneCommande l = lignes.get(i);

                        // Récupérer le produit avec gestion d'erreur
                        Produit p = null;
                        try {
                            Produit probe = new Produit();
                            probe.setIdProduit(l.getIdProduit());
                            p = produitService.findOne(probe);
                        } catch (Exception e) {
                            System.err.println("Erreur lors de la récupération du produit " + l.getIdProduit() + ": " + e.getMessage());
                            p = null;
                        }

                        json.append("{");
                        json.append("\"idLigne\":").append(l.getIdLigne()).append(",");
                        json.append("\"idProduit\":").append(l.getIdProduit()).append(",");
                        json.append("\"nomProduit\":\"").append(p != null ? escapeJson(p.getNomProduit()) : "Inconnu").append("\",");
                        json.append("\"quantite\":").append(l.getQuantite()).append(",");
                        json.append("\"prixUnitaire\":").append(l.getPrixUnitaire());
                        json.append("}");

                        if (i < lignes.size() - 1) {
                            json.append(",");
                        }
                    } catch (Exception e) {
                        System.err.println("Erreur lors du traitement de la ligne " + i + ": " + e.getMessage());
                        e.printStackTrace();
                    }
                }

                json.append("]");

                System.out.println("JSON généré: " + json.toString());

                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write(json.toString());
                return;

            } catch (NumberFormatException e) {
                System.err.println("Erreur format commandeId: " + e.getMessage());
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\":\"Format de commandeId invalide\"}");
                return;
            } catch (SQLException e) {
                System.err.println("Erreur SQL: " + e.getMessage());
                e.printStackTrace();
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.getWriter().write("{\"error\":\"Erreur SQL: " + escapeJson(e.getMessage()) + "\"}");
                return;
            } catch (Exception e) {
                System.err.println("Erreur inattendue: " + e.getMessage());
                e.printStackTrace();
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.getWriter().write("{\"error\":\"Erreur: " + escapeJson(e.getMessage()) + "\"}");
                return;
            }
        }

        // Sinon comportement normal pour afficher le JSP
        if (clientParam == null) {
            req.setAttribute("error", "Paramètre clientId manquant");
            req.getRequestDispatcher("/views/commande/lignes.jsp").forward(req, resp);
            return;
        }

        try {
            long clientId = Long.parseLong(clientParam);
            Client probe = new Client();
            probe.setIdClient(clientId);
            Client client = clientService.findOne(probe);
            if (client == null) {
                req.setAttribute("error", "Client introuvable: ID=" + clientId);
                req.getRequestDispatcher("/views/commande/lignes.jsp").forward(req, resp);
                return;
            }

            List<Commande> all = commandeService.findAll();
            List<Commande> commandesDuClient = new ArrayList<>();
            for (Commande c : all) {
                if (c.getIdClient() != null && c.getIdClient().getIdClient() == clientId) {
                    commandesDuClient.add(c);
                }
            }

            Map<Long, List<LigneCommande>> lignesMap = new HashMap<>();
            for (Commande cmd : commandesDuClient) {
                lignesMap.put((long) cmd.getIdCommande(), ligneCommandeDAO.findByCommande(cmd.getIdCommande()));
            }

            req.setAttribute("client", client);
            req.setAttribute("commandes", commandesDuClient);
            req.setAttribute("lignesMap", lignesMap);
            req.getRequestDispatcher("/views/commande/lignes.jsp").forward(req, resp);
        } catch (SQLException | NumberFormatException e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/views/commande/lignes.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        System.out.println("===== DEBUT doPost =====");
        System.out.println("Action reçue: " + action);

        String clientParam = req.getParameter("clientId");
        String commandeParam = req.getParameter("commandeId");
        String produitParam = req.getParameter("produitId");
        String quantiteParam = req.getParameter("quantite");
        String prixParam = req.getParameter("prix_unitaire");
        String ligneParam = req.getParameter("ligneId");

        System.out.println("Paramètres reçus:");
        System.out.println("  - clientId: " + clientParam);
        System.out.println("  - commandeId: " + commandeParam);
        System.out.println("  - produitId: " + produitParam);
        System.out.println("  - quantite: " + quantiteParam);
        System.out.println("  - prix_unitaire: " + prixParam);
        System.out.println("  - ligneId: " + ligneParam);

        if (action == null || (!action.equals("add-line") && !action.equals("delete-line"))) {
            System.out.println("ERREUR: Action invalide ou nulle");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action invalide: " + action);
            return;
        }

        if (clientParam == null || clientParam.trim().isEmpty()) {
            System.out.println("ERREUR: clientId manquant");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Paramètre clientId manquant");
            return;
        }

        if (commandeParam == null || commandeParam.trim().isEmpty()) {
            System.out.println("ERREUR: commandeId manquant");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Paramètre commandeId manquant");
            return;
        }

        try {
            long clientId = Long.parseLong(clientParam);
            int commandeId = Integer.parseInt(commandeParam);

            System.out.println("IDs parsés: clientId=" + clientId + ", commandeId=" + commandeId);

            if ("add-line".equals(action)) {
                System.out.println(">>> Traitement ADD-LINE");

                if (produitParam == null || produitParam.trim().isEmpty()) {
                    System.out.println("ERREUR: produitId manquant");
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Paramètre produitId manquant");
                    return;
                }

                if (quantiteParam == null || quantiteParam.trim().isEmpty()) {
                    System.out.println("ERREUR: quantite manquante");
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Paramètre quantite manquant");
                    return;
                }

                int produitId = Integer.parseInt(produitParam);
                int quantite = Integer.parseInt(quantiteParam);
                double prixUnitaire = (prixParam != null && !prixParam.isBlank())
                        ? Double.parseDouble(prixParam)
                        : 0.0;

                System.out.println("Valeurs parsées: produitId=" + produitId + ", quantite=" + quantite + ", prix=" + prixUnitaire);

                Client probe = new Client();
                probe.setIdClient(clientId);
                Client client = clientService.findOne(probe);
                if (client == null) {
                    System.out.println("ERREUR: Client introuvable: " + clientId);
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Client introuvable: " + clientId);
                    return;
                }
                System.out.println("Client trouvé: " + client.getIdClient());

                boolean commandeOk = false;
                Commande targetCommande = null;
                for (Commande c : commandeService.findAll()) {
                    if (c.getIdCommande() == commandeId &&
                            c.getIdClient() != null &&
                            c.getIdClient().getIdClient() == clientId) {
                        commandeOk = true;
                        targetCommande = c;
                        break;
                    }
                }

                if (!commandeOk) {
                    System.out.println("ERREUR: Commande invalide pour ce client");
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Commande " + commandeId + " invalide pour client " + clientId);
                    return;
                }
                System.out.println("Commande validée: " + commandeId);

                Produit pProbe = new Produit();
                pProbe.setIdProduit(produitId);
                Produit produit = produitService.findOne(pProbe);
                if (produit == null) {
                    System.out.println("ERREUR: Produit introuvable: " + produitId);
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Produit introuvable: " + produitId);
                    return;
                }
                System.out.println("Produit trouvé: " + produit.getNomProduit() + " (stock: " + produit.getStock() + ")");

                if (prixUnitaire == 0.0) {
                    prixUnitaire = produit.getPrix();
                    System.out.println("Prix utilisé du produit: " + prixUnitaire);
                }

                if (produit.getStock() < quantite) {
                    System.out.println("ERREUR: Stock insuffisant (demandé: " + quantite + ", dispo: " + produit.getStock() + ")");
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST,
                            "Stock insuffisant pour produit " + produitId + " (disponible: " + produit.getStock() + ", demandé: " + quantite + ")");
                    return;
                }

                LigneCommande ligne = new LigneCommande(0, quantite, prixUnitaire, commandeId, produitId);
                ligne.setIdProduit(produitId);
                ligneCommandeDAO.create(ligne);
                System.out.println("Ligne de commande créée");

                produit.setStock(produit.getStock() - quantite);
                produitService.update(produit);
                System.out.println("Stock mis à jour: " + produit.getStock());

                double total = ligneCommandeDAO.sumTotalByCommandeId(commandeId);
                targetCommande.setPrixTotal(total);
                commandeService.update(targetCommande);
                System.out.println("Total commande mis à jour: " + total);

                System.out.println("===== SUCCESS: Ligne ajoutée =====");
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write("Ligne ajoutée avec succès");

            } else if ("delete-line".equals(action)) {
                System.out.println(">>> Traitement DELETE-LINE");

                if (ligneParam == null || ligneParam.trim().isEmpty()) {
                    System.out.println("ERREUR: ligneId manquant");
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Paramètre ligneId manquant");
                    return;
                }

                int ligneId = Integer.parseInt(ligneParam);
                System.out.println("ligneId à supprimer: " + ligneId);

                LigneCommande existing = ligneCommandeDAO.findOne(new LigneCommande(ligneId, 0, 0.0, 0, 0));
                if (existing == null) {
                    System.out.println("ERREUR: Ligne introuvable: " + ligneId);
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ligne de commande introuvable: " + ligneId);
                    return;
                }

                System.out.println("Ligne trouvée: " + existing.getIdLigne());

                Produit p = new Produit();
                p.setIdProduit(existing.getIdProduit());
                Produit full = produitService.findOne(p);

                if (full != null) {
                    full.setStock(full.getStock() + existing.getQuantite());
                    produitService.update(full);
                    System.out.println("Stock restauré: " + full.getStock());
                }

                ligneCommandeDAO.delete(existing);
                System.out.println("Ligne supprimée");

                double total = ligneCommandeDAO.sumTotalByCommandeId(commandeId);
                for (Commande c : commandeService.findAll()) {
                    if (c.getIdCommande() == commandeId) {
                        c.setPrixTotal(total);
                        commandeService.update(c);
                        System.out.println("Total commande mis à jour: " + total);
                        break;
                    }
                }

                System.out.println("===== SUCCESS: Ligne supprimée =====");
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write("Ligne supprimée avec succès");
            }

        } catch (NumberFormatException e) {
            System.out.println("ERREUR NumberFormatException: " + e.getMessage());
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Format de nombre invalide: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("ERREUR SQLException: " + e.getMessage());
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur base de données: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("ERREUR inattendue: " + e.getMessage());
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur: " + e.getMessage());
        }
    }

    // Utilitaire pour échapper les caractères spéciaux JSON
    private String escapeJson(String text) {
        if (text == null) return "";
        return text.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}