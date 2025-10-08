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
### Diagramme de classe : 
-> Le diagramme de classe a été conçu avec quatre entités principales :

<img src="https://github.com/user-attachments/assets/ce6d0f61-8187-4663-989d-decc03e509cc" alt="Image" width="400" />

### Tables principales :

- **Client** (`idClient`, `nom`, `prenom`, `email`, `adresse`)  
- **Produit** (`idProduit`, `nomProduit`, `prix`, `stock`)  
- **Commande** (`idCommande`, `dateCommande`, `prixTotal`, `idClient`)  
- **LigneDeCommande** (`idLigne`, `quantite`, `prixUnitaire`, `idCommande`, `idProduit`)
  
<img src="https://github.com/user-attachments/assets/128a14c1-589a-47d7-a574-d3f4ed5799d6" alt="Image" width="700" />
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

###  Interface Utilisateur
- Design moderne (dégradé bleu/violet)  
- Modales pour l’édition  
- Notifications Toast (succès / erreur)  
- AJAX / jQuery pour interactions dynamiques
  
  <img src="https://github.com/user-attachments/assets/78b17ae3-6cea-4df7-ba3c-3b28a32bc3fb" alt="Image" width="600" />

###  Gestion des Clients
- Affichage, ajout, modification, suppression  
- Formulaire dynamique et interface moderne
  
<img src="https://github.com/user-attachments/assets/f427bd65-b726-4b77-8af2-c052e1f92979" alt="Image" width="600" />
<img src="https://github.com/user-attachments/assets/a0964380-007f-4743-b9ab-2e47e15f35c3" alt="Image" width="600" />

###  Gestion des Produits
- CRUD complet (ajout, modification via modale, suppression)  
- Gestion du stock et du prix  

<img src="https://github.com/user-attachments/assets/80e3de5d-c81f-476d-b056-51dd07bee51b" alt="Image" width="600" />
<img src="https://github.com/user-attachments/assets/b6af9939-c3a2-4a5b-99e8-50b31a6c2ce8" alt="Image" width="600" />

###  Gestion des Commandes
- Liste des commandes et détails dynamiques  
- Création de commande avec calcul automatique du prix total

  <img src="https://github.com/user-attachments/assets/49715f8c-c186-4a83-9c02-2168ff169325" alt="Image" width="600" />

###  Gestion des Lignes de Commande
- Ajout / suppression de produits à une commande  
- Mise à jour automatique du stock et du total  

<img src="https://github.com/user-attachments/assets/94cd8707-f810-4e8d-884f-428d96cdb55e" alt="Image" width="600" />
<img src="https://github.com/user-attachments/assets/941a9812-d187-46af-b0a7-9ec965def4e7" alt="Image" width="600" />
  

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

