package ma.fstt.atelier2.servlet;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ma.fstt.atelier2.model.Vitrine;
import ma.fstt.atelier2.tools.JPAtools;

import java.io.IOException;
import java.util.List;

@WebServlet("/accueil")
public class AccueilServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String q = request.getParameter("q");

        EntityManager em = JPAtools.getEntityManager();

        List<Vitrine> vitrines;
        if (q != null && !q.trim().isEmpty()) {
            String like = "%" + q.trim().toLowerCase() + "%";
            TypedQuery<Vitrine> query = em.createQuery(
                    "SELECT DISTINCT v FROM Vitrine v JOIN v.produits p " +
                            "WHERE LOWER(p.nom) LIKE :kw OR LOWER(p.description) LIKE :kw",
                    Vitrine.class
            );
            query.setParameter("kw", like);
            vitrines = query.getResultList();
            request.setAttribute("searchQuery", q);
            request.setAttribute("noResults", vitrines.isEmpty());
        } else {
            vitrines = em.createQuery("SELECT v FROM Vitrine v", Vitrine.class).getResultList();
        }
        em.close();

        request.setAttribute("vitrines", vitrines);
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
