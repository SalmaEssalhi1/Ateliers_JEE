# Rapport Technique - Application de Gestion des Employés

## 📋 Vue d'Ensemble

Application full-stack de gestion des employés avec authentification JWT, développée avec **Spring Boot** (backend) et **Angular 19** (frontend).

**Auteur :** ESSALHI SALMA  
**Date :** Novembre 2025

---

## 🏗️ Architecture Générale

```
┌─────────────────┐         HTTP/REST         ┌─────────────────┐
│                 │◄──────────────────────────►│                 │
│  Angular 19     │    JWT Authentication      │  Spring Boot 4   │
│  (Frontend)     │                            │  (Backend)       │
│  Port: 4200     │                            │  Port: 8080      │
└─────────────────┘                            └─────────────────┘
                                                         │
                                                         ▼
                                                ┌─────────────────┐
                                                │   MySQL         │
                                                │   Database      │
                                                │   Port: 3306    │
                                                └─────────────────┘
```

---

## 🔧 Backend - Spring Boot

### Technologies Utilisées

- **Spring Boot** : 4.0.0
- **Java** : 25
- **Spring Security** : Authentification et autorisation
- **Spring Data JPA** : Accès aux données
- **JWT (jjwt)** : 0.11.5 - Gestion des tokens
- **MySQL** : Base de données
- **Lombok** : Réduction du code boilerplate
- **Validation** : Validation des entités

### Structure du Projet

```
Atelier7/
├── src/main/java/ma/fstt/atelier7/
│   ├── Atelier7Application.java          # Point d'entrée
│   ├── entities/
│   │   └── Employee.java                 # Entité JPA
│   ├── Repository/
│   │   └── EmployeeRepository.java      # Interface JPA Repository
│   ├── Service/
│   │   ├── EmployeeService.java          # Interface service
│   │   ├── EmployeeServiceImpl.java     # Implémentation service
│   │   └── JwtService.java              # Service JWT
│   ├── RESTController/
│   │   ├── AuthController.java          # Endpoints authentification
│   │   └── EmployeeController.java      # Endpoints CRUD employés
│   ├── Security/
│   │   └── SecurityConfig.java          # Configuration sécurité + CORS
│   └── filter/
│       └── JwtAuthenticationFilter.java # Filtre JWT
└── src/main/resources/
    └── application.properties           # Configuration
```

### Configuration

#### Base de Données (application.properties)

```properties
server.port=8080
spring.datasource.url=jdbc:mysql://localhost:3306/employees_db
spring.datasource.username=root
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
```

#### Sécurité

- **Authentification** : JWT (JSON Web Token)
- **Utilisateurs en mémoire** :
  - `admin` / `password123` (ROLE_ADMIN)
  - `user` / `user123` (ROLE_USER)
- **CORS** : Configuré pour `http://localhost:4200`
- **CSRF** : Désactivé (API REST)

### API REST

#### Authentification

**POST** `/api/auth/login`
```json
Request:
{
  "username": "admin",
  "password": "password123"
}

Response:
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "username": "admin"
}
```

#### Gestion des Employés

| Méthode | Endpoint | Description | Authentification |
|---------|----------|------------|------------------|
| GET | `/api/employees` | Liste tous les employés | ✅ Requis |
| GET | `/api/employees/{id}` | Détails d'un employé | ✅ Requis |
| POST | `/api/employees` | Créer un employé | ✅ Requis |
| PUT | `/api/employees/{id}` | Modifier un employé | ✅ Requis |
| DELETE | `/api/employees/{id}` | Supprimer un employé | ✅ Requis |

### Modèle de Données

**Employee**
```java
- id: Long (auto-généré)
- firstName: String (obligatoire)
- lastName: String (obligatoire)
- email: String (obligatoire, unique)
- salary: Double (obligatoire, >= 0)
```

### Filtre JWT

Le `JwtAuthenticationFilter` :
- Intercepte les requêtes HTTP
- Extrait le token JWT du header `Authorization`
- Valide le token et authentifie l'utilisateur
- Ignore les requêtes OPTIONS (CORS preflight)

### Configuration CORS

```java
- Origines autorisées : http://localhost:4200
- Méthodes : GET, POST, PUT, DELETE, OPTIONS
- Headers : Tous (*)
- Credentials : Autorisés
```

---

## 🎨 Frontend - Angular 19

### Technologies Utilisées

- **Angular** : 19.2.0
- **TypeScript** : 5.7.2
- **RxJS** : 7.8.0
- **Angular Router** : Navigation et routing
- **Reactive Forms** : Formulaires réactifs
- **Standalone Components** : Architecture moderne

### Structure du Projet

```
frontemploye/
├── src/app/
│   ├── app.component.*              # Composant racine
│   ├── app.config.ts                 # Configuration app
│   ├── app.routes.ts                 # Routes de l'application
│   ├── auth/
│   │   └── login/                    # Page de connexion
│   ├── employees/
│   │   ├── employee-shell/           # Container principal
│   │   ├── employee-list/            # Liste des employés
│   │   ├── employee-form/            # Formulaire (création/édition)
│   │   ├── employee-detail/          # Détails d'un employé
│   │   └── employee-delete/          # Confirmation suppression
│   └── core/
│       ├── config/
│       │   └── api.config.ts         # URL API backend
│       ├── guards/
│       │   └── auth.guard.ts         # Protection des routes
│       ├── interceptors/
│       │   └── auth.interceptor.ts   # Injection token JWT
│       ├── models/
│       │   ├── auth.model.ts         # Modèles authentification
│       │   └── employee.model.ts     # Modèle employé
│       └── services/
│           ├── auth.service.ts       # Service authentification
│           └── employee.service.ts   # Service CRUD employés
```

### Configuration API

**api.config.ts**
```typescript
export const API_BASE_URL = 'http://localhost:8080/api';
```

### Routing

```typescript
Routes:
- /login                    → Page de connexion
- /employees                → Liste des employés (protégé)
- /employees/new           → Créer un employé (protégé)
- /employees/:id           → Détails employé (protégé)
- /employees/:id/edit      → Modifier employé (protégé)
- /employees/:id/delete    → Supprimer employé (protégé)
- /                         → Redirection vers /employees
```

### Authentification

#### AuthService
- `login(payload)` : Connexion et stockage du token
- `logout()` : Déconnexion et suppression du token
- `isAuthenticated()` : Vérification de l'authentification
- `getToken()` : Récupération du token JWT
- `getUsername()` : Récupération du nom d'utilisateur

#### AuthGuard
- Protège toutes les routes `/employees/*`
- Redirige vers `/login` si non authentifié
- Préserve l'URL de destination (`returnUrl`)

#### AuthInterceptor
- Ajoute automatiquement le header `Authorization: Bearer {token}`
- Intercepte toutes les requêtes HTTP vers l'API

### Services

#### EmployeeService
```typescript
- getAllEmployees(): Observable<Employee[]>
- getEmployee(id: number): Observable<Employee>
- createEmployee(employee: Employee): Observable<Employee>
- updateEmployee(id: number, employee: Employee): Observable<Employee>
- deleteEmployee(id: number): Observable<void>
```

### Composants

#### LoginComponent
- Formulaire réactif (username/password)
- Validation des champs
- Gestion des erreurs
- Redirection après connexion

#### EmployeeListComponent
- Tableau responsive avec tous les employés
- Actions : Détails, Modifier, Supprimer
- Bouton "Ajouter un employé"
- Actualisation des données

#### EmployeeFormComponent
- Mode création/édition
- Validation des champs (required, email, min)
- Messages d'erreur contextuels
- Redirection après sauvegarde

#### EmployeeDetailComponent
- Affichage des détails d'un employé
- Actions : Modifier, Supprimer

#### EmployeeDeleteComponent
- Confirmation de suppression
- Message d'avertissement

### Modèles TypeScript

**Employee**
```typescript
interface Employee {
  id?: number;
  firstName: string;
  lastName: string;
  email: string;
  salary: number;
}
```

**Auth Models**
```typescript
interface LoginPayload {
  username: string;
  password: string;
}

interface LoginResponse {
  token: string;
  username: string;
}
```

---

## 🔐 Sécurité

### Flux d'Authentification

1. **Connexion** :
   - L'utilisateur saisit username/password
   - Frontend envoie POST `/api/auth/login`
   - Backend valide les credentials
   - Backend génère un JWT et le retourne
   - Frontend stocke le token dans `localStorage`

2. **Requêtes Authentifiées** :
   - Frontend ajoute `Authorization: Bearer {token}` via interceptor
   - Backend valide le token via `JwtAuthenticationFilter`
   - Si valide, la requête est autorisée

3. **Déconnexion** :
   - Frontend supprime le token du `localStorage`
   - Redirection vers `/login`

### Stockage du Token

- **Localisation** : `localStorage` (navigateur)
- **Clés** :
  - `atelier7_token` : Token JWT
  - `atelier7_username` : Nom d'utilisateur

---

## 🚀 Démarrage du Projet

### Option 1 : Docker (Recommandé) 🐳

**Prérequis** :
- Docker 20.10+
- Docker Compose 2.0+

**Démarrage** :
```bash
# À la racine du projet
docker-compose up -d
```

Cette commande lance automatiquement :
- MySQL (port 3306)
- Backend Spring Boot (port 8080)
- Frontend Angular/Nginx (port 4200)

**Accès** :
- Frontend : http://localhost:4200
- Backend API : http://localhost:8080/api
- Connexion : `admin` / `password123`

Voir [DOCKER.md](./DOCKER.md) pour la documentation complète Docker.

### Option 2 : Développement Local

**Prérequis** :
- **Java** 25
- **Node.js** (version récente)
- **MySQL** 8.0+
- **Maven** (inclus avec Spring Boot)
- **Angular CLI** : `npm install -g @angular/cli`

#### Backend (Spring Boot)

1. **Créer la base de données** :
```sql
CREATE DATABASE employees_db;
```

2. **Configurer** `application.properties` :
   - Vérifier l'URL MySQL
   - Vérifier username/password MySQL

3. **Lancer l'application** :
```bash
cd Atelier7
mvn spring-boot:run
```

L'application démarre sur `http://localhost:8080`

#### Frontend (Angular)

1. **Installer les dépendances** :
```bash
cd frontemploye
npm install
```

2. **Lancer le serveur de développement** :
```bash
npm start
# ou
ng serve
```

L'application démarre sur `http://localhost:4200`

### Accès à l'Application

1. Ouvrir `http://localhost:4200`
2. Redirection automatique vers `/login`
3. Se connecter avec :
   - **Username** : `admin`
   - **Password** : `password123`

---

## 📊 Fonctionnalités Implémentées

### ✅ Backend

- [x] Authentification JWT
- [x] Configuration CORS
- [x] CRUD complet pour les employés
- [x] Validation des données (Bean Validation)
- [x] Gestion des erreurs HTTP
- [x] Filtre d'authentification JWT
- [x] Sécurité Spring Security

### ✅ Frontend

- [x] Page de connexion
- [x] Liste des employés (tableau)
- [x] Création d'employé (formulaire)
- [x] Modification d'employé (formulaire)
- [x] Détails d'un employé
- [x] Suppression d'employé (confirmation)
- [x] Guard d'authentification
- [x] Intercepteur HTTP (JWT)
- [x] Gestion des erreurs
- [x] Navigation et routing
- [x] Interface utilisateur moderne

---

## 🔧 Configuration CORS - Résolution des Problèmes

### Problème Initial

Les requêtes depuis Angular (port 4200) vers Spring Boot (port 8080) étaient bloquées par la politique CORS.

### Solution Implémentée

1. **Configuration CORS dans SecurityConfig** :
   - Bean `CorsConfigurationSource`
   - Origine autorisée : `http://localhost:4200`
   - Méthodes : GET, POST, PUT, DELETE, OPTIONS
   - Headers : Tous autorisés
   - Credentials : Autorisés

2. **Autorisation des requêtes OPTIONS** :
   - Ajout de `.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()`
   - Les requêtes preflight (OPTIONS) sont autorisées sans authentification

3. **Filtre JWT** :
   - Ignore les requêtes OPTIONS
   - Ne traite que les requêtes avec token JWT

---

## 📝 Notes Techniques

### Points d'Attention

1. **CORS** : La configuration CORS doit être cohérente entre frontend et backend
2. **Token JWT** : Stocké dans `localStorage` (considérer `httpOnly` cookies en production)
3. **Validation** : Les validations sont effectuées côté backend et frontend
4. **Erreurs** : Gestion d'erreurs améliorée avec messages clairs



**Document généré le :** Novembre 2025  
**Version :** 1.0

