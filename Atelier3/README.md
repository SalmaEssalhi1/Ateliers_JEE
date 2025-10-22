# RAPPORT TECHNIQUE - PROJET SALMASTORE

##  Vue d'ensemble du projet

**SalmaStore** est une application E-commerce moderne développée avec Java EE, utilisant JSF (Jakarta Server Faces) et PrimeFaces pour l'interface utilisateur. Le projet implémente une architecture MVC avec CDI (Contexts and Dependency Injection) et JPA pour la persistance des données.

---

##  Informations générales

| **Propriété** | **Valeur** |
|---------------|------------|
| **Nom du projet** | SalmaStore |
| **Type d'application** | Application Web E-Commerce |
| **Version** | 1.0-SNAPSHOT |
| **Packaging** | WAR (Web Archive) |
| **Java Version** | 17 |
| **Serveur d'application** | WildFly |
| **Base de données** | MySQL |

---

##  Architecture technique

### Stack technologique

#### Backend
- **Java EE 17** - Plateforme d'entreprise
- **Jakarta Server Faces (JSF) 4.1.1** - Framework web MVC
- **Jakarta CDI 4.0.1** - Injection de dépendances
- **Jakarta Persistence (JPA) 3.1.0** - ORM
- **Hibernate 6.2.7.Final** - Implémentation JPA
- **Jakarta Bean Validation 3.0.2** - Validation des données

#### Frontend
- **PrimeFaces 13.0.5** - Composants UI JSF
- **CSS3** - Styles personnalisés avec thème rose/magenta
- **JavaScript** - Interactions côté client
- **Responsive Design** - Interface adaptative

#### Base de données
- **MySQL 8.0.33** - SGBD relationnel
- **JTA (Java Transaction API)** - Gestion des transactions

#### Outils de développement
- **Maven 3.x** - Gestion des dépendances et build
- **Lombok 1.18.32** - Génération automatique de code
- **JUnit 5.11.0** - Tests unitaires
- **WildFly Maven Plugin 5.0.0** - Déploiement automatique

---

##  Structure du projet

```
salma/
├── src/main/
│   ├── java/net/salma/salma/
│   │   ├── bean/                    # Contrôleurs JSF
│   │   │   ├── AuthBean.java        # Authentification
│   │   │   ├── InternauteBean.java # Gestion utilisateurs
│   │   │   ├── PanierBean.java     # Gestion panier
│   │   │   ├── ProduitBean.java    # Gestion produits
│   │   │   ├── UserBean.java       # Administration utilisateurs
│   │   │   └── VitrineBean.java    # Catalogue produits
│   │   ├── model/                   # Entités JPA
│   │   │   ├── Internaute.java     # Utilisateur
│   │   │   ├── Produit.java        # Produit
│   │   │   ├── Panier.java         # Panier d'achat
│   │   │   ├── LignePanier.java    # Ligne de panier
│   │   │   └── Role.java           # Rôles utilisateur
│   │   └── service/                 # Services métier
│   │       ├── InternauteService.java
│   │       ├── PanierService.java
│   │       └── ProduitService.java
│   ├── resources/
│   │   ├── META-INF/
│   │   │   ├── beans.xml           # Configuration CDI
│   │   │   └── persistence.xml     # Configuration JPA
│   │   └── import.sql              # Données initiales
│   └── webapp/
│       ├── WEB-INF/
│       │   ├── templates/
│       │   │   └── layout.xhtml    # Template principal
│       │   └── web.xml             # Configuration web
│       ├── admin/                   # Pages administration
│       │   ├── produits.xhtml
│       │   └── users.xhtml
│       ├── resources/css/          # Styles CSS
│       │   ├── style.css           # Style principal
│       │   └── auth.css            # Style authentification
│       └── *.xhtml                  # Pages JSF
└── pom.xml                         # Configuration Maven
```

---

##  Modèle de données

### Entités principales

#### 1. **Internaute** (Utilisateur)
```java
@Entity
@Table(name = "internaute")
public class Internaute {
    private Long id;
    private String nom;
    private String email;
    private String motDePasse;
    private Set<Role> roles;
    private Panier panier;
}
```

#### 2. **Produit**
```java
@Entity
@Table(name = "produit")
public class Produit {
    private Long id;
    private String libelle;
    private String description;
    private BigDecimal prix;
    private Integer stock;
    private String imageUrl;
}
```

#### 3. **Panier**
```java
@Entity
@Table(name = "panier")
public class Panier {
    private Long id;
    private Internaute internaute;
    private List<LignePanier> lignes;
    // Méthodes calculées
    public BigDecimal getTotal()
    public int getNombreArticles()
}
```

#### 4. **LignePanier**
```java
@Entity
@Table(name = "ligne_panier")
public class LignePanier {
    private Long id;
    private Panier panier;
    private Produit produit;
    private Integer quantite;
    private BigDecimal prixUnitaire;
    private BigDecimal sousTotal;
}
```

#### 5. **Role** (Enumération)
```java
public enum Role {
    ADMIN,      // Administrateur
    VENDEUR,    // Vendeur
    ACHETEUR    // Acheteur
}
```

### Relations entre entités

- **Internaute ↔ Panier** : Relation One-to-One
- **Panier ↔ LignePanier** : Relation One-to-Many
- **Produit ↔ LignePanier** : Relation One-to-Many
- **Internaute ↔ Role** : Relation Many-to-Many

---

##  Interface utilisateur

### Design et thème

Le projet utilise un **thème personnalisé rose/magenta** avec les caractéristiques suivantes :

#### Palette de couleurs
- **Primaire** : `#f06292` (Rose vif)
- **Secondaire** : `#ec407a` (Rose magenta)
- **Accent** : `#d81b60` (Rose foncé)
- **Arrière-plan** : Dégradé `#fce4ec` vers `#f8bbd0`

#### Caractéristiques visuelles
- **Typographie** : Police Poppins pour une apparence moderne
- **Bordures arrondies** : 20px pour les cartes, 25px pour les boutons
- **Animations** : Transitions fluides et effets d'apparition
- **Design responsive** : Adaptation mobile et desktop

### Pages principales

#### 1. **Page d'accueil** (`index.xhtml`)
- Affichage du catalogue produits
- Cartes produits avec images, prix et stock
- Boutons d'ajout au panier
- Interface adaptative
  
<img src="https://github.com/user-attachments/assets/abe31345-1d23-448d-b399-ca9bf797ca1a" alt="Image" width="600" />

#### 2. **Authentification**
- **Connexion** (`login.xhtml`) : Formulaire de connexion
- **Inscription** (`register.xhtml`) : Création de compte avec choix de rôle
- **Gestion d'erreurs** (`login-error.xhtml`) : Page d'erreur stylisée

<img src="https://github.com/user-attachments/assets/f6f93fb8-c42b-4060-b68d-4addacfcd4f8" alt="Image" width="500" />
<img src="https://github.com/user-attachments/assets/4f69a2d0-0811-4113-b5a0-ff255a061d72" alt="Image" width="345" />

#### 3. **Panier** (`panier.xhtml`)
- Affichage des articles sélectionnés
- Modification des quantités
- Calcul automatique du total
- Bouton de validation de commande

#### 4. **Administration**
- **Gestion produits** (`admin/produits.xhtml`) : CRUD complet
- **Gestion utilisateurs** (`admin/users.xhtml`) : Administration des comptes
<img src="https://github.com/user-attachments/assets/7a6d7b72-d01f-404d-bac0-769ee54e60fe" alt="Image" width="600" />

#### 5. **Confirmation** (`checkout.xhtml`)
- Page de confirmation de commande
- Informations client et commande
<img src="https://github.com/user-attachments/assets/e281fc5f-21ed-4880-84dc-d62fdcf001a8" alt="Image" width="600" />

---

##  Fonctionnalités implémentées

### Authentification et autorisation

#### Gestion des utilisateurs
- **Inscription** : Création de compte avec validation
- **Connexion** : Authentification par email/mot de passe
- **Déconnexion** : Invalidation de session
- **Rôles** : ADMIN, VENDEUR, ACHETEUR
<img src="https://github.com/user-attachments/assets/85aa0961-405c-4eef-8202-38bea9e38ec6" alt="Image" width="600" />

#### Sécurité
- Validation des données côté client et serveur
- Gestion des sessions utilisateur
- Contrôle d'accès basé sur les rôles

### Gestion des produits

#### Catalogue
- Affichage de tous les produits disponibles
- Filtrage par stock disponible
- Recherche et tri
- Images produits avec placeholders

#### Administration
- **CRUD complet** : Création, lecture, mise à jour, suppression
- **Gestion du stock** : Suivi des quantités disponibles
- **Validation** : Contrôles de saisie et contraintes métier

### Panier d'achat

#### Fonctionnalités
- **Ajout de produits** : Sélection depuis le catalogue
- **Modification des quantités** : Augmentation/diminution
- **Suppression d'articles** : Retrait du panier
- **Calcul automatique** : Total et nombre d'articles
- **Persistance** : Sauvegarde en base de données

#### Gestion avancée
- Vérification du stock disponible
- Mise à jour en temps réel
- Validation avant commande

### Administration

#### Gestion des utilisateurs
- Liste de tous les utilisateurs
- Modification des rôles
- Suppression de comptes
- Filtrage et recherche

#### Gestion des produits
- Interface d'administration complète
- Ajout/modification de produits
- Gestion des images et descriptions
- Contrôle des prix et stocks

---

##  Configuration et déploiement

### Configuration Maven

Le projet utilise Maven avec les plugins suivants :

```xml
<plugins>
    <plugin>
        <groupId>org.wildfly.plugins</groupId>
        <artifactId>wildfly-maven-plugin</artifactId>
        <version>5.0.0.Final</version>
    </plugin>
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
        <version>3.11.0</version>
    </plugin>
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-war-plugin</artifactId>
        <version>3.4.0</version>
    </plugin>
</plugins>
```

### Configuration JPA

```xml
<persistence-unit name="mycnx" transaction-type="JTA">
    <provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>
    <jta-data-source>java:jboss/datasources/MySQLDS</jta-data-source>
    <properties>
        <property name="hibernate.hbm2ddl.auto" value="update"/>
        <property name="hibernate.show_sql" value="true"/>
        <property name="hibernate.format_sql" value="true"/>
    </properties>
</persistence-unit>
```

### Configuration JSF

```xml
<context-param>
    <param-name>jakarta.faces.PROJECT_STAGE</param-name>
    <param-value>Development</param-value>
</context-param>
<context-param>
    <param-name>primefaces.THEME</param-name>
    <param-value>saga</param-value>
</context-param>
```

---

##  Données de test

Le projet inclut des données de démonstration dans `import.sql` :

### Produits de test
- **Laptop HP** - 6,500 DH
- **Smartphone Samsung Galaxy** - 3,200 DH
- **Casque Bluetooth Sony** - 2,800 DH
- **Tablette iPad Air** - 4,500 DH
- **Montre Connectée Apple Watch** - 3,800 DH
- **Clavier Mécanique Logitech** - 1,200 DH
- **Souris Gaming Razer** - 550 DH
- **Écran Dell UltraSharp** - 4,200 DH
- **Imprimante Canon** - 980 DH
- **Disque Dur Externe Seagate** - 680 DH

---

##  Tests et qualité

### Tests unitaires
- Configuration JUnit 5
- Tests des services métier
- Validation des entités

### Qualité du code
- Utilisation de Lombok pour réduire le code boilerplate
- Respect des conventions Java EE
- Architecture MVC bien structurée
- Séparation des responsabilités

---

##  Métriques du projet

| **Métrique** | **Valeur** |
|--------------|------------|
| **Lignes de code** | ~2,500 lignes |
| **Nombre de classes** | 15 classes |
| **Pages JSF** | 11 pages |
| **Entités JPA** | 5 entités |
| **Services** | 3 services |
| **Beans JSF** | 6 beans |

---

##  Conclusion

**SalmaStore** est une application e-commerce moderne et bien structurée qui démontre une maîtrise des technologies Java EE. Le projet présente :

### Points forts
- ✅ **Architecture solide** : Respect des patterns MVC et CDI
- ✅ **Interface moderne** : Design responsive et thème cohérent
- ✅ **Fonctionnalités complètes** : CRUD, authentification, panier
- ✅ **Code maintenable** : Structure claire et documentation

### Technologies maîtrisées
- ✅ **Java EE 17** : Jakarta EE moderne
- ✅ **JSF/PrimeFaces** : Interface utilisateur riche
- ✅ **JPA/Hibernate** : Persistance des données
- ✅ **Maven** : Gestion des dépendances
- ✅ **WildFly** : Serveur d'application

Le projet constitue une base solide pour une application e-commerce professionnelle et peut être étendu avec les améliorations suggérées pour répondre aux besoins d'un environnement de production.

---

##  Réalisé par

**ESSALHI SALMA**  
Filière : *Logiciels et Systèmes Intelligents*  
Sous la supervision de **Pr. ELAACHAK LOTFI**
**Date du rapport** : Janvier 2025  
**Version du projet** : 1.0-SNAPSHOT  
**Statut** : Fonctionnel et déployable
