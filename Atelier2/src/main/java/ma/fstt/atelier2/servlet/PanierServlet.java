package ma.fstt.atelier2.servlet;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ma.fstt.atelier2.model.Panier;
import ma.fstt.atelier2.model.Produit;
import ma.fstt.atelier2.tools.JPAtools;

import java.io.IOException;

@WebServlet("/panier")
public class PanierServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "afficher";

        switch (action) {
            case "ajouter":
                ajouterProduit(request, response);
                break;
            case "supprimer":
                supprimerProduit(request, response);
                break;
            case "inc":
                changerQuantite(request, response, 1);
                break;
            case "dec":
                changerQuantite(request, response, -1);
                break;
            case "vider":
                viderPanier(request, response);
                break;
            case "afficher":
            default:
                afficherPanier(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if ("ajouter".equals(action)) {
            ajouterProduit(request, response);
        }
    }

    private void afficherPanier(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Panier panier = (Panier) session.getAttribute("panier");
        if (panier == null) {
            panier = new Panier();
            session.setAttribute("panier", panier);
        }

        request.setAttribute("panier", panier);
        request.getRequestDispatcher("/WEB-INF/panier/list.jsp").forward(request, response);
    }

    private void ajouterProduit(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Long id = Long.parseLong(request.getParameter("id"));
        EntityManager em = JPAtools.getEntityManager();
        Produit produit = em.find(Produit.class, id);
        em.close();

        if (produit != null) {
            HttpSession session = request.getSession();
            Panier panier = (Panier) session.getAttribute("panier");
            if (panier == null) {
                panier = new Panier();
                session.setAttribute("panier", panier);
            }
            panier.ajouterProduit(produit);
        }

        // Redirect back to accueil with success message
        response.sendRedirect(request.getContextPath() + "/accueil?added=true");
    }

    private void supprimerProduit(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Long id = Long.parseLong(request.getParameter("id"));

        HttpSession session = request.getSession();
        Panier panier = (Panier) session.getAttribute("panier");

        if (panier != null) {
            panier.supprimerProduit(id);
        }

        response.sendRedirect(request.getContextPath() + "/panier?action=afficher");
    }

    private void changerQuantite(HttpServletRequest request, HttpServletResponse response, int delta)
            throws IOException {
        Long id = Long.parseLong(request.getParameter("id"));

        HttpSession session = request.getSession();
        Panier panier = (Panier) session.getAttribute("panier");

        if (panier != null) {
            for (ma.fstt.atelier2.model.LignePanier ligne : panier.getLignes()) {
                if (ligne.getProduit().getId().equals(id)) {
                    int newQte = ligne.getQuantite() + delta;
                    if (newQte <= 0) {
                        panier.supprimerProduit(id);
                    } else {
                        ligne.setQuantite(newQte);
                    }
                    break;
                }
            }
        }
        response.sendRedirect(request.getContextPath() + "/panier?action=afficher");
    }

    private void viderPanier(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession();
        Panier panier = (Panier) session.getAttribute("panier");
        if (panier != null) {
            panier.getLignes().clear();
        }
        response.sendRedirect(request.getContextPath() + "/panier?action=afficher");
    }
}