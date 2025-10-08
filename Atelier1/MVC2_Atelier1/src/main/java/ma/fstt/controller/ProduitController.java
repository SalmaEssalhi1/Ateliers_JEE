package ma.fstt.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ma.fstt.tools.DBconfig;
import ma.fstt.service.ProduitService;
import ma.fstt.entities.Produit;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

@WebServlet(value="/produits")
public class ProduitController extends HttpServlet {

    @Inject
    private ProduitService produitService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            List<Produit> produits = produitService.findAll();
            req.setAttribute("produits", produits);
            req.getRequestDispatcher("/WEB-INF/views/produit/Productlist.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            if ("add".equals(action)) {
                String nomProduit = req.getParameter("nomProduit");
                double prix = Double.parseDouble(req.getParameter("prix"));
                int stock = Integer.parseInt(req.getParameter("stock"));
                Produit produit = new Produit(0, nomProduit, prix, stock);
                produitService.create(produit);
            }
            resp.sendRedirect(req.getContextPath() + "/produits");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}