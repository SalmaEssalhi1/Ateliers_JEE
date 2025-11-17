# RAPPORT DE PROJET
## Système de Gestion des Absences Étudiantes

---

**Projet** : Atelier 6 - Application Web de Gestion des Absences  
**Technologie** : Spring Boot - Java  
**Date** : Novembre 2025  
**Auteur** : FSTT

---

## TABLE DES MATIÈRES

1. [Introduction](#1-introduction)
2. [Contexte et Objectifs](#2-contexte-et-objectifs)
3. [Analyse et Conception](#3-analyse-et-conception)
4. [Technologies Utilisées](#4-technologies-utilisées)
5. [Architecture de l'Application](#5-architecture-de-lapplication)
6. [Implémentation](#6-implémentation)
7. [Fonctionnalités](#7-fonctionnalités)
8. [Tests et Validation](#8-tests-et-validation)
9. [Difficultés Rencontrées](#9-difficultés-rencontrées)
10. [Conclusion et Perspectives](#10-conclusion-et-perspectives)

---

## 1. INTRODUCTION

Ce rapport présente le développement d'une application web de gestion des absences étudiantes réalisée dans le cadre de l'Atelier 6. L'application permet aux administrateurs de gérer efficacement les informations des étudiants et de suivre leurs absences de manière structurée et intuitive.

L'application a été développée en utilisant le framework Spring Boot, offrant une architecture moderne et scalable pour la gestion des données étudiantes et de leurs absences.

---

## 2. CONTEXTE ET OBJECTIFS

### 2.1 Contexte

Dans un environnement éducatif, la gestion des absences des étudiants est une tâche importante qui nécessite un suivi précis et organisé. Cette application répond au besoin de digitaliser et automatiser ce processus.

### 2.2 Objectifs

Les objectifs principaux de ce projet sont :

- **Objectif 1** : Développer une application web complète pour la gestion des étudiants
- **Objectif 2** : Implémenter un système de gestion des absences lié aux étudiants
- **Objectif 3** : Créer une interface utilisateur moderne et intuitive
- **Objectif 4** : Assurer la persistance des données dans une base de données relationnelle
- **Objectif 5** : Mettre en place une architecture MVC propre et maintenable

### 2.3 Périmètre du Projet

Le projet couvre :
- La gestion complète des étudiants (CRUD)
- La gestion complète des absences (CRUD)
- L'affichage des absences par étudiant
- Une interface web responsive

---

## 3. ANALYSE ET CONCEPTION

### 3.1 Modèle de Données

#### 3.1.1 Entité Étudiant

L'entité `Etudiant` représente un étudiant dans le système avec les attributs suivants :

| Attribut | Type | Description | Contraintes |
|----------|------|-------------|-------------|
| id | Long | Identifiant unique | Auto-généré, Clé primaire |
| nom | String | Nom de l'étudiant | Obligatoire |
| prenom | String | Prénom de l'étudiant | Obligatoire |
| classe | String | Classe de l'étudiant | Obligatoire |
| absences | List<Absence> | Liste des absences | Relation OneToMany |

**Relation** : Un étudiant peut avoir plusieurs absences (OneToMany)

#### 3.1.2 Entité Absence

L'entité `Absence` représente une absence d'un étudiant :

| Attribut | Type | Description | Contraintes |
|----------|------|-------------|-------------|
| id | Long | Identifiant unique | Auto-généré, Clé primaire |
| dateAbs | LocalDate | Date de l'absence | Obligatoire |
| motif | String | Motif de l'absence | Optionnel |
| etudiant | Etudiant | Référence à l'étudiant | Relation ManyToOne, Obligatoire |

**Relation** : Une absence appartient à un seul étudiant (ManyToOne)

### 3.2 Diagramme de Classes

```
┌─────────────────────┐
│     Etudiant        │
├─────────────────────┤
│ - id: Long          │
│ - nom: String       │
│ - prenom: String    │
│ - classe: String    │
│ - absences: List    │
└──────────┬──────────┘
           │
           │ 1
           │
           │ *
           │
┌──────────▼──────────┐
│      Absence        │
├─────────────────────┤
│ - id: Long          │
│ - dateAbs: LocalDate│
│ - motif: String     │
│ - etudiant: Etudiant│
└─────────────────────┘
```

### 3.3 Architecture MVC

L'application suit le pattern Model-View-Controller :

- **Model** : Les entités JPA (`Etudiant`, `Absence`)
- **View** : Les templates Thymeleaf (HTML)
- **Controller** : Les contrôleurs Spring MVC (`EtudiantController`, `AbsenceController`)

---

## 4. TECHNOLOGIES UTILISÉES

### 4.1 Backend

#### 4.1.1 Spring Boot 3.5.7
Framework Java qui simplifie le développement d'applications en fournissant une configuration automatique et des dépendances pré-configurées.

**Avantages** :
- Configuration automatique
- Démarrage rapide
- Intégration facile avec d'autres technologies

#### 4.1.2 Spring Data JPA
Abstraction pour l'accès aux données qui simplifie l'implémentation des repositories.

**Utilisation** : Création des interfaces `EtudiantRepository` et `AbsenceRepository` avec des méthodes personnalisées.

#### 4.1.3 Hibernate
ORM (Object-Relational Mapping) qui gère la persistance des objets Java dans la base de données.

**Configuration** : Mode `update` pour la création automatique des tables.

#### 4.1.4 MySQL
Système de gestion de base de données relationnelle utilisé pour stocker les données.

**Version** : MySQL 8.0+ (compatible avec MySQL 5.5.5 minimum)

### 4.2 Frontend

#### 4.2.1 Thymeleaf
Moteur de template côté serveur qui permet de créer des vues dynamiques.

**Avantages** :
- Intégration native avec Spring
- Syntaxe HTML naturelle
- Support des expressions Spring EL

#### 4.2.2 Bootstrap 5.3.2
Framework CSS pour créer des interfaces utilisateur responsive et modernes.

**Utilisation** : Composants de formulaire, tables, boutons, cartes.

#### 4.2.3 Bootstrap Icons
Bibliothèque d'icônes SVG utilisée pour améliorer l'interface utilisateur.

### 4.3 Outils de Développement

- **Maven** : Gestion des dépendances et build du projet
- **Lombok** : Réduction du code boilerplate
- **HikariCP** : Pool de connexions haute performance

---

## 5. ARCHITECTURE DE L'APPLICATION

### 5.1 Structure des Packages

```
ma.fstt.atelier6
├── controller/          # Couche de contrôle
│   ├── EtudiantController
│   ├── AbsenceController
│   └── HomeController
├── entities/            # Couche modèle (JPA)
│   ├── Etudiant
│   └── Absence
├── repository/          # Couche d'accès aux données
│   ├── EtudiantRepository
│   └── AbsenceRepository
└── service/            # Couche métier
    ├── EtudiantService
    └── EtudiantServiceImpl
```

### 5.2 Flux de Données

```
┌─────────────┐
│   Browser   │
└──────┬──────┘
       │ HTTP Request
       ▼
┌─────────────┐
│ Controller  │ ←─── Spring MVC
└──────┬──────┘
       │
       ▼
┌─────────────┐
│   Service   │ ←─── Logique métier
└──────┬──────┘
       │
       ▼
┌─────────────┐
│ Repository  │ ←─── Spring Data JPA
└──────┬──────┘
       │
       ▼
┌─────────────┐
│  Database   │ ←─── MySQL
└─────────────┘
```

### 5.3 Configuration

#### 5.3.1 Configuration de la Base de Données

Le fichier `application.properties` contient toutes les configurations nécessaires :

```properties
# Connexion à la base de données
spring.datasource.url=jdbc:mysql://localhost:3306/absencedb
spring.datasource.username=root
spring.datasource.password=

# Configuration JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

#### 5.3.2 Configuration Thymeleaf

```properties
spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.suffix=.html
spring.thymeleaf.cache=false
```

---

## 6. IMPLÉMENTATION

### 6.1 Entités JPA

#### 6.1.1 Classe Etudiant

```java
@Entity
public class Etudiant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nom;
    private String prenom;
    private String classe;
    
    @OneToMany(mappedBy = "etudiant", cascade = CascadeType.ALL)
    private List<Absence> absences = new ArrayList<>();
}
```

**Points clés** :
- Relation bidirectionnelle avec `Absence`
- Cascade pour la suppression en cascade
- Initialisation de la liste dans le constructeur

#### 6.1.2 Classe Absence

```java
@Entity
public class Absence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDate dateAbs;
    private String motif;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "etudiant_id")
    private Etudiant etudiant;
}
```

**Points clés** :
- FetchType.LAZY pour optimiser les performances
- Clé étrangère `etudiant_id` dans la table `absence`

### 6.2 Repositories

#### 6.2.1 EtudiantRepository

```java
@Repository
public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {
    @Query("SELECT DISTINCT e FROM Etudiant e LEFT JOIN FETCH e.absences")
    List<Etudiant> findAllWithAbsences();
}
```

**Méthode personnalisée** : `findAllWithAbsences()` utilise `LEFT JOIN FETCH` pour éviter les problèmes de lazy loading.

#### 6.2.2 AbsenceRepository

```java
@Repository
public interface AbsenceRepository extends JpaRepository<Absence, Long> {
    List<Absence> findByEtudiantId(Long etudiantId);
    
    @Query("SELECT a FROM Absence a LEFT JOIN FETCH a.etudiant WHERE a.id = :id")
    Optional<Absence> findByIdWithEtudiant(Long id);
}
```

**Méthodes** :
- `findByEtudiantId()` : Trouve toutes les absences d'un étudiant
- `findByIdWithEtudiant()` : Charge une absence avec son étudiant

### 6.3 Services

#### 6.3.1 EtudiantService

Le service implémente la logique métier pour la gestion des étudiants :

```java
@Service
@Transactional
public class EtudiantServiceImpl implements EtudiantService {
    @Autowired
    private EtudiantRepository etudiantRepository;
    
    @Override
    @Transactional(readOnly = true)
    public List<Etudiant> findAll() {
        return etudiantRepository.findAllWithAbsences();
    }
    
    // Autres méthodes : findById, save, deleteById
}
```

**Points importants** :
- Annotation `@Transactional` pour gérer les transactions
- Utilisation de `findAllWithAbsences()` pour éviter les problèmes de lazy loading

### 6.4 Contrôleurs

#### 6.4.1 EtudiantController

Gère toutes les requêtes liées aux étudiants :

- `GET /etudiants` : Liste des étudiants
- `GET /etudiants/add` : Formulaire d'ajout
- `GET /etudiants/edit/{id}` : Formulaire de modification
- `POST /etudiants/save` : Sauvegarde (ajout ou modification)
- `GET /etudiants/delete/{id}` : Suppression

#### 6.4.2 AbsenceController

Gère toutes les requêtes liées aux absences :

- `GET /absences/etudiant/{id}` : Liste des absences d'un étudiant
- `GET /absences/add?etudiantId={id}` : Formulaire d'ajout
- `GET /absences/edit/{id}` : Formulaire de modification
- `POST /absences/save` : Sauvegarde
- `GET /absences/delete/{id}` : Suppression

### 6.5 Templates Thymeleaf

#### 6.5.1 Liste des Étudiants (`etudiants/list.html`)

Affiche un tableau avec :
- Informations des étudiants
- Nombre d'absences par étudiant
- Boutons d'action (Modifier, Supprimer, Gérer absences)

#### 6.5.2 Formulaire Étudiant (`etudiants/form.html`)

Formulaire unique pour l'ajout et la modification :
- Détection automatique du mode (ajout/modification)
- Validation des champs obligatoires
- Design adaptatif selon le mode

#### 6.5.3 Liste des Absences (`absences/list-etudiant.html`)

Affiche les absences d'un étudiant spécifique avec :
- Informations de l'étudiant en en-tête
- Tableau des absences
- Total d'absences

#### 6.5.4 Formulaire Absence (`absences/form.html`)

Formulaire pour ajouter/modifier une absence :
- Sélection de l'étudiant (désactivé en mode modification)
- Champ date avec validation
- Champ motif (optionnel)

---

## 7. FONCTIONNALITÉS

### 7.1 Gestion des Étudiants

#### 7.1.1 Liste des Étudiants

**Fonctionnalité** : Affichage de tous les étudiants enregistrés dans le système.

**Caractéristiques** :
- Tableau responsive avec Bootstrap
- Affichage du nombre d'absences par étudiant
- Actions rapides (Modifier, Supprimer, Gérer absences)
- Message informatif si aucun étudiant n'est enregistré

**URL** : `http://localhost:8080/etudiants`

#### 7.1.2 Ajout d'un Étudiant

**Fonctionnalité** : Permet d'ajouter un nouvel étudiant au système.

**Champs requis** :
- Nom
- Prénom
- Classe

**Validation** : Tous les champs sont obligatoires.

**URL** : `http://localhost:8080/etudiants/add`

#### 7.1.3 Modification d'un Étudiant

**Fonctionnalité** : Permet de modifier les informations d'un étudiant existant.

**Caractéristiques** :
- Pré-remplissage automatique des champs
- Conservation de l'ID
- Interface visuelle différente (couleur jaune)

**URL** : `http://localhost:8080/etudiants/edit/{id}`

#### 7.1.4 Suppression d'un Étudiant

**Fonctionnalité** : Permet de supprimer un étudiant du système.

**Sécurité** : Confirmation avant suppression pour éviter les suppressions accidentelles.

**Cascade** : Les absences associées sont également supprimées (cascade).

### 7.2 Gestion des Absences

#### 7.2.1 Liste des Absences par Étudiant

**Fonctionnalité** : Affiche toutes les absences d'un étudiant spécifique.

**Caractéristiques** :
- En-tête avec informations de l'étudiant
- Tableau des absences (Date, Motif)
- Compteur total d'absences
- Actions pour chaque absence

**URL** : `http://localhost:8080/absences/etudiant/{id}`

#### 7.2.2 Ajout d'une Absence

**Fonctionnalité** : Permet d'enregistrer une nouvelle absence pour un étudiant.

**Caractéristiques** :
- Sélection de l'étudiant (pré-sélectionné si appelé depuis la liste)
- Champ date avec validation
- Champ motif optionnel
- Date par défaut : date du jour si non spécifiée

**URL** : `http://localhost:8080/absences/add?etudiantId={id}`

#### 7.2.3 Modification d'une Absence

**Fonctionnalité** : Permet de modifier la date et le motif d'une absence.

**Caractéristiques** :
- Étudiant verrouillé (ne peut pas être modifié)
- Pré-remplissage des champs existants
- Modification uniquement de la date et du motif

**URL** : `http://localhost:8080/absences/edit/{id}`

#### 7.2.4 Suppression d'une Absence

**Fonctionnalité** : Permet de supprimer une absence.

**Sécurité** : Confirmation avant suppression.

**Redirection** : Retour à la liste des absences de l'étudiant après suppression.

### 7.3 Interface Utilisateur

#### 7.3.1 Design Responsive

L'interface s'adapte à tous les types d'écrans :
- Desktop : Affichage complet avec toutes les colonnes
- Tablette : Adaptation des tableaux
- Mobile : Interface optimisée pour petits écrans

#### 7.3.2 Navigation

- Navigation intuitive entre les pages
- Boutons de retour clairs
- Liens contextuels (retour à la liste des absences après modification)

#### 7.3.3 Feedback Utilisateur

- Messages de confirmation avant suppression
- Indicateurs visuels (couleurs différentes pour ajout/modification)
- Badges pour le comptage d'absences
- Messages informatifs si aucune donnée

---

## 8. TESTS ET VALIDATION

### 8.1 Tests Fonctionnels

#### 8.1.1 Tests de Gestion des Étudiants

✅ **Test 1** : Ajout d'un étudiant
- **Résultat** : L'étudiant est correctement enregistré dans la base de données
- **Validation** : Affichage dans la liste des étudiants

✅ **Test 2** : Modification d'un étudiant
- **Résultat** : Les modifications sont sauvegardées correctement
- **Validation** : Les nouvelles valeurs apparaissent dans la liste

✅ **Test 3** : Suppression d'un étudiant
- **Résultat** : L'étudiant et ses absences sont supprimés
- **Validation** : Disparition de la liste

#### 8.1.2 Tests de Gestion des Absences

✅ **Test 1** : Ajout d'une absence
- **Résultat** : L'absence est liée à l'étudiant correct
- **Validation** : Apparition dans la liste des absences de l'étudiant

✅ **Test 2** : Modification d'une absence
- **Résultat** : La date et le motif sont modifiés
- **Validation** : Les nouvelles valeurs apparaissent dans la liste

✅ **Test 3** : Suppression d'une absence
- **Résultat** : L'absence est supprimée
- **Validation** : Disparition de la liste, compteur mis à jour

### 8.2 Tests de Performance

- **Temps de chargement** : Pages chargées rapidement (< 1 seconde)
- **Requêtes SQL** : Optimisées avec JOIN FETCH pour éviter le N+1 problem
- **Base de données** : Gestion efficace des connexions avec HikariCP

### 8.3 Tests d'Interface

- **Responsive** : Interface fonctionnelle sur différents appareils
- **Navigation** : Tous les liens fonctionnent correctement
- **Validation** : Les formulaires rejettent les données invalides

---

## 9. DIFFICULTÉS RENCONTRÉES

### 9.1 Problème de Lazy Loading

**Problème** : Erreur `LazyInitializationException` lors de l'accès aux absences dans les templates Thymeleaf.

**Cause** : La session Hibernate était fermée avant le rendu du template.

**Solution** :
1. Utilisation de `LEFT JOIN FETCH` dans les requêtes
2. Ajout de `@Transactional` sur les services
3. Création de méthodes spécifiques dans les repositories

**Code de la solution** :
```java
@Query("SELECT DISTINCT e FROM Etudiant e LEFT JOIN FETCH e.absences")
List<Etudiant> findAllWithAbsences();
```

### 9.2 Problème de Scan des Composants

**Problème** : Les contrôleurs et services n'étaient pas détectés par Spring Boot.

**Cause** : Les packages `controller` et `service` n'étaient pas des sous-packages de `ma.fstt.atelier6`.

**Solution** : Ajout de `@ComponentScan` dans la classe principale :
```java
@ComponentScan(basePackages = {"controller", "service", "ma.fstt.atelier6"})
```

### 9.3 Problème d'Échappement dans Thymeleaf

**Problème** : Erreur de parsing SPEL avec les apostrophes dans les expressions Thymeleaf.

**Cause** : Mauvaise échappement des apostrophes dans les chaînes de caractères.

**Solution** : Utilisation de la concaténation de chaînes :
```html
th:text="${etudiant?.id != null ? 'Modifier l' + 'étudiant' : 'Ajouter un étudiant'}"
```

### 9.4 Problème de Création des Tables

**Problème** : Les tables n'étaient pas créées automatiquement dans la base de données.

**Cause** : Spring Boot ne scannait pas les entités dans le package `entities`.

**Solution** : Ajout de `@EntityScan` dans la classe principale :
```java
@EntityScan("entities")
@EnableJpaRepositories("repository")
```

---

## 10. CONCLUSION ET PERSPECTIVES

### 10.1 Conclusion

Ce projet a permis de développer une application web complète de gestion des absences étudiantes en utilisant les technologies modernes du framework Spring Boot. L'application répond aux objectifs fixés :

✅ **Objectifs atteints** :
- Application web fonctionnelle
- Interface utilisateur moderne et intuitive
- Gestion complète des étudiants et absences
- Persistance des données en base de données
- Architecture MVC propre et maintenable

**Points forts** :
- Code structuré et organisé
- Interface utilisateur responsive
- Gestion efficace des relations JPA
- Optimisation des requêtes SQL

### 10.2 Apprentissages

Ce projet a permis d'acquérir des compétences dans :
- Le développement d'applications Spring Boot
- L'utilisation de Spring Data JPA et Hibernate
- La création d'interfaces avec Thymeleaf et Bootstrap
- La gestion des relations entre entités
- La résolution de problèmes de lazy loading
- L'architecture MVC

### 10.3 Perspectives d'Amélioration

#### 10.3.1 Court Terme

- [ ] Ajout de tests unitaires et d'intégration
- [ ] Validation avancée des formulaires
- [ ] Gestion des erreurs plus robuste
- [ ] Messages de confirmation après actions

#### 10.3.2 Moyen Terme

- [ ] Système d'authentification et d'autorisation
- [ ] Recherche et filtrage des étudiants
- [ ] Export des données (PDF, Excel)
- [ ] Statistiques et graphiques
- [ ] Pagination des listes

#### 10.3.3 Long Terme

- [ ] API REST pour intégration mobile
- [ ] Notifications par email
- [ ] Tableau de bord avec analytics
- [ ] Multi-utilisateurs avec rôles
- [ ] Historique des modifications

### 10.4 Recommandations

Pour la mise en production, il est recommandé de :

1. **Sécurité** :
   - Implémenter l'authentification (Spring Security)
   - Ajouter la validation CSRF
   - Chiffrer les données sensibles

2. **Performance** :
   - Mettre en place un cache
   - Optimiser les requêtes SQL
   - Utiliser la pagination pour les grandes listes

3. **Maintenance** :
   - Ajouter des logs structurés
   - Documenter le code
   - Mettre en place des tests automatisés

4. **Base de données** :
   - Migrer vers MySQL 8.0+ (actuellement compatible avec 5.5.5)
   - Mettre en place des sauvegardes régulières
   - Optimiser les index

---

## ANNEXES

### Annexe A : Structure de la Base de Données

#### Table `etudiant`

| Colonne | Type | Contraintes |
|---------|------|-------------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT |
| nom | VARCHAR(255) | NOT NULL |
| prenom | VARCHAR(255) | NOT NULL |
| classe | VARCHAR(255) | NOT NULL |

#### Table `absence`

| Colonne | Type | Contraintes |
|---------|------|-------------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT |
| date_abs | DATE | NOT NULL |
| motif | VARCHAR(255) | NULL |
| etudiant_id | BIGINT | FOREIGN KEY → etudiant(id) |

### Annexe B : URLs de l'Application

| URL | Description | Méthode |
|-----|-------------|---------|
| `/` | Redirection vers `/etudiants` | GET |
| `/etudiants` | Liste des étudiants | GET |
| `/etudiants/add` | Formulaire d'ajout | GET |
| `/etudiants/edit/{id}` | Formulaire de modification | GET |
| `/etudiants/save` | Sauvegarde (ajout/modification) | POST |
| `/etudiants/delete/{id}` | Suppression | GET |
| `/absences/etudiant/{id}` | Liste des absences d'un étudiant | GET |
| `/absences/add` | Formulaire d'ajout d'absence | GET |
| `/absences/edit/{id}` | Formulaire de modification | GET |
| `/absences/save` | Sauvegarde d'absence | POST |
| `/absences/delete/{id}` | Suppression d'absence | GET |

### Annexe C : Dépendances Maven

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-thymeleaf</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
    </dependency>
</dependencies>
```

---

**Fin du Rapport**

*Document généré le : Novembre 2025*  
*Version : 1.0*

