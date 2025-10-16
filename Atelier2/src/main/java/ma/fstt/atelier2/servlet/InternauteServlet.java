package ma.fstt.atelier2.servlet;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import ma.fstt.atelier2.model.Internaute;
import ma.fstt.atelier2.model.Panier;
import ma.fstt.atelier2.tools.JPAtools;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.io.IOException;
import java.util.List;

@WebServlet("/internaute")
public class InternauteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "addForm":
                request.getRequestDispatcher("/WEB-INF/internaute/add.jsp").forward(request, response);
                break;
            case "editForm":
                showEditForm(request, response);
                break;
            case "profile":
                showProfile(request, response);
                break;
            case "changePasswordForm":
                request.getRequestDispatcher("/WEB-INF/internaute/change-password.jsp").forward(request, response);
                break;
            case "logout":
                logout(request, response);
                return;
            case "delete":
                deleteInternaute(request, response);
                break;
            case "loginPage" :
                request.getRequestDispatcher("/WEB-INF/internaute/login.jsp").forward(request, response);
                break;

            default:
                listInternautes(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        switch (action) {
            case "add":
                addInternaute(request, response);
                break; case "login":
                loginInternaute(request, response);
                break;
            case "update":
                updateInternaute(request, response);
                break;
            case "changePassword":
                changePassword(request, response);
                break;

            default:
                listInternautes(request, response);
        }
    }

    // Authentification de l'internaute
    private void loginInternaute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String email = request.getParameter("email");
        String motDePasse = request.getParameter("motDePasse");
        String passwordHash = hashPassword(motDePasse);

        EntityManager em = JPAtools.getEntityManager();
        List<Internaute> result = em.createQuery("SELECT i FROM Internaute i WHERE i.email = :email", Internaute.class)
                .setParameter("email", email)
                .getResultList();
        em.close();

        if (!result.isEmpty()) {
            // Authentification réussie
            request.getSession().setAttribute("internaute", result.get(0));
            response.sendRedirect(request.getContextPath() + "/accueil");
        } else {
            // Échec de connexion
            request.setAttribute("loginError", "Email ou mot de passe incorrect.");
            request.getRequestDispatcher("/WEB-INF/internaute/login.jsp").forward(request, response);
        }
    }

    private void listInternautes(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManager em = JPAtools.getEntityManager();
        List<Internaute> internautes = em.createQuery("SELECT i FROM Internaute i", Internaute.class).getResultList();
        em.close();

        request.setAttribute("internautes", internautes);
        request.getRequestDispatcher("/WEB-INF/internaute/list.jsp").forward(request, response);
    }

    private void addInternaute(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String nom = request.getParameter("nom");
        String email = request.getParameter("email");

    String motDePasse = request.getParameter("motDePasse");
    String passwordHash = hashPassword(motDePasse);

    Internaute internaute = new Internaute();
    internaute.setNom(nom);
    internaute.setEmail(email);
    internaute.setPassword(passwordHash);

    // Create a new empty cart for the user
    Panier panier = new Panier();
    internaute.setPanier(panier);

        EntityManager em = JPAtools.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(internaute);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

    response.sendRedirect(request.getContextPath() + "/internaute?action=loginPage");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));

        EntityManager em = JPAtools.getEntityManager();
        Internaute internaute = em.find(Internaute.class, id);
        em.close();

        request.setAttribute("internaute", internaute);
        request.getRequestDispatcher("/WEB-INF/internaute/edit.jsp").forward(request, response);
    }

    private void updateInternaute(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        String nom = request.getParameter("nom");
        String email = request.getParameter("email");

    String motDePasse = request.getParameter("motDePasse");

        // Basic server-side validation
        boolean hasError = false;
        StringBuilder error = new StringBuilder();
        if (nom == null || nom.trim().length() < 2) {
            hasError = true;
            error.append("Le nom doit comporter au moins 2 caractères. ");
        }
        if (email == null || !email.matches("[^\\s@]+@[^\\s@]+\\.[^\\s@]+")) {
            hasError = true;
            error.append("Email invalide. ");
        }
        if (motDePasse != null && !motDePasse.isEmpty() && motDePasse.length() < 6) {
            hasError = true;
            error.append("Le mot de passe doit comporter au moins 6 caractères.");
        }

        if (hasError) {
            try {
                request.setAttribute("error", error.toString());
                EntityManager em = JPAtools.getEntityManager();
                Internaute internaute = em.find(Internaute.class, id);
                em.close();
                request.setAttribute("internaute", internaute);
                request.getRequestDispatcher("/WEB-INF/internaute/edit.jsp").forward(request, response);
            } catch (Exception ex) {
                response.sendRedirect(request.getContextPath() + "/internaute?action=editForm&id=" + id + "&error=1");
            }
            return;
        }

        EntityManager em = JPAtools.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            Internaute internaute = em.find(Internaute.class, id);
            if (internaute != null) {
                internaute.setNom(nom.trim());
                internaute.setEmail(email.trim());
                if (motDePasse != null && !motDePasse.isEmpty()) {
                    internaute.setPassword(hashPassword(motDePasse));
                }
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        response.sendRedirect(request.getContextPath() + "/internaute?action=editForm&id=" + id + "&updated=true");
    }

    // Méthode utilitaire pour hasher le mot de passe (SHA-256)
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteInternaute(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Long id = Long.parseLong(request.getParameter("id"));

        EntityManager em = JPAtools.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            Internaute internaute = em.find(Internaute.class, id);
            if (internaute != null) em.remove(internaute);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        response.sendRedirect(request.getContextPath() + "/internaute?action=list");
    }

    private void showProfile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Object user = request.getSession().getAttribute("internaute");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/internaute?action=loginPage");
            return;
        }
        request.setAttribute("internaute", user);
        request.getRequestDispatcher("/WEB-INF/internaute/profile.jsp").forward(request, response);
    }

    private void changePassword(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        Object userObj = request.getSession().getAttribute("internaute");
        if (userObj == null) {
            response.sendRedirect(request.getContextPath() + "/internaute?action=loginPage");
            return;
        }
        Long id = ((ma.fstt.atelier2.model.Internaute) userObj).getId();
        String newPwd = request.getParameter("newPassword");
        String confirm = request.getParameter("confirmPassword");
        if (newPwd == null || newPwd.length() < 6 || !newPwd.equals(confirm)) {
            request.setAttribute("error", "Mot de passe invalide ou confirmation différente.");
            request.getRequestDispatcher("/WEB-INF/internaute/change-password.jsp").forward(request, response);
            return;
        }

        EntityManager em = JPAtools.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Internaute internaute = em.find(Internaute.class, id);
            if (internaute != null) {
                internaute.setPassword(hashPassword(newPwd));
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
        response.sendRedirect(request.getContextPath() + "/internaute?action=changePasswordForm&updated=true");
    }

    private void logout(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.getSession().invalidate();
        response.sendRedirect(request.getContextPath() + "/internaute?action=loginPage");
    }
}
