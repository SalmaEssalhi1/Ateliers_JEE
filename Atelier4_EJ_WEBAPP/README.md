#  Application de Gestion Étudiants

Application web de gestion académique développée avec **Jakarta EE 10**, **EJB3** et **JPA/Hibernate**.

---

##  Table des Matières

1. [Introduction](#introduction)
2. [Fonctionnalités](#fonctionnalités)
3. [Technologies Utilisées](#technologies-utilisées)
4. [Installation et Déploiement](#installation-et-déploiement)
5. [Guide d'Utilisation](#guide-dutilisation)
6. [Architecture Technique](#architecture-technique)
7. [Conception de la Base de Données](#conception-de-la-base-de-données)
8. [API des Servlets](#api-des-servlets)
9. [Tests et Validation](#tests-et-validation)
10. [Difficultés et Solutions](#difficultés-et-solutions)
11. [Améliorations Possibles](#améliorations-possibles)
12. [Conclusion](#conclusion)

---

## 1. Introduction

### 1.1 Contexte et Objectifs

Ce projet consiste à développer une application web de gestion académique permettant de gérer les étudiants, les modules d'enseignement et leurs notes. Développé dans le cadre du cours sur **Jakarta EE 10** à l'Université Abdelmalek Essaadi.

**Objectifs principaux :**
- Maîtriser l'architecture multi-tiers avec Jakarta EE 10
- Implémenter une application avec EJB3 pour la logique métier
- Utiliser JPA/Hibernate pour la persistance des données
- Créer une interface web moderne avec JSP/JSTL
- Appliquer les bonnes pratiques de développement Java EE

**Portée du projet :**
1. Gérer les informations des étudiants
2. Gérer les modules d'enseignement
3. Enregistrer et suivre les notes des étudiants par module

### 1.2 Établissement

**Université Abdelmalek Essaadi**  
**Faculté des Sciences et Techniques de Tanger**  
**Département Génie Informatique**

---

## 2. Fonctionnalités

<img src="https://github.com/user-attachments/assets/3d0c0e17-510b-43c4-9e73-493bef021a07" alt="Image" width="550" />


### 👥 Gestion des Étudiants
- ✅ Créer un nouvel étudiant
- ✅ Lister tous les étudiants
- ✅ Modifier les informations d'un étudiant
- ✅ Supprimer un étudiant
- ✅ Interface intuitive avec validation des données

<img src="https://github.com/user-attachments/assets/d9e1890c-959b-44f1-a78a-541b5edc6795" alt="Image" width="550" />
  
### 📚 Gestion des Modules
- ✅ Ajouter de nouveaux modules d'enseignement
- ✅ Consulter la liste des modules
- ✅ Modifier les détails d'un module
- ✅ Supprimer un module
- ✅ Affichage du code, nom et description

<img src="https://github.com/user-attachments/assets/52dfbd76-271d-4939-81c7-0d4fbb7f72d5" alt="Image" width="550" />

### 📊 Gestion des Notes (Suivie)
- ✅ Enregistrer les notes par étudiant et par module
- ✅ Consulter toutes les notes
- ✅ Modifier une note existante
- ✅ Supprimer une note
- ✅ Affichage coloré selon la performance (excellent, bon, moyen, faible)

  <img src="https://github.com/user-attachments/assets/5bfdfa11-4164-444b-8eb0-b295b5107f51" alt="Image" width="550" />

---

## 3. Technologies Utilisées

### 3.1 Stack Technique

| Technologie | Version | Utilisation |
|------------|---------|-------------|
| **Java** | 17 | Langage principal |
| **Jakarta EE** | 10 | Plateforme enterprise |
| **Jakarta Servlets** | 6.0 | Gestion des requêtes HTTP |
| **JSP/JSTL** | 3.0 | Vues dynamiques |
| **EJB** | 3.2 | Logique métier |
| **JPA/Hibernate** | 7.0.4 | Persistance des données |
| **Jakarta Persistence API** | 3.2.0 | API de persistance |
| **Jakarta Bean Validation** | 9.0.1 | Validation des données |
| **Maven** | - | Gestion des dépendances |
| **WildFly** | 27+ | Serveur d'application |
| **MySQL/MariaDB** | - | Base de données |

### 3.2 Infrastructure
- **Serveur d'application** : WildFly 27+ (Jakarta EE 10)
- **Base de données** : MySQL/MariaDB (SGBD relationnel)
- **Build Tool** : Maven

---

## 4. Installation et Déploiement

### 4.1 Prérequis

- Java JDK 17 ou supérieur
- Maven 3.6+
- WildFly 27+ (ou équivalent Jakarta EE 10)
- MySQL/MariaDB (pour la base de données)
- IDE (IntelliJ IDEA, Eclipse, etc.)

### 4.2 Configuration de la Base de Données

1. **Créer une base de données MySQL :**
```sql
CREATE DATABASE Getudiants;
```

2. **Configurer le fichier `persistence.xml`** avec vos paramètres de connexion

### 4.3 Compilation et Déploiement

1. **Cloner le projet** (si applicable)
```bash
git clone <repository-url>
cd JEE10
```

2. **Compiler le projet**
```bash
mvn clean compile
```

3. **Créer le package WAR**
```bash
mvn clean package
```

4. **Déployer sur WildFly**
```bash
# Copier le fichier WAR dans le répertoire deployments de WildFly
cp target/JEE10-1.0-SNAPSHOT.war $WILDFLY_HOME/standalone/deployments/
```

5. **Démarrer WildFly**
```bash
$WILDFLY_HOME/bin/standalone.sh  # Linux/Mac
$WILDFLY_HOME/bin/standalone.bat  # Windows
```

6. **Accéder à l'application**
```
http://localhost:8080/JEE10-1.0-SNAPSHOT/
```

### 4.4 Structure du Projet

```
JEE10/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── ma/fstt/
│   │   │       └── servlet/
│   │   │           ├── EtudiantServlet.java
│   │   │           ├── ModuleServlet.java
│   │   │           └── SuivieServlet.java
│   │   ├── webapp/
│   │   │   ├── index.jsp              # Page d'accueil
│   │   │   ├── etudiants/
│   │   │   │   ├── list.jsp           # Liste des étudiants
│   │   │   │   └── form.jsp           # Formulaire étudiant
│   │   │   ├── modules/
│   │   │   │   ├── list.jsp           # Liste des modules
│   │   │   │   └── form.jsp           # Formulaire module
│   │   ├── suivies/
│   │   │       ├── list.jsp           # Liste des notes
│   │   │       └── form.jsp           # Formulaire note
│   │   └── resources/
│   │       └── META-INF/
│   │           ├── persistence.xml
│   │           └── beans.xml
│   └── pom.xml
```

---

## 5. Guide d'Utilisation

### 5.1 Page d'Accueil
La page d'accueil présente trois cartes principales :
- 👥 **Étudiants** : Accéder à la gestion des étudiants
- 📚 **Modules** : Accéder à la gestion des modules
- 📊 **Notes** : Accéder à la gestion des notes

### 5.2 Créer un Étudiant
1. Cliquer sur "📋 Accéder" dans la carte Étudiants
2. Cliquer sur "➕ Ajouter un étudiant"
3. Remplir le formulaire :
   - CNE (ex: R123456789)
   - Nom
   - Prénom
   - Adresse
   - Niveau (ex: LSI 3)
4. Cliquer sur "✅ Ajouter"

### 5.3 Ajouter un Module
1. Cliquer sur "📖 Accéder" dans la carte Modules
2. Cliquer sur "➕ Ajouter un module"
3. Remplir :
   - Code Module (ex: INF101)
   - Nom du Module
   - Description
4. Cliquer sur "✅ Ajouter"

### 5.4 Enregistrer une Note
1. Cliquer sur "📝 Accéder" dans la carte Notes
2. Cliquer sur "➕ Ajouter une note"
3. Sélectionner :
   - Étudiant
   - Module
   - Note (sur 20)
   - Date
4. Cliquer sur "✅ Ajouter"

### 5.5 Interface Utilisateur

L'application dispose d'une interface moderne et élégante avec :
- **Palette de couleurs douce** : Rose (#f8d7da) et Mauve (#c8a2c8)
- **Dégradés harmonieux** : Transitions fluides
- **Animations fluides** : CSS animations (fadeIn, bounce, pulse)
- **Design responsive** : Mobile-friendly
- **Badges colorés** : Selon la performance des notes

---

## 6. Architecture Technique

### 6.1 Architecture Multi-Tiers (3-Tier)

L'application suit l'architecture **multi-tiers** :

```
┌─────────────────────────────────────────┐
│         COUCHE PRÉSENTATION            │
│  (JSP - Interface Utilisateur)         │
└────────────┬────────────────────────────┘
             │
┌────────────▼────────────────────────────┐
│         COUCHE CONTRÔLEUR              │
│  (Jakarta Servlets)                    │
└────────────┬────────────────────────────┘
             │
┌────────────▼────────────────────────────┐
│         COUCHE MÉTIER                   │
│  (EJB3 - Beans Session)                │
└────────────┬────────────────────────────┘
             │
┌────────────▼────────────────────────────┐
│         COUCHE PERSISTANCE             │
│  (JPA/Hibernate + Base de Données)    │
└─────────────────────────────────────────┘
```

### 6.2 Couche Présentation (JSP)

#### 6.2.1 Pages Principales
- **index.jsp** : Page d'accueil avec 3 cartes interactives
- **etudiants/list.jsp** : Tableau des étudiants
- **etudiants/form.jsp** : Formulaire étudiant
- **modules/list.jsp** : Liste des modules
- **modules/form.jsp** : Formulaire module
- **suivies/list.jsp** : Liste des notes avec badges
- **suivies/form.jsp** : Formulaire de note

#### 6.2.2 Design et UX
- **Animations CSS** : fadeIn, bounce, pulse
- **Hover effects** : Interactivité améliorée
- **Badges colorés** :
  - Excellent (≥16) : Lavender clair
  - Bon (≥14) : Rose clair
  - Moyen (≥10) : Rose moyen
  - Faible (<10) : Rose foncé

### 6.3 Couche Contrôleur (Jakarta Servlets)

#### 6.3.1 Architecture des Servlets
Les servlets héritent de `HttpServlet` et implémentent le pattern **Front Controller** :

```java
@WebServlet("/etudiant")
public class EtudiantServlet extends HttpServlet {
    private Object etudiantEJB; // Référence au bean EJB
    
    @Override
    public void init() throws ServletException {
        // Initialisation EJB via JNDI
        Context context = new InitialContext();
        String[] jndiNames = {
            "java:global/EJB/GestionEtudiantBean!...",
            "java:app/EJB/GestionEtudiantBean!...",
            "ejb:/EJB/GestionEtudiantBean!..."
        };
        // Recherche de l'EJB
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        // Gestion des requêtes GET
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        // Gestion des requêtes POST
    }
}
```

#### 6.3.2 Communication EJB
- **Récupération via JNDI** : Lookup des beans EJB
- **Réflexion Java** : Chargement dynamique des classes d'entités
- **Opérations CRUD** : Appels aux méthodes EJB

### 6.4 Couche Métier (EJB3)

Les beans EJB implémentent :
- `GestionEtudiantRemote` : Gestion des étudiants
- `GestionModuleRemote` : Gestion des modules
- `GestionSuivieRemote` : Gestion des notes

**Avantages :**
- Séparation claire des modules
- Communication inter-modules via JNDI
- Transactions déclaratives

### 6.5 Couche Persistance (JPA/Hibernate)

#### 6.5.1 Configuration
- **Fichier** : `persistence.xml`
- **Provider** : Hibernate 7.0.4
- **ORM** : Mapping objet-relationnel

#### 6.5.2 Entités JPA
Les entités sont annotées avec :
- `@Entity` : Entité JPA
- `@Id` : Clé primaire
- `@ManyToOne` : Relations
- `@Column` : Mapping des colonnes

### 6.6 Flux de Données

```
Requête HTTP → Servlet → EJB → JPA → Base de Données
     ↓                                              ↓
  JSP ← Données ← Servlet ← Résultat ← Résultat
```

---

## 7. Conception de la Base de Données

### 7.1 Modèle Entité-Relation

```
┌─────────────────┐         ┌─────────────────┐         ┌─────────────────┐
│   ETUDIANT      │         │     MODULE      │         │    SUIVIE       │
├─────────────────┤         ├─────────────────┤         ├─────────────────┤
│ id_etudiant (PK)│◄────┐   │ id_module (PK)  │◄────┐   │ id (PK)         │
│ cne             │     │   │ code            │     │   │ note            │
│ nom             │     │   │ nom             │     │   │ date            │
│ prenom          │     │   │ description     │     │   │ id_etudiant (FK)│
│ adresse         │     │   └─────────────────┘     │   │ id_module (FK)  │
│ niveau          │     │                           │   └─────────────────┘
└─────────────────┘     │                           │
                        └───────────────────────────┘
```

### 7.2 Description des Entités

#### 7.2.1 Etudiant
- **id_etudiant** : Identifiant unique (PK)
- **cne** : Code National Étudiant
- **nom** : Nom de famille
- **prenom** : Prénom
- **adresse** : Adresse complète
- **niveau** : Niveau académique (ex: LSI 3, M1)

#### 7.2.2 Module
- **id_module** : Identifiant unique (PK)
- **code** : Code du module (ex: INF101)
- **nom** : Nom du module
- **description** : Description détaillée

#### 7.2.3 Suivie
- **id** : Identifiant unique (PK)
- **note** : Note sur 20 (Double)
- **date** : Date d'attribution
- **id_etudiant** : Référence à Etudiant (FK)
- **id_module** : Référence à Module (FK)

### 7.3 Relations
- **Many-to-One** : Suivie → Etudiant (plusieurs notes par étudiant)
- **Many-to-One** : Suivie → Module (plusieurs notes par module)

---

## 8. API des Servlets

### 8.1 EtudiantServlet (`/etudiant`)
- `GET ?action=list` : Liste des étudiants
- `GET ?action=add` : Formulaire d'ajout
- `GET ?action=edit&id=X` : Formulaire de modification
- `POST ?action=insert` : Créer un étudiant
- `POST ?action=update` : Modifier un étudiant
- `GET ?action=delete&id=X` : Supprimer un étudiant

### 8.2 ModuleServlet (`/module`)
- `GET ?action=list` : Liste des modules
- `GET ?action=add` : Formulaire d'ajout
- `GET ?action=edit&id=X` : Formulaire de modification
- `POST ?action=insert` : Créer un module
- `POST ?action=update` : Modifier un module
- `GET ?action=delete&id=X` : Supprimer un module

### 8.3 SuivieServlet (`/suivie`)
- `GET ?action=list` : Liste des notes
- `GET ?action=add` : Formulaire d'ajout
- `GET ?action=edit&id=X` : Formulaire de modification
- `POST ?action=insert` : Créer une note
- `POST ?action=update` : Modifier une note
- `GET ?action=delete&id=X` : Supprimer une note

---

## 9. Tests et Validation

### 9.1 Tests Fonctionnels
- ✅ Création d'étudiants avec validation
- ✅ Ajout et modification de modules
- ✅ Attribution de notes avec relations
- ✅ Suppression en cascade
- ✅ Liste dynamique avec liens de navigation

### 9.2 Tests d'Interface
- ✅ Responsive design sur mobile
- ✅ Animations fluides
- ✅ Validation des formulaires
- ✅ Messages de confirmation

### 9.3 Gestion des Erreurs
- Gestion des exceptions de connexion EJB
- Messages d'erreur utilisateur clairs
- Validation côté serveur des données

### 9.4 Bonnes Pratiques Appliquées

#### Architecture
- ✅ Séparation des couches (MVC)
- ✅ Injection de dépendances via JNDI
- ✅ Principe DRY (Don't Repeat Yourself)

#### Code
- ✅ Nommage explicite
- ✅ Commentaires clairs
- ✅ Gestion propre des exceptions

#### Sécurité
- ✅ Validation des données côté serveur
- ✅ Précautions contre l'injection SQL (via JPA)

#### Performance
- ✅ Utilisation de connexions poolées
- ✅ Lazy loading pour les relations JPA

---

## 10. Difficultés et Solutions

### 10.1 Communication EJB-WEB
**Problème** : Les servlets ne trouvaient pas les beans EJB.

**Solution** : 
- Implémentation de multiples tentatives JNDI avec différents noms
- Utilisation de la réflexion pour contourner les problèmes de classpath
- Pattern de retry automatique

### 10.2 Réflexion Java
**Problème** : Nécessité d'utiliser la réflexion pour manipuler les entités.

**Solution** : 
- Utilisation du ClassLoader de l'EJB pour charger les classes dynamiquement
- Méthodes génériques pour les opérations CRUD

### 10.3 Dépannage

#### EJB non trouvé
- Vérifier que le module EJB est déployé avant le module WEB
- Vérifier les noms JNDI dans les servlets
- Consulter les logs WildFly

#### Erreur de connexion base
- Vérifier la configuration dans `persistence.xml`
- Vérifier que MySQL/MariaDB est démarré
- Vérifier les credentials

---

## 11. Améliorations Possibles

### 11.1 Fonctionnalités
- 🔲 Système d'authentification
- 🔲 Génération de rapports PDF
- 🔲 Export des données en CSV/Excel
- 🔲 Recherche et filtrage avancé
- 🔲 Statistiques et graphiques
- 🔲 Email notifications

### 11.2 Technique
- 🔲 Migration vers Jakarta Faces (JSF)
- 🔲 Tests unitaires (JUnit)
- 🔲 Logs structurés (Log4j)
- 🔲 Configuration Docker
- 🔲 API REST pour accès mobile
- 🔲 Cache Redis

### 11.3 Interface
- 🔲 Thèmes clair/sombre
- 🔲 Internationalisation (i18n)
- 🔲 Notifications toast
- 🔲 Upload de fichiers (photos étudiants)
- 🔲 Preview en temps réel

---

## 12. Conclusion

### 12.1 Objectifs Atteints
Ce projet a permis de :
- ✅ Maîtriser l'architecture Jakarta EE 10
- ✅ Implémenter une application complète multi-tiers
- ✅ Utiliser EJB3 pour la logique métier
- ✅ Créer une interface web moderne et fonctionnelle
- ✅ Appliquer les concepts de JPA/Hibernate

### 12.2 Compétences Acquises
- Architecture des applications Java EE
- Développement avec Servlets et JSP
- Persistance avec JPA/Hibernate
- Gestion de projet avec Maven
- Déploiement sur WildFly
- Communication EJB via JNDI
- Utilisation de la réflexion Java

### 12.3 Perspective
Cette application démontre une compréhension solide des technologies Jakarta EE et peut servir de base pour des projets plus complexes nécessitant une architecture enterprise robuste.

---

##  Réalisé par

**ESSALHI SALMA**  
Filière : *Logiciels et Systèmes Intelligents*  
Sous la supervision de **Pr. ELAACHAK LOTFI**
**Date du rapport** : 28/10/2025  
**Version du projet** : 1.0-SNAPSHOT  
**Statut** : Fonctionnel et déployable

