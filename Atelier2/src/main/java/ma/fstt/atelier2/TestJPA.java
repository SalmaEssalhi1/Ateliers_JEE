package ma.fstt.atelier2;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import ma.fstt.atelier2.model.*;

public class TestJPA {
    public static void main(String[] args) {
        EntityManagerFactory emf = null;
        EntityManager em = null;

        try {
            System.out.println("=== Démarrage de l'application JPA ===");

            // Créer l'EntityManagerFactory - Cela va créer les tables
            System.out.println("Création de l'EntityManagerFactory...");
            emf = Persistence.createEntityManagerFactory("macnx");
            System.out.println("✓ EntityManagerFactory créé avec succès !");

            // Créer l'EntityManager
            System.out.println("Création de l'EntityManager...");
            em = emf.createEntityManager();
            System.out.println("✓ EntityManager créé avec succès !");

            // Démarrer une transaction
            System.out.println("\n=== Début de la transaction ===");
            em.getTransaction().begin();

            // Créer quelques données de test
            System.out.println("Création des données de test...");

            // 1. Créer une Vitrine
            Vitrine vitrine = new Vitrine();
            em.persist(vitrine);
            System.out.println("✓ Vitrine créée");

            // 2. Créer des Produits
            Produit produit1 = new Produit("Laptop", "Ordinateur portable HP", 8500.00, 10);
            Produit produit2 = new Produit("Souris", "Souris sans fil", 150.00, 50);
            Produit produit3 = new Produit("Clavier", "Clavier mécanique", 450.00, 30);

            em.persist(produit1);
            em.persist(produit2);
            em.persist(produit3);
            System.out.println("✓ 3 Produits créés");

            // 3. Créer un Internaute avec un Panier
            Internaute internaute = new Internaute();

            internaute.setNom("Alami");
            internaute.setEmail("alami@example.com");
            internaute.setPassword("password123");

            Panier panier = new Panier();
            internaute.setPanier(panier);

            em.persist(internaute);
            System.out.println("✓ Internaute et Panier créés");

            // 4. Créer des LignePanier
            LignePanier ligne1 = new LignePanier();
            ligne1.setQuantite(2);
            ligne1.setProduit(produit1);
            ligne1.setPanier(panier);

            LignePanier ligne2 = new LignePanier();
            ligne2.setQuantite(1);
            ligne2.setProduit(produit2);
            ligne2.setPanier(panier);

            em.persist(ligne1);
            em.persist(ligne2);
            System.out.println("✓ 2 LignePanier créées");

            // Commit de la transaction
            em.getTransaction().commit();
            System.out.println("\n✓✓✓ Transaction committée avec succès ! ✓✓✓");

            // Vérifier les données
            System.out.println("\n=== Vérification des données ===");
            long nbProduits = em.createQuery("SELECT COUNT(p) FROM Produit p", Long.class).getSingleResult();
            long nbInternautes = em.createQuery("SELECT COUNT(i) FROM Internaute i", Long.class).getSingleResult();
            long nbPaniers = em.createQuery("SELECT COUNT(p) FROM Panier p", Long.class).getSingleResult();

            System.out.println("Nombre de produits : " + nbProduits);
            System.out.println("Nombre d'internautes : " + nbInternautes);
            System.out.println("Nombre de paniers : " + nbPaniers);

            System.out.println("\n=== Les tables ont été créées avec succès dans la base de données ! ===");

        } catch (Exception e) {
            System.err.println("❌ Erreur : " + e.getMessage());
            e.printStackTrace();

            if (em != null && em.getTransaction().isActive()) {
                System.out.println("Rollback de la transaction...");
                em.getTransaction().rollback();
            }
        } finally {
            // Fermer les ressources
            if (em != null) {
                em.close();
                System.out.println("EntityManager fermé");
            }
            if (emf != null) {
                emf.close();
                System.out.println("EntityManagerFactory fermé");
            }
        }
    }
}