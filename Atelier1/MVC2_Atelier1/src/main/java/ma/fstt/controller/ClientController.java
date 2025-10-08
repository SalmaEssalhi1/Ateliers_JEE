package ma.fstt.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ma.fstt.tools.DBconfig;

import ma.fstt.entities.Client;
import ma.fstt.service.ClientService;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

@WebServlet(value="/clients")

@ApplicationScoped
public class ClientController extends HttpServlet {
    @Inject
    private ClientService clientService;


    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
    try{
        List<Client> clients = clientService.findAll();
        req.setAttribute("clients", clients);
        req.getRequestDispatcher("/WEB-INF/views/client/Clientlist.jsp").forward(req, resp);
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
                String nom = req.getParameter("nom");
                String prenom = req.getParameter("prenom");
                String email = req.getParameter("email");
                String adresse = req.getParameter("adresse");
                Client client = new Client(null, nom,prenom, email, adresse);
                clientService.create(client);
            }
            resp.sendRedirect(req.getContextPath() + "/clients");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}

