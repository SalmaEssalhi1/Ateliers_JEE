package ma.fstt.dao;
import jakarta.enterprise.context.ApplicationScoped;
import ma.fstt.entities.Client;
import ma.fstt.tools.DBconfig;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.*;
import java.util.List;
import ma.fstt.entities.Client;
@ApplicationScoped
public class ClientDAO extends DAO<Client> {

    public ClientDAO() throws SQLException {
  connection = DBconfig.produceDataSource().getConnection();
    }
    @Override
public void create(Client client) throws SQLException {
       PreparedStatement ps = connection.prepareStatement("INSERT INTO `client` ( `nom`, `prenom` , `email`, `adresse`) VALUES (?, ?, ? , ?)");

    ps.setString(1, client.getNom());
    ps.setString(2, client.getPrenom());
    ps.setString(3, client.getEmail());
    ps.setString(4, client.getAdresse());
    ps.executeUpdate();
    }

    @Override
    public void update(Client client) throws SQLException {
        try(PreparedStatement ps= connection.prepareStatement("UPDATE  client SET nom = ? , prenom = ?, email = ?, adresse = ? WHERE idClient= ?")) {
            ps.setString(1, client.getNom());
            ps.setString(2, client.getPrenom());
            ps.setString(3, client.getEmail());
            ps.setString(4, client.getAdresse());
            ps.executeUpdate();
        }
    }
    @Override
    public void delete(Client client) throws SQLException {
        try(PreparedStatement ps= connection.prepareStatement("DELETE FROM client WHERE idClient = ?")) {
            ps.setLong(1, client.getIdClient());
            ps.executeUpdate();
        }
    }

    @Override
    public Client findOne (Client client) throws SQLException{
  try(PreparedStatement ps= connection.prepareStatement("SELECT * FROM client WHERE idClient = ?")) {
      ps.setLong(1, client.getIdClient());
      try (ResultSet rs = ps.executeQuery()) {
          if (rs.next()) {
              Client c = new Client();
              c.setIdClient(rs.getLong("idClient"));
              c.setNom(rs.getString("nom"));
              c.setPrenom(rs.getString("prenom"));
              c.setEmail(rs.getString("email"));
              c.setAdresse(rs.getString("adresse"));
              return c;
          }
      }
  }
  return null;
    }

    @Override
    public List<Client> findAll() throws SQLException {
       List<Client> clients = new ArrayList<>();
       try(Statement st = connection.createStatement();
       ResultSet rs = st.executeQuery("SELECT * FROM client")) {
           while (rs.next()) {
               Client c = new Client();
               c.setIdClient(rs.getLong("idClient"));
               c.setNom(rs.getString("nom"));
               c.setPrenom(rs.getString("prenom"));
               c.setEmail(rs.getString("email"));
               c.setAdresse(rs.getString("adresse"));
               clients.add(c);
           }
        }
       return clients;
    }


}
