package ma.fstt.controller;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ma.fstt.service.CommandeService;
import ma.fstt.service.ClientService;
import ma.fstt.dao.LigneCommandeDAO;
import ma.fstt.entities.Commande;
import ma.fstt.entities.Client;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(value = "/commandes")
public class CommandeController extends HttpServlet {

    @Inject
    private CommandeService commandeService;

    @Inject
    private ClientService clientService;

    @Inject
    private LigneCommandeDAO ligneCommandeDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            List<Commande> commandes = commandeService.findAll();
            req.setAttribute("commandes", commandes);

            String detailsId = req.getParameter("commandeId");
            if (detailsId != null) {
                int cid = Integer.parseInt(detailsId);
                req.setAttribute("lignes", ligneCommandeDAO.findByCommande(cid));
            }

            req.getRequestDispatcher("/WEB-INF/views/commande/Commandelist.jsp").forward(req, resp);

        } catch (SQLException e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/commande/Commandelist.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        System.out.println("==== doPost CommandeController appelé ====");
        String action = req.getParameter("action");
        System.out.println("Action reçue : " + action);

        try {
            if ("add".equals(action)) {
                String dateStr = req.getParameter("dateCommande");
                Long client_id = Long.parseLong(req.getParameter("idClient"));


                Date date_commande = null;
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    date_commande = sdf.parse(dateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                Client probe = new Client();
                probe.setIdClient(client_id);
                Client existing = clientService.findOne(probe);

                if (existing == null) {
                    System.out.println("Client introuvable pour ID = " + client_id);
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.getWriter().write("Client introuvable !");
                    return;
                }

                //  Création et insertion
                Commande commande = new Commande(0, date_commande, existing, 0.0);
                commandeService.create(commande);

                System.out.println("Commande ajoutée avec succès !");
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write("OK");
                return;
            }


            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Action non reconnue !");

        } catch (SQLException ex) {
            ex.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Erreur SQL : " + ex.getMessage());
        }
    }
}
