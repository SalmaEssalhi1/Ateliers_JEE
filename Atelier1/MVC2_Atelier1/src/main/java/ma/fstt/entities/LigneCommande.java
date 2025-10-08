package ma.fstt.entities;

public class LigneCommande {
    private int idLigne;
    private int quantite;
    private Double prixUnitaire;
    private int idCommande;
    private int idProduit;


public LigneCommande() {};
    public LigneCommande( int idLigne, int quantite, Double prixUnitaire, int idCommande, int idProduit ) {
        this.idLigne = idLigne;
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
        this.idCommande = idCommande;
        this.idProduit = idProduit;

    }
    public int getIdLigne() {
        return idLigne;
    }
    public void setIdLigne(int idLigne) {
        this.idLigne = idLigne;
    }
    public int getQuantite() {
        return quantite;
    }
    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
    public Double getPrixUnitaire() {
        return prixUnitaire;
    }
    public void setPrixUnitaire(Double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }
    public int getIdCommande() {
        return idCommande;
    }
    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }
    public int getIdProduit() {
        return idProduit;
    }
    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }

    @Override
    public String toString() {
        return "LigneCommande{" +
                "idCommande=" + idCommande +
                ", idLigne=" + idLigne +
                ", quantite=" + quantite +
                ", prixUnitaire=" + prixUnitaire +
                ", idProduit=" + idProduit +
                '}';
    }
}
