package ma.fstt.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import ma.fstt.entities.Client;
import ma.fstt.entities.Commande;

@ApplicationScoped
public class CommandeDAO extends DAO<Commande> {

    public CommandeDAO() {}

    @Override
    public void create(Commande commande) throws SQLException {
        String sql = "INSERT INTO commande (dateCommande, prixTotal, idClient) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(commande.getDateCommande().getTime()));
            ps.setDouble(2, commande.getPrixTotal());
            ps.setLong(3, commande.getIdClient().getIdClient());
            ps.executeUpdate();
        }
    }

    @Override
    public void update(Commande commande) throws SQLException {
        String sql = "UPDATE commande SET dateCommande = ?, prixTotal = ?, idClient = ? WHERE idCommande = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(commande.getDateCommande().getTime()));
            ps.setDouble(2, commande.getPrixTotal());
            ps.setLong(3, commande.getIdClient().getIdClient());
            ps.setInt(4, commande.getIdCommande());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(Commande commande) throws SQLException {
        String sql = "DELETE FROM commande WHERE idCommande = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, commande.getIdCommande());
            ps.executeUpdate();
        }
    }

    @Override
    public Commande findOne(Commande commande) throws SQLException {
        String sql = "SELECT * FROM commande WHERE idCommande = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, commande.getIdCommande());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Commande c = new Commande();
                    c.setIdCommande(rs.getInt("idCommande"));
                    c.setDateCommande(rs.getDate("dateCommande"));

                    Client client = new Client();
                    client.setIdClient(rs.getLong("idClient"));
                    c.setIdClient(client);

                    c.setPrixTotal(rs.getDouble("prixTotal"));
                    return c;
                }
            }
        }
        return null;
    }

    @Override
    public List<Commande> findAll() throws SQLException {
        List<Commande> commandes = new ArrayList<>();
        String sql = "SELECT * FROM commande";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Commande c = new Commande();
                c.setIdCommande(rs.getInt("idCommande"));
                c.setDateCommande(rs.getDate("dateCommande"));

                Client client = new Client();
                client.setIdClient(rs.getLong("idClient"));
                c.setIdClient(client);

                c.setPrixTotal(rs.getDouble("prixTotal"));
                commandes.add(c);
            }
        }
        return commandes;
    }
}
