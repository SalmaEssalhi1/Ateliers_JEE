#  Application Web de Gestion des Commandes – JEE (MVC2)

##  Description du Projet

Ce projet consiste à développer une **application web de gestion des commandes** respectant l’architecture **MVC2** et les standards **Jakarta EE (JEE)**.  
Elle permet la gestion complète des **clients**, **produits**, **commandes** et **lignes de commande**, tout en appliquant les bonnes pratiques de développement d’applications web dynamiques.

---

##  Objectifs Pédagogiques

- Maîtriser les **standards JEE** : Servlets, JSP, JavaBeans, DAO  
- Implémenter le **patron MVC2** (Modèle - Vue - Contrôleur)  
- Concevoir et implémenter une **base de données MySQL**  
- Développer une **application web dynamique** avec Jakarta EE  
- Appliquer les **bonnes pratiques de conception logicielle**

---

##  Architecture et Technologies

| Couche | Description | Packages / Technologies |
|--------|--------------|--------------------------|
| **Modèle (Model)** | Entités métier et DAO | `ma.fstt.entities`, `ma.fstt.dao`, `ma.fstt.service` |
| **Contrôleur (Controller)** | Gestion des requêtes HTTP (Servlets) | `ma.fstt.controller` |
| **Vue (View)** | Pages JSP et interface utilisateur | `/WEB-INF/views`, HTML, CSS, JavaScript, jQuery |

###  Technologies utilisées

- **IDE :** IntelliJ IDEA  
- **Serveur d’application :** WildFly 37  
- **Framework :** Jakarta EE 10 (Servlet 6.0, JSP 3.0)  
- **Base de données :** MySQL 8.3  
- **Connecteur JDBC :** MySQL Connector/J 8.3  
- **Gestionnaire de dépendances :** Maven  

---

##  Schéma de la Base de Données

### Tables principales :

- **Client** (`idClient`, `nom`, `prenom`, `email`, `adresse`)  
- **Produit** (`idProduit`, `nomProduit`, `prix`, `stock`)  
- **Commande** (`idCommande`, `dateCommande`, `prixTotal`, `idClient`)  
- **LigneDeCommande** (`idLigne`, `quantite`, `prixUnitaire`, `idCommande`, `idProduit`)

### Relations :
- 1 client → plusieurs commandes  
- 1 commande → plusieurs lignes de commande  
- 1 ligne de commande → 1 produit  

---

##  Dépendances Maven principales

```xml
<!-- MySQL Connector -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.3</version>
</dependency>

<!-- Jakarta EE -->
<dependency>
    <groupId>jakarta.platform</groupId>
    <artifactId>jakarta.jakartaee-api</artifactId>
    <version>10.0.0</version>
</dependency>

```
##  Fonctionnalités Implémentées

###  Gestion des Clients
- Affichage, ajout, modification, suppression  
- Formulaire dynamique et interface moderne  

###  Gestion des Produits
- CRUD complet (ajout, modification via modale, suppression)  
- Gestion du stock et du prix  

###  Gestion des Commandes
- Liste des commandes et détails dynamiques  
- Création de commande avec calcul automatique du prix total  

###  Gestion des Lignes de Commande
- Ajout / suppression de produits à une commande  
- Mise à jour automatique du stock et du total  

###  Interface Utilisateur
- Design moderne (dégradé bleu/violet)  
- Modales pour l’édition  
- Notifications Toast (succès / erreur)  
- AJAX / jQuery pour interactions dynamiques  

---

##  Défis et Solutions

| Problème | Solution |
|-----------|-----------|
| Gestion des exceptions AJAX | Réponses JSON explicites pour les erreurs |
| Intégrité des données | Vérifications d’existence des entités et du stock avant chaque opération |
| Cohérence du stock | Décrément automatique lors de l’ajout, incrément lors de la suppression |

---

##  Résultats

- Application totalement fonctionnelle et fluide  
- CRUD complet sur toutes les entités  
- Calcul automatique du total de commande  
- Interface intuitive et réactive  
- Respect des standards Jakarta EE, MVC2, et DAO  

---

##  Exécution du Projet

###  Configuration de la Base de Données

Créer une base de données MySQL nommée **`atelier1_mvc2`**, puis importer le schéma suivant :

```sql
CREATE DATABASE atelier1_mvc2;
USE atelier1_mvc2;

CREATE TABLE client (
  idClient INT AUTO_INCREMENT PRIMARY KEY,
  nom VARCHAR(100),
  prenom VARCHAR(100),
  email VARCHAR(100),
  adresse VARCHAR(200)
);

CREATE TABLE produit (
  idProduit INT AUTO_INCREMENT PRIMARY KEY,
  nomProduit VARCHAR(100),
  prix DOUBLE,
  stock INT
);

CREATE TABLE commande (
  idCommande INT AUTO_INCREMENT PRIMARY KEY,
  dateCommande DATE,
  prixTotal DOUBLE,
  idClient INT,
  FOREIGN KEY (idClient) REFERENCES client(idClient)
);
 
CREATE TABLE lignedecommande (
  idLigne INT AUTO_INCREMENT PRIMARY KEY,
  quantite INT,
  prixUnitaire DOUBLE,
  idCommande INT,
  idProduit INT,
  FOREIGN KEY (idCommande) REFERENCES commande(idCommande),
  FOREIGN KEY (idProduit) REFERENCES produit(idProduit)
);
```
##  Configuration du Fichier `pom.xml` ou DAO

Assurez-vous de modifier les informations de connexion à la base **MySQL** selon votre environnement local.

---

##  Déploiement sur WildFly

1. Ouvrir le projet dans **IntelliJ IDEA** ou **Eclipse**  
2. Compiler avec **Maven → clean install**  
3. Déployer le fichier `.war` sur **WildFly 37**  
4. Accéder à l’application via :  
    [http://localhost:8080/atelier1_mvc2/](http://localhost:8080/atelier1_mvc2/)

---

##  Réalisé par

**ESSALHI SALMA**  
Filière : *Logiciels et Systèmes Intelligents*  
Sous la supervision de **Pr. ELAACHAK LOTFI**

