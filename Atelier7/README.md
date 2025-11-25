# Application de Gestion des Employés

Application full-stack de gestion des employés avec authentification JWT.

## 🚀 Démarrage Rapide

### Option 1 : Docker (Recommandé) 🐳

```bash
# Lancer toute l'application avec Docker
docker-compose up -d
```

L'application sera accessible sur :
- **Frontend** : http://localhost:4200
- **Backend** : http://localhost:8080/api

**Connexion** :
- **Username** : `admin`
- **Password** : `password123`

Voir [DOCKER.md](./DOCKER.md) pour plus de détails.

### Option 2 : Développement Local

#### Backend (Spring Boot)
```bash
cd Atelier7
mvn spring-boot:run
```
→ Démarre sur `http://localhost:8080`

#### Frontend (Angular)
```bash
cd frontemploye
npm install
npm start
```
→ Démarre sur `http://localhost:4200`

#### Base de données MySQL
Assurez-vous que MySQL est démarré et que la base `employees_db` existe.

## 📚 Documentation

- [RAPPORT_PROJET.md](./RAPPORT_PROJET.md) - Documentation technique complète
- [DOCKER.md](./DOCKER.md) - Guide Docker et Docker Compose

## 🛠️ Technologies

- **Backend** : Spring Boot 4.0, Java 25, MySQL, JWT
- **Frontend** : Angular 19, TypeScript, RxJS
- **DevOps** : Docker, Docker Compose

## 📋 Fonctionnalités

- ✅ Authentification JWT
- ✅ CRUD complet des employés
- ✅ Interface utilisateur moderne
- ✅ Protection des routes
- ✅ Gestion des erreurs
- ✅ Dockerisation complète

