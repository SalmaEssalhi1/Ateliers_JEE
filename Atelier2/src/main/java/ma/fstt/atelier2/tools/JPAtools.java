package ma.fstt.atelier2.tools;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAtools {

    // Create only one EntityManagerFactory for the whole application
    private static final EntityManagerFactory emf;

    static {
        try {
            // "default" must match the name in your persistence.xml
            emf = Persistence.createEntityManagerFactory("default");
        } catch (Throwable ex) {
            System.err.println("Initial EntityManagerFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    // Method to get a new EntityManager each time
    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // Optional: method to close the factory (useful on application shutdown)
    public static void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
