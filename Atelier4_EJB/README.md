# Application de Gestion des Étudiants - EJB Project

## Description

Ce projet est une application Jakarta EE pour la gestion des étudiants, des modules et du suivi pédagogique (notes et évaluations). Il utilise les technologies Enterprise Java Beans (EJB) pour la couche métier et JPA/Hibernate pour la persistance des données.

## Architecture

### Modèle de données

Le système gère trois entités principales :

- **Etudiant** : Informations sur les étudiants (nom, prénom, CNE, adresse, niveau)
- **Module** : Modules pédagogiques (code, nom, description)
- **Suivie** : Suivi pédagogique avec les notes et dates d'évaluation (relation Many-to-One avec Etudiant et Module)

### Couches de l'application

- **Entités** (`entities/`) : Modèle de données avec JPA
- **Beans EJB** (`beans/`) : Logique métier avec Stateless Session Beans
- **Interfaces** (`interfaces/`) : Interfaces Remote pour l'accès distant
- **Web** (`webapp/`) : Interface web avec PrimeFaces

## Technologies utilisées

- **Java** : Version 17
- **Jakarta EE** : Version 10
- **EJB** : Jakarta Enterprise Beans 4.0.1
- **JPA** : Jakarta Persistence 3.1.0 avec Hibernate 6.4.4.Final
- **Base de données** : MySQL 8.3.0
- **Build Tool** : Maven
- **Serveur d'application** : WildFly
- **Framework UI** : PrimeFaces 13.0.5
- **Lombok** : 1.18.32

## Prérequis

- Java Development Kit (JDK) 17 ou supérieur
- Apache Maven 3.6+
- Serveur d'application WildFly (dernière version)
- MySQL Server 8.0+

## Installation

### 1. Cloner le projet

```bash
git clone <repository-url>
cd EJB
```

### 2. Configurer la base de données MySQL

Créez une base de données MySQL :

```sql
CREATE DATABASE gestion_etudiants;
```

### 3. Configurer WildFly

Configurez la datasource dans WildFly pour pointer vers MySQL :

1. Ajoutez le pilote MySQL dans `$WILDFLY_HOME/modules/com/mysql/main/`
2. Configurez la datasource `MySQLDS` via la console d'administration WildFly ou via la CLI :

```bash
/subsystem=datasources/data-source=MySQLDS:add(jndi-name=java:jboss/datasources/MySQLDS, driver-name=mysql, connection-url=jdbc:mysql://localhost:3306/gestion_etudiants, user-name=root, password=your_password)
```

### 4. Configuration de la persistance

Le fichier `persistence.xml` est configuré pour :
- Auto-créer/mettre à jour le schéma de base de données
- Afficher les requêtes SQL
- Importer des données initiales

## Construction du projet

### Compiler le projet

```bash
mvn clean install
```

### Déployer sur WildFly

```bash
mvn wildfly:deploy
```

### Démarrer WildFly

```bash
$WILDFLY_HOME/bin/standalone.sh  # Linux/Mac
%WILDFLY_HOME%\bin\standalone.bat  # Windows
```

### Accéder à l'application

- **URL** : `http://localhost:8080/EJB`
- **Console WildFly** : `http://localhost:9990`

## Structure du projet

```
EJB/
├── src/
│   └── main/
│       ├── java/
│       │   └── ma/fstt/ejb/
│       │       ├── beans/           # EJB Stateless Session Beans
│       │       │   └── GestionEtudiantBean.java
│       │       ├── entities/        # Entités JPA
│       │       │   ├── Etudiant.java
│       │       │   ├── Module.java
│       │       │   └── Suivie.java
│       │       └── interfaces/      # Interfaces Remote
│       │           └── GestionEtudiantRemote.java
│       ├── resources/
│       │   └── META-INF/
│       │       ├── beans.xml
│       │       └── persistence.xml  # Configuration JPA
│       └── webapp/                  # Interface web
│           ├── index.jsp
│           └── WEB-INF/
│               └── web.xml
├── pom.xml                          # Configuration Maven
└── README.md
```

## Fonctionnalités

L'application fournit les opérations CRUD (Create, Read, Update, Delete) pour chaque entité :

### Gestion des Étudiants
- Ajouter un étudiant
- Modifier un étudiant
- Supprimer un étudiant
- Rechercher un étudiant par ID
- Lister tous les étudiants

### Gestion des Modules
- Ajouter un module
- Modifier un module
- Supprimer un module
- Rechercher un module par ID
- Lister tous les modules

### Gestion du Suivi Pédagogique
- Enregistrer une note d'évaluation
- Modifier une note
- Supprimer une note
- Rechercher une note par ID
- Lister toutes les notes

## API EJB

L'interface `GestionEtudiantRemote` expose les méthodes suivantes :

```java
// Étudiants
void ajouterEtudiant(Etudiant etudiant);
void modifierEtudiant(Etudiant etudiant);
void supprimerEtudiant(Long id);
Etudiant trouverEtudiant(Long id);
List<Etudiant> listEtudiants();

// Modules
void ajouterModule(Module module);
void modifierModule(Module module);
void supprimerModule(Long id);
Module trouverModule(Long id);
List<Module> listModules();

// Suivie
void ajouterSuivie(Suivie suivie);
void modifierSuivie(Suivie suivie);
void supprimerSuivie(Long id);
Suivie trouverSuivie(Long id);
List<Suivie> listSuivies();
```

## Variables d'environnement

Pour le déploiement WildFly via Maven, configurez :

```bash
export WILDFLY_USER=admin
export WILDFLY_PASS=your_password
```

## Développement

### Utiliser Lombok

Le projet utilise Lombok pour réduire le code boilerplate. Assurez-vous que votre IDE a le plugin Lombok installé :
- IntelliJ IDEA : Installer le plugin Lombok
- Eclipse : Ajouter lombok.jar aux classpath

### Formatage SQL

Les requêtes SQL sont automatiquement formatées dans les logs grâce à la configuration :
```xml
<property name="hibernate.format_sql" value="true"/>
```

## Tests

Les tests unitaires peuvent être exécutés avec :

```bash
mvn test
```

## Troubleshooting

### Erreur de connexion à la base de données

Vérifiez que :
- MySQL est démarré
- La datasource est correctement configurée dans WildFly
- Les identifiants sont corrects

### EJB non trouvé

Assurez-vous que :
- WildFly est démarré
- L'application est déployée
- Les annotations `@Stateless` et `@Remote` sont présentes

## Contribution

Les contributions sont les bienvenues ! Merci de :
1. Fork le projet
2. Créer une branche pour votre fonctionnalité
3. Commiter vos changements
4. Pousser vers la branche
5. Ouvrir une Pull Request

## Licence

Ce projet est destiné à des fins éducatives.

## Auteur

Développé dans le cadre d'un projet académique à l'Université FSTT.

