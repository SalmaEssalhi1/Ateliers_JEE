package ma.fstt.entities;

import java.util.Date;

public class Commande {
    private int idCommande;
    private Date dateCommande;
    private Double prixTotal;
    private Client idClient;

    public Commande() {}

    public Commande(int idCommande, Date dateCommande, Client idClient, Double prixTotal) {
        this.idCommande = idCommande;
        this.dateCommande = dateCommande;
        this.idClient = idClient;
        this.prixTotal = prixTotal;
    }
    public int getIdCommande() { return idCommande; }

    public void setIdCommande(int idCommande) {
         this.idCommande = idCommande;
    }

    public Date getDateCommande() { return dateCommande; }

    public void setDateCommande(Date dateCommande) {
        this.dateCommande = dateCommande;
    }

    public Double getPrixTotal() { return prixTotal; }

    public void setPrixTotal(Double prixTotal) { this.prixTotal = prixTotal; }

    public Client getIdClient() { return idClient; }

    public void setIdClient(Client idClient) { this.idClient = idClient; }

    @Override
    public String toString() {
        return "Commande{" +
                "dateCommande=" + dateCommande +
                ", idCommande='" + idCommande + '\'' +
                ", prixTotal=" + prixTotal +
                ", idClient=" + idClient +
                '}';
    }
}
