package ma.fstt.dao;

import jakarta.enterprise.context.ApplicationScoped;
import ma.fstt.entities.LigneCommande;

import java.sql.SQLException;
import java.util.List;

@ApplicationScoped
public class LigneCommandeDAO extends DAO<LigneCommande> {
    @Override
    public void create(LigneCommande entity) throws SQLException {
        try (java.sql.PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO lignedecommande " +
                        "(quantite, prixUnitaire, idCommande, idProduit) VALUES (?, ?, ?, ?)")) {
            ps.setInt(1, entity.getQuantite());
            ps.setDouble(2, entity.getPrixUnitaire());
            ps.setInt(3, entity.getIdCommande());
            ps.setInt(4, entity.getIdProduit());
            ps.executeUpdate();
        }
    }

    @Override
    public void update(LigneCommande entity) throws SQLException {
        try (java.sql.PreparedStatement ps = connection.prepareStatement(
                "UPDATE lignedecommande SET idCommande = ?, idProduit = ?, quantite = ?, prixUnitaire = ? WHERE idLigne = ?")) {
            ps.setInt(1, entity.getIdCommande());
            ps.setInt(2, entity.getIdProduit());
            ps.setInt(3, entity.getQuantite());
            ps.setDouble(4, entity.getPrixUnitaire());
            ps.setLong(5, entity.getIdLigne());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(LigneCommande entity) throws SQLException {
        try (java.sql.PreparedStatement ps = connection.prepareStatement(
                "DELETE FROM lignedecommande WHERE idLigne = ?")) {
            ps.setLong(1, entity.getIdLigne());
            ps.executeUpdate();
        }
    }

    @Override
    public LigneCommande findOne(LigneCommande entity) throws SQLException {
        try (java.sql.PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM lignedecommande WHERE idLigne = ?")) {
            ps.setLong(1, entity.getIdLigne());
            try (java.sql.ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    LigneCommande l = new LigneCommande();
                    l.setIdLigne(rs.getInt("idLigne"));
                    l.setIdCommande(rs.getInt("idCommande"));
                    l.setIdProduit(rs.getInt("idProduit"));
                    l.setQuantite(rs.getInt("quantite"));
                    l.setPrixUnitaire(rs.getDouble("prixUnitaire"));
                    return l;
                }
            }
        }
        return null;
    }

    @Override
    public List<LigneCommande> findAll() throws SQLException {
        List<LigneCommande> lignes = new java.util.ArrayList<>();
        try (java.sql.Statement st = connection.createStatement();
             java.sql.ResultSet rs = st.executeQuery("SELECT * FROM lignedecommande")) {
            while (rs.next()) {
                LigneCommande l = new LigneCommande();
                l.setIdLigne(rs.getInt("idLigne"));
                l.setIdCommande(rs.getInt("idCommande"));
                l.setIdProduit(rs.getInt("idProduit"));
                l.setQuantite(rs.getInt("quantite"));
                l.setPrixUnitaire(rs.getDouble("prixUnitaire"));
                lignes.add(l);
            }
        }
        return lignes;
    }

    public List<LigneCommande> findByCommande(int idCommande) throws SQLException {
        List<LigneCommande> lignes = new java.util.ArrayList<>();
        try (java.sql.PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM lignedecommande WHERE idCommande = ?")) {
            ps.setInt(1, idCommande);
            try (java.sql.ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    LigneCommande l = new LigneCommande();
                    l.setIdLigne(rs.getInt("idLigne"));
                    l.setIdCommande(rs.getInt("idCommande"));
                    l.setIdProduit(rs.getInt("idProduit"));
                    l.setQuantite(rs.getInt("quantite"));
                    l.setPrixUnitaire(rs.getDouble("prixUnitaire"));
                    lignes.add(l);
                }
            }
        }
        return lignes;
    }

    public double sumTotalByCommandeId(int idCommande) throws SQLException {
        try (java.sql.PreparedStatement ps = connection.prepareStatement(
                "SELECT COALESCE(SUM(quantite * prixUnitaire), 0) AS total FROM lignedecommande WHERE idCommande = ?")) {
            ps.setInt(1, idCommande);
            try (java.sql.ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("total");
                }
            }
        }
        return 0.0;
    }
}