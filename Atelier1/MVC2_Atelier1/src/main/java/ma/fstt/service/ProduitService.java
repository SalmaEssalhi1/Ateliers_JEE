package ma.fstt.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ma.fstt.dao.ProduitDAO;
import ma.fstt.entities.Produit;
import java.sql.SQLException;
import java.util.*;

@ApplicationScoped
public class ProduitService implements produitInterface {

    @Inject
    private ProduitDAO produitDAO; // ✅ injection directe ici

    public ProduitService() {
    }

    @Override
    public void create(Produit produit) throws SQLException {
        produitDAO.create(produit);
    }

    @Override
    public void update(Produit produit) throws SQLException {
        produitDAO.update(produit);
    }

    @Override
    public void delete(Produit produit) throws SQLException {
        produitDAO.delete(produit);
    }

    @Override
    public Produit findOne(Produit produit) throws SQLException {
        return produitDAO.findOne(produit);
    }

    @Override
    public List<Produit> findAll() throws SQLException {
        return produitDAO.findAll();
    }
}
