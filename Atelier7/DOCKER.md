# Guide Docker - Application de Gestion des Employés

## 📦 Vue d'Ensemble

Ce projet utilise Docker et Docker Compose pour orchestrer trois services :
- **MySQL** : Base de données
- **Spring Boot** : Backend API
- **Angular** : Frontend (servi par Nginx)

---

## 🚀 Démarrage Rapide

### Prérequis

- **Docker** : Version 20.10+
- **Docker Compose** : Version 2.0+

Vérifier l'installation :
```bash
docker --version
docker-compose --version
```

### Lancer l'Application Complète

```bash
# À la racine du projet
docker-compose up -d
```

Cette commande va :
1. Construire les images Docker pour le backend et le frontend
2. Démarrer MySQL
3. Démarrer le backend Spring Boot
4. Démarrer le frontend Angular (Nginx)

### Accéder à l'Application

- **Frontend** : http://localhost:4200
- **Backend API** : http://localhost:8080/api
- **MySQL** : localhost:3306

### Connexion

- **Username** : `admin`
- **Password** : `password123`

---

## 🛠️ Commandes Utiles

### Démarrer les services

```bash
docker-compose up -d
```

### Voir les logs

```bash
# Tous les services
docker-compose logs -f

# Un service spécifique
docker-compose logs -f backend
docker-compose logs -f frontend
docker-compose logs -f mysql
```

### Arrêter les services

```bash
docker-compose down
```

### Arrêter et supprimer les volumes (⚠️ supprime les données)

```bash
docker-compose down -v
```

### Reconstruire les images

```bash
# Reconstruire toutes les images
docker-compose build --no-cache

# Reconstruire un service spécifique
docker-compose build --no-cache backend
docker-compose build --no-cache frontend
```

### Redémarrer un service

```bash
docker-compose restart backend
docker-compose restart frontend
docker-compose restart mysql
```

### Voir l'état des services

```bash
docker-compose ps
```

### Accéder à un conteneur

```bash
# Backend
docker-compose exec backend sh

# Frontend
docker-compose exec frontend sh

# MySQL
docker-compose exec mysql mysql -u appuser -papppassword employees_db
```

---

## 📁 Structure des Fichiers Docker

```
.
├── docker-compose.yml          # Configuration principale
├── docker-compose.dev.yml      # Configuration développement
├── docker-compose.prod.yml     # Configuration production
├── .dockerignore              # Fichiers ignorés par Docker
├── Atelier7/
│   ├── Dockerfile             # Image backend (production)
│   ├── Dockerfile.dev         # Image backend (développement)
│   └── .dockerignore
├── frontemploye/
│   ├── Dockerfile             # Image frontend
│   ├── nginx.conf            # Configuration Nginx
│   └── .dockerignore
└── mysql-init/               # Scripts SQL d'initialisation (optionnel)
```

---

## 🔧 Configuration des Services

### MySQL

- **Image** : `mysql:8.0`
- **Port** : `3306`
- **Base de données** : `employees_db`
- **Utilisateur** : `appuser`
- **Mot de passe** : `apppassword`
- **Volume** : `mysql_data` (persistance des données)

### Backend (Spring Boot)

- **Port** : `8080`
- **Build** : Multi-stage avec Maven
- **Variables d'environnement** :
  - `SPRING_DATASOURCE_URL` : URL de connexion MySQL
  - `SPRING_DATASOURCE_USERNAME` : `appuser`
  - `SPRING_DATASOURCE_PASSWORD` : `apppassword`
  - `SPRING_JPA_HIBERNATE_DDL_AUTO` : `update`

### Frontend (Angular + Nginx)

- **Port** : `4200` (mappé vers `80` dans le conteneur)
- **Build** : Multi-stage avec Node.js et Nginx
- **Serveur** : Nginx Alpine

---

## 🎯 Scénarios d'Utilisation

### Développement Local

Pour le développement avec hot-reload :

```bash
# Option 1 : Utiliser docker-compose.dev.yml
docker-compose -f docker-compose.dev.yml up

# Option 2 : Lancer seulement MySQL avec Docker
docker-compose up mysql -d
# Puis lancer backend et frontend localement
```

### Production

```bash
# Utiliser la configuration de production
docker-compose -f docker-compose.prod.yml up -d
```

### Rebuild après modifications

```bash
# Reconstruire et redémarrer
docker-compose up -d --build
```

---

## 🐛 Dépannage

### Les services ne démarrent pas

1. **Vérifier les ports disponibles** :
```bash
# Vérifier si les ports sont utilisés
netstat -an | grep 8080
netstat -an | grep 4200
netstat -an | grep 3306
```

2. **Vérifier les logs** :
```bash
docker-compose logs
```

3. **Vérifier l'état des conteneurs** :
```bash
docker-compose ps
```

### Le backend ne peut pas se connecter à MySQL

1. **Vérifier que MySQL est démarré** :
```bash
docker-compose ps mysql
```

2. **Vérifier les logs MySQL** :
```bash
docker-compose logs mysql
```

3. **Vérifier la connexion manuellement** :
```bash
docker-compose exec mysql mysql -u appuser -papppassword employees_db
```

### Le frontend ne peut pas accéder au backend

1. **Vérifier que le backend est démarré** :
```bash
curl http://localhost:8080/api/auth/login
```

2. **Vérifier la configuration CORS** dans `SecurityConfig.java`

3. **Vérifier les logs du backend** :
```bash
docker-compose logs backend
```

### Rebuild complet

Si vous rencontrez des problèmes persistants :

```bash
# Arrêter tout
docker-compose down -v

# Nettoyer les images
docker system prune -a

# Reconstruire
docker-compose build --no-cache
docker-compose up -d
```

---

## 📊 Monitoring

### Voir l'utilisation des ressources

```bash
docker stats
```

### Voir les volumes

```bash
docker volume ls
```

### Inspecter un service

```bash
docker-compose inspect backend
```

---

## 🔒 Sécurité en Production

⚠️ **Important** : Avant de déployer en production :

1. **Changer les mots de passe par défaut** dans `docker-compose.prod.yml`
2. **Utiliser des variables d'environnement** pour les secrets
3. **Activer SSL/TLS** pour MySQL
4. **Configurer un reverse proxy** (Nginx/Traefik) devant les services
5. **Limiter les ports exposés** (ne pas exposer MySQL publiquement)
6. **Utiliser des secrets Docker** pour les mots de passe

Exemple avec variables d'environnement :

```bash
# Créer un fichier .env
MYSQL_ROOT_PASSWORD=your_secure_password
MYSQL_USER=appuser
MYSQL_PASSWORD=your_secure_password

# Lancer avec
docker-compose --env-file .env up -d
```

---

## 📝 Notes

- Les données MySQL sont persistées dans un volume Docker
- Les images sont construites avec multi-stage build pour optimiser la taille
- Le frontend utilise Nginx pour servir les fichiers statiques
- Le backend utilise un JAR optimisé pour la production

---

## 🔗 Ressources

- [Documentation Docker](https://docs.docker.com/)
- [Documentation Docker Compose](https://docs.docker.com/compose/)
- [Spring Boot Docker](https://spring.io/guides/gs/spring-boot-docker/)
- [Angular Docker](https://angular.io/guide/deployment)

