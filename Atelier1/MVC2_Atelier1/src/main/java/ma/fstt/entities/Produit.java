package ma.fstt.entities;

public class Produit {
    private int idProduit;
    private String nomProduit;
    private Double prix;
    private int stock;

    public Produit(){}
    public Produit(int idProduit, String nomProduit, Double prix, int stock) {
        this.idProduit = idProduit;
         this.nomProduit = nomProduit;
         this.prix = prix;
         this.stock = stock;

    }

    public int getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }
    public String getNomProduit() {
        return nomProduit;
    }
    public void setNomProduit(String nomProduit) { this.nomProduit = nomProduit; }
    public Double getPrix() {
        return prix;
    }
    public void setPrix(Double prix) {
        this.prix = prix;
    }
    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "idProduit=" + idProduit +
                ", nomProduit='" + nomProduit + '\'' +
                ", prix=" + prix +
                ", stock=" + stock +
                '}';
    }
}
