# Application web basée sur MVC2 et JPA

## Vue d'ensemble du projet

**Nom du projet :** Atelier2 - Application E-commerce "SalmaStore"  
**Type :** Application Web Java EE avec JPA  
**Architecture :** MVC (Model-View-Controller)  
**Base de données :** MySQL  
**Framework :** Jakarta EE (anciennement Java EE)  

---

## Architecture technique

### Stack technologique
- **Backend :** Java 23, Jakarta EE 6.0
- **ORM :** JPA (Java Persistence API) avec EclipseLink
- **Base de données :** MySQL 9.4.0
- **Frontend :** JSP (JavaServer Pages) + JSTL + CSS3
- **Build tool :** Maven 3.x
- **Serveur :** Compatible Tomcat/Jetty
- **Utilitaires :** Lombok pour la réduction du code boilerplate

### Structure du projet
```
atelier2/
├── src/main/java/ma/fstt/atelier2/
│   ├── model/           # Entités JPA
│   ├── servlet/         # Contrôleurs
│   ├── tools/           # Utilitaires JPA
│   └── TestJPA.java     # Tests de persistance
├── src/main/webapp/
│   ├── WEB-INF/         # Pages JSP
│   ├── css/             # Styles
│   └── js/              # JavaScript
└── src/main/resources/
    └── META-INF/        # Configuration JPA
```

---
##  Schéma de la Base de Données
### Diagramme de classe : 
<img src="https://github.com/user-attachments/assets/493c21ac-8c84-4a3d-b863-bf5aceff4e31" alt="Image" width="400" />

## Modèle de données (Entités JPA)

### 1. **Internaute** (Utilisateur)
```java
@Entity
@Table(name = "internaute")
```
**Attributs :**
- `id` : Identifiant unique (auto-généré)
- `nom` : Nom de l'utilisateur
- `email` : Adresse email (unique)
- `password` : Mot de passe hashé (SHA-256)
- `panier` : Relation OneToOne avec Panier

**Fonctionnalités :**
- Authentification sécurisée avec hashage SHA-256
- Gestion de session utilisateur
- Profil utilisateur avec modification

### 2. **Produit**
```java
@Entity
@Table(name = "produit")
```
**Attributs :**
- `id` : Identifiant unique
- `nom` : Nom du produit
- `description` : Description détaillée
- `prix` : Prix en DH (Dirhams marocains)
- `stock` : Quantité disponible

### 3. **Vitrine**
```java
@Entity
@Table(name = "vitrine")
```
**Attributs :**
- `id` : Identifiant unique
- `nom` : Nom de la vitrine (auto-généré si vide)
- `date` : Date de création
- `produits` : Liste des produits (OneToMany)

**Fonctionnalités :**
- Organisation des produits par vitrines
- Recherche de produits par nom/description
- Affichage par catégories

### 4. **Panier**
```java
@Entity
@Table(name = "panier")
```
**Attributs :**
- `id` : Identifiant unique
- `dateCreation` : Date de création (auto-générée)
- `lignes` : Liste des lignes de panier (OneToMany)

**Fonctionnalités :**
- Ajout/suppression de produits
- Modification des quantités
- Calcul automatique du total
- Vider le panier

### 5. **LignePanier**
```java
@Entity
@Table(name = "ligne_panier")
```
**Attributs :**
- `id` : Identifiant unique
- `quantite` : Quantité du produit
- `panier` : Référence au panier (ManyToOne)
- `produit` : Référence au produit (ManyToOne)
<img src="https://github.com/user-attachments/assets/5b01f4b1-4234-44f6-a552-9fa5a5e56097" alt="Image" width="600" />
---

##  Contrôleurs (Servlets)

### 1. **AccueilServlet** (`/accueil`)
**Fonctionnalités :**
- Affichage de la page d'accueil
- Recherche de produits par nom/description
- Gestion des vitrines et produits
- Messages de bienvenue personnalisés

**Méthodes :**
- `doGet()` : Affichage avec recherche optionnelle

### 2. **InternauteServlet** (`/internaute`)
**Fonctionnalités :**
- Authentification (login/logout)
- Inscription de nouveaux utilisateurs
- Gestion du profil utilisateur
- Modification du mot de passe
- Suppression de compte

**Actions supportées :**
- `login` : Connexion utilisateur
- `add` : Inscription
- `update` : Modification profil
- `changePassword` : Changement mot de passe
- `logout` : Déconnexion
- `delete` : Suppression compte

### 3. **PanierServlet** (`/panier`)
**Fonctionnalités :**
- Gestion complète du panier d'achat
- Ajout/suppression de produits
- Modification des quantités
- Calcul du total

**Actions supportées :**
- `ajouter` : Ajouter un produit
- `supprimer` : Supprimer un produit
- `inc/dec` : Augmenter/diminuer quantité
- `vider` : Vider le panier
- `afficher` : Afficher le panier

### 4. **ProduitServlet** (`/produit`)
**Fonctionnalités :**
- Gestion des produits (CRUD)
- Ajout de nouveaux produits
- Liste des produits

### 5. **VitrineServlet** (`/vitrine`)
**Fonctionnalités :**
- Gestion des vitrines
- Organisation des produits

---

##  Interface utilisateur

### Design et UX
- **Thème :** Design moderne avec palette rose/rose clair
- **Police :** Poppins (Google Fonts)
- **Style :** Interface féminine et élégante
- **Responsive :** Adaptation mobile incluse

### Pages principales

#### 1. **Page de connexion** (`login.jsp`)
- Formulaire de connexion élégant
- Validation côté client et serveur
- Lien vers l'inscription
- Messages d'erreur personnalisés

  <img src="https://github.com/user-attachments/assets/427f430b-92d5-4eb9-bd36-e6cf7bdcf4a2" alt="Image" width="600" />

#### 2. **Page d'accueil** (`index.jsp`)
- Affichage des vitrines et produits
- Recherche en temps réel
- Boutons d'ajout au panier
- Messages de bienvenue personnalisés

  <img src="https://github.com/user-attachments/assets/5a1cfd25-9334-4f7c-9b28-55f037d8055f" alt="Image" width="600" />

#### 3. **Page du panier** (`panier/list.jsp`)
- Tableau des produits avec quantités
- Boutons d'incrémentation/décrémentation
- Calcul automatique du total
- Actions de gestion du panier

  <img src="https://github.com/user-attachments/assets/ffc4c957-2edc-4a16-9fc4-ac279fd0ae3f" alt="Image" width="600" />

#### 4. **Page du profil** (`profile.jsp`)
- Fonction : Affichage des informations et modification du profil utilisateur
- Design : Cartes d'information élégantes
- Fonctionnalités : Boutons d'action pour modification
<img src="https://github.com/user-attachments/assets/f228016b-7699-4d20-8a39-0de8287570ce" alt="Image" width="600" />

### Composants réutilisables
- **Header** : Navigation et informations utilisateur
- **Footer** : Informations générales
- **Navbar** : Menu de navigation

---

##  Configuration et déploiement

### Configuration JPA (`persistence.xml`)
```xml
<persistence-unit name="default" transaction-type="RESOURCE_LOCAL">
    <provider>org.eclipse.persistence.jpa.PersistenceProvider</provider>
    <!-- Entités déclarées -->
    <properties>
        <property name="jakarta.persistence.jdbc.driver" value="com.mysql.cj.jdbc.Driver"/>
        <property name="jakarta.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/atelier2jpa"/>
        <property name="jakarta.persistence.jdbc.user" value="root"/>
        <property name="jakarta.persistence.jdbc.password" value=""/>
    </properties>
</persistence-unit>
```

### Configuration Web (`web.xml`)
- Page d'accueil : `login.jsp`
- Configuration Jakarta EE 6.0
- Gestion des sessions

### Base de données
- **SGBD :** MySQL
- **Base :** `atelier2jpa`
- **Tables :** Générées automatiquement par JPA
- **Relations :** Gérées par annotations JPA

---

##  Fonctionnalités principales

### 1. **Gestion des utilisateurs**
- ✅ Inscription avec validation
- ✅ Connexion sécurisée (SHA-256)
- ✅ Gestion de session
- ✅ Modification du profil
- ✅ Changement de mot de passe
- ✅ Déconnexion

### 2. **Catalogue produits**
- ✅ Affichage par vitrines
- ✅ Recherche de produits
- ✅ Informations détaillées (prix, stock, description)
- ✅ Interface utilisateur intuitive

### 3. **Panier d'achat**
- ✅ Ajout/suppression de produits
- ✅ Modification des quantités
- ✅ Calcul automatique du total
- ✅ Persistance en session
- ✅ Vider le panier

### 4. **Interface utilisateur**
- ✅ Design moderne et responsive
- ✅ Navigation intuitive
- ✅ Messages de feedback
- ✅ Validation des formulaires

---

##  Points forts du projet

### Architecture
- **Séparation des responsabilités** : MVC bien structuré
- **ORM moderne** : JPA avec EclipseLink
- **Sécurité** : Hashage des mots de passe
- **Gestion des sessions** : Authentification robuste

### Code
- **Annotations Lombok** : Réduction du code boilerplate
- **Gestion des transactions** : Rollback en cas d'erreur
- **Validation** : Côté client et serveur
- **Gestion d'erreurs** : Try-catch appropriés

### Interface
- **Design moderne** : Interface utilisateur attrayante
- **Responsive** : Adaptation mobile
- **UX optimisée** : Navigation fluide
- **Feedback utilisateur** : Messages d'état

---

## Métriques du projet

### Code
- **Lignes de code :** ~2000+ lignes
- **Fichiers Java :** 12 classes
- **Pages JSP :** 8+ pages
- **Servlets :** 6 contrôleurs

### Base de données
- **Tables :** 5 entités principales
- **Relations :** 8 relations JPA
- **Contraintes :** Clés étrangères et index

### Dépendances
- **Jakarta EE :** 6.1.0
- **EclipseLink :** 4.0.2
- **MySQL Connector :** 9.4.0
- **Lombok :** 1.18.42

---

##  Conclusion

Le projet **Atelier2 - SalmaStore** est une application e-commerce complète et bien structurée qui démontre une maîtrise solide des technologies Java EE modernes. L'architecture MVC, l'utilisation de JPA, et l'interface utilisateur soignée en font un projet de qualité professionnelle.

**Points remarquables :**
- Architecture propre et maintenable
- Interface utilisateur moderne et intuitive
- Gestion complète du cycle de vie des utilisateurs
- Système de panier fonctionnel
- Code bien documenté et structuré



--
##  Réalisé par

**ESSALHI SALMA**  
Filière : *Logiciels et Systèmes Intelligents*  
Sous la supervision de **Pr. ELAACHAK LOTFI**

*Rapport généré le : 16/10/2025*  
*Projet : Atelier2 - SalmaStore*  
*Technologies : Java EE, JPA, MySQL, JSP*
