package ma.fstt.dao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

import jakarta.enterprise.context.ApplicationScoped;
import ma.fstt.entities.Produit;

@ApplicationScoped
public class ProduitDAO extends DAO<Produit> {
    public ProduitDAO() {}

    @Override
    public void create(Produit produit) throws SQLException {
        try(PreparedStatement ps= connection.prepareStatement(
                "INSERT INTO produit (nomProduit, Prix, stock) VALUES (?, ?, ?)")){
            ps.setString(1, produit.getNomProduit());
            ps.setDouble(2, produit.getPrix());
            ps.setInt(3, produit.getStock());
            ps.executeUpdate();
        }
    }
    @Override
    public void update(Produit produit) throws SQLException {
        try(PreparedStatement ps= connection.prepareStatement(
                "UPDATE produit SET nomProduit = ?, prix = ?, stock = ? WHERE idProduit = ?")){
            ps.setString(1, produit.getNomProduit());
            ps.setDouble(2, produit.getPrix());
            ps.setInt(3, produit.getStock());
            ps.setInt(4, produit.getIdProduit());
            ps.executeUpdate();
        }
    }
    @Override
    public void delete(Produit produit) throws SQLException {
        try(PreparedStatement ps= connection.prepareStatement(
                "DELETE FROM produit WHERE idProduit = ?")){
           ps.setInt(1, produit.getIdProduit());
           ps.executeUpdate();
        }
    }

    @Override
    public Produit findOne (Produit produit) throws SQLException {
        try(PreparedStatement ps= connection.prepareStatement(
                "SELECT * FROM produit WHERE idProduit = ?")){
            ps.setInt(1, produit.getIdProduit());
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    Produit p = new Produit();
                    p.setIdProduit(rs.getInt("idProduit"));
                    p.setNomProduit(rs.getString("nomProduit"));
                    p.setPrix(rs.getDouble("prix"));
                    p.setStock(rs.getInt("stock"));
                    return p;

                }
            }
        }
        return null;
    }

    @Override
    public List<Produit> findAll() throws SQLException {
        List<Produit> produits = new ArrayList<Produit>();
        try(Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM produit")){
            while(rs.next()){
                Produit p = new Produit();
                p.setIdProduit(rs.getInt("idProduit"));
                p.setNomProduit(rs.getString("nomProduit"));
                p.setPrix(rs.getDouble("prix"));
                p.setStock(rs.getInt("stock"));
                produits.add(p);

            }
        }
        return produits;
    }
}