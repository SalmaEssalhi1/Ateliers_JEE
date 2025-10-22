# 📄 DESCRIPTIONS DES FICHIERS .XHTML - PROJET SALMASTORE

## 🎯 Vue d'ensemble

Le projet SalmaStore contient **11 fichiers .xhtml** organisés en différentes catégories :
- **Pages publiques** : Accueil, authentification, panier
- **Pages d'administration** : Gestion des produits et utilisateurs  
- **Pages utilitaires** : Debug, test, erreurs
- **Template** : Layout principal

---

## 📁 Structure des fichiers

```
src/main/webapp/
├── index.xhtml                    # Page d'accueil
├── login.xhtml                    # Connexion
├── register.xhtml                  # Inscription
├── login-error.xhtml              # Erreur de connexion
├── panier.xhtml                   # Panier d'achat
├── checkout.xhtml                 # Confirmation de commande
├── debug-panier.xhtml             # Debug du panier
├── test.xhtml                     # Page de test
├── admin/
│   ├── produits.xhtml             # Gestion des produits
│   └── users.xhtml                # Gestion des utilisateurs
└── WEB-INF/templates/
    └── layout.xhtml               # Template principal
```

---

## 📋 Descriptions détaillées

### 🏠 **1. index.xhtml** - Page d'accueil
**Rôle** : Page principale du site e-commerce  
**Template** : Utilise `layout.xhtml`  
**Bean principal** : `vitrineBean`

#### Fonctionnalités :
- **En-tête de bienvenue** avec logo SalmaStore et slogan
- **Alerte pour utilisateurs non connectés** avec boutons de connexion/inscription
- **Catalogue produits** en grille responsive avec cartes élégantes
- **Informations produit** : nom, description, prix, stock, image
- **Boutons d'ajout au panier** avec gestion des états (connecté/non connecté)
- **Gestion du stock** avec badges colorés (vert/orange/rouge)
- **État vide** : Message quand aucun produit n'est disponible

#### Éléments visuels :
- Cartes produits avec effet hover (élévation et ombre)
- Images produits avec placeholder si aucune image
- Design responsive avec grille CSS Grid
- Thème rose/magenta cohérent

---

### 🔐 **2. login.xhtml** - Page de connexion
**Rôle** : Authentification des utilisateurs  
**Template** : Page autonome (pas de layout)  
**Bean principal** : `authBean`

#### Fonctionnalités :
- **Formulaire de connexion** avec email et mot de passe
- **Validation côté client** avec messages d'erreur
- **Champ mot de passe** avec masquage/affichage (toggle)
- **Bouton de connexion** avec icône et style auth
- **Liens de navigation** : inscription et retour accueil
- **Messages d'erreur** avec PrimeFaces Growl

#### Design :
- Style `auth-container` avec arrière-plan dégradé
- Carte centrée avec animations
- Formulaire stylisé avec classes auth

---

### 📝 **3. register.xhtml** - Page d'inscription
**Rôle** : Création de nouveaux comptes utilisateur  
**Template** : Page autonome (pas de layout)  
**Bean principal** : `authBean`

#### Fonctionnalités :
- **Formulaire d'inscription complet** :
  - Nom complet
  - Email (avec validation d'unicité)
  - Mot de passe (minimum 6 caractères)
  - Confirmation du mot de passe
  - Sélection du rôle (ACHETEUR/VENDEUR)
- **Validation des données** côté client et serveur
- **Gestion des erreurs** avec messages explicites
- **Redirection automatique** vers login après inscription réussie

#### Caractéristiques :
- Carte plus large (550px) pour accommoder le formulaire
- Radio buttons pour le choix du rôle
- Validation en temps réel

---

### ❌ **4. login-error.xhtml** - Page d'erreur de connexion
**Rôle** : Gestion des erreurs d'authentification  
**Template** : Page autonome (pas de layout)  
**Bean principal** : Aucun (page statique)

#### Fonctionnalités :
- **Message d'erreur explicite** : "Email ou mot de passe incorrect"
- **Actions de récupération** :
  - Bouton "Réessayer" vers login.xhtml
  - Bouton "Créer un compte" vers register.xhtml
  - Bouton "Retour à l'accueil" vers index.xhtml
- **Design cohérent** avec le thème auth

#### Design :
- Icône d'erreur rouge (#e53e3e)
- Carte centrée avec style auth
- Boutons d'action empilés verticalement

---

### 🛒 **5. panier.xhtml** - Page du panier d'achat
**Rôle** : Gestion du panier et préparation de la commande  
**Template** : Utilise `layout.xhtml`  
**Bean principal** : `panierBean`

#### Fonctionnalités :
- **État vide du panier** : Message et bouton vers le catalogue
- **Tableau des articles** avec colonnes :
  - Produit (image, nom, référence)
  - Prix unitaire
  - Quantité (boutons +/- avec limites de stock)
  - Sous-total calculé
  - Actions (supprimer)
- **Gestion des quantités** :
  - Boutons +/- pour modifier les quantités
  - Vérification du stock disponible
  - Mise à jour en temps réel
- **Résumé de commande** :
  - Total calculé automatiquement
  - Actions : vider panier, continuer achats, valider commande
- **Script de rafraîchissement** automatique au chargement

#### Éléments techniques :
- RemoteCommand pour le rafraîchissement
- Mise à jour AJAX des composants
- Gestion des contraintes de stock

---

### ✅ **6. checkout.xhtml** - Page de confirmation
**Rôle** : Confirmation de commande après validation du panier  
**Template** : Utilise `layout.xhtml`  
**Bean principal** : `internauteBean`

#### Fonctionnalités :
- **Message de succès** avec icône verte de validation
- **Informations client** :
  - Nom de l'utilisateur connecté
  - Email de contact
- **Message de confirmation** : Email de confirmation à venir
- **Bouton de retour** vers l'accueil

#### Design :
- Style de confirmation avec couleurs vertes (#4CAF50)
- Layout centré avec informations client
- Design épuré pour la confirmation

---

### 🐛 **7. debug-panier.xhtml** - Page de débogage
**Rôle** : Outil de développement pour diagnostiquer le panier  
**Template** : Utilise `layout.xhtml`  
**Bean principal** : `panierBean`, `internauteBean`

#### Fonctionnalités :
- **Informations utilisateur** :
  - Statut de connexion
  - Nom, email, ID utilisateur
  - ID du panier
- **Informations panier** :
  - État d'initialisation
  - Nombre d'articles et total
  - Nombre de lignes
- **Tableau des lignes** détaillé :
  - ID ligne, ID produit, nom produit
  - Quantité et prix unitaire
- **Bouton de rafraîchissement** pour forcer la mise à jour

#### Usage :
- Outil de développement uniquement
- Affichage de toutes les données internes
- Diagnostic des problèmes de panier

---

### 🧪 **8. test.xhtml** - Page de test
**Rôle** : Interface de test pour les fonctionnalités d'authentification  
**Template** : Page autonome (pas de layout)  
**Bean principal** : `authBean`

#### Fonctionnalités :
- **Formulaire de test** avec champs :
  - Email
  - Mot de passe
  - Rôle
- **Bouton de test** pour l'inscription
- **Section debug** affichant les valeurs actuelles des champs
- **Messages d'erreur** PrimeFaces

#### Usage :
- Développement et tests
- Validation des beans d'authentification
- Interface simplifiée pour les tests

---

### 📦 **9. admin/produits.xhtml** - Gestion des produits
**Rôle** : Interface d'administration pour la gestion du catalogue  
**Template** : Utilise `layout.xhtml`  
**Bean principal** : `produitBean`

#### Contrôle d'accès :
- **Restriction** : ADMIN ou VENDEUR uniquement
- **Message d'accès refusé** pour les utilisateurs non autorisés

#### Fonctionnalités :
- **Bouton d'ajout** de nouveau produit
- **Tableau des produits** avec colonnes :
  - ID, Libellé, Description, Prix, Stock
  - Actions : Modifier, Supprimer
- **Pagination** (10 éléments par page)
- **Tri et filtrage** par libellé et prix
- **Dialogs modaux** :
  - **Nouveau produit** : Formulaire de création
  - **Modifier produit** : Formulaire d'édition
- **Validation** des champs obligatoires
- **Gestion des images** avec URL

#### Champs du formulaire :
- Libellé (obligatoire)
- Description (optionnel)
- Prix (obligatoire, format monétaire)
- Stock (obligatoire, entier positif)
- URL Image (optionnel)

---

### 👥 **10. admin/users.xhtml** - Gestion des utilisateurs
**Rôle** : Interface d'administration pour la gestion des comptes utilisateur  
**Template** : Utilise `layout.xhtml`  
**Bean principal** : `userBean`

#### Contrôle d'accès :
- **Restriction** : ADMIN uniquement
- **Message d'accès refusé** pour les non-administrateurs

#### Fonctionnalités :
- **Tableau des utilisateurs** avec colonnes :
  - ID, Nom, Email
  - Rôles (badges colorés : ADMIN=rouge, VENDEUR=orange, ACHETEUR=vert)
  - ID Panier
  - Actions : Modifier rôles, Supprimer
- **Pagination** (10 éléments par page)
- **Tri et filtrage** par nom et email
- **Dialog de modification des rôles** :
  - Checkboxes pour chaque rôle
  - Mise à jour en temps réel
  - Validation des permissions

#### Gestion des rôles :
- ADMIN : Accès complet
- VENDEUR : Gestion des produits
- ACHETEUR : Achat uniquement

---

### 🎨 **11. WEB-INF/templates/layout.xhtml** - Template principal
**Rôle** : Template de base pour toutes les pages utilisant la composition  
**Template** : Template racine  
**Bean principal** : `internauteBean`, `panierBean`, `authBean`

#### Structure :
- **En-tête HTML** avec métadonnées et CSS
- **Menu de navigation** avec :
  - Accueil (toujours visible)
  - Mon Panier (utilisateurs connectés uniquement)
  - Administration (ADMIN/VENDEUR)
  - Users (ADMIN uniquement)
- **Zone utilisateur** :
  - **Connecté** : Nom + bouton déconnexion
  - **Non connecté** : Boutons connexion/inscription
- **Messages globaux** avec PrimeFaces Growl
- **Zone de contenu** avec classe `main-card`
- **Pied de page** avec copyright

#### Fonctionnalités :
- **Navigation conditionnelle** basée sur les rôles
- **Badge du panier** avec nombre d'articles
- **Gestion des sessions** utilisateur
- **Messages d'erreur** globaux
- **Design responsive** avec thème cohérent

---

## 🔗 Relations entre les pages

### Flux de navigation principal :
```
index.xhtml (accueil)
    ↓
login.xhtml ← → register.xhtml
    ↓
panier.xhtml → checkout.xhtml
    ↓
index.xhtml (retour)
```

### Flux d'administration :
```
admin/produits.xhtml (gestion produits)
admin/users.xhtml (gestion utilisateurs)
```

### Pages utilitaires :
```
debug-panier.xhtml (développement)
test.xhtml (tests)
login-error.xhtml (erreurs)
```

---

## 🎨 Caractéristiques communes

### Design unifié :
- **Thème rose/magenta** cohérent sur toutes les pages
- **Animations** et transitions fluides
- **Design responsive** pour mobile et desktop
- **Icônes PrimeIcons** pour l'interface

### Technologies utilisées :
- **JSF (Jakarta Server Faces)** pour la logique web
- **PrimeFaces** pour les composants UI
- **Facelets** pour la composition des templates
- **CSS3** pour le style personnalisé
- **JavaScript** pour les interactions

### Patterns d'architecture :
- **MVC** : Séparation claire entre vue, contrôleur et modèle
- **Composition** : Réutilisation du template layout
- **CDI** : Injection de dépendances pour les beans
- **Validation** : Contrôles côté client et serveur

---

## 📊 Statistiques des fichiers

| **Fichier** | **Lignes** | **Complexité** | **Rôle** |
|-------------|------------|----------------|----------|
| `layout.xhtml` | 79 | Moyenne | Template |
| `index.xhtml` | 127 | Élevée | Page principale |
| `panier.xhtml` | 187 | Très élevée | Gestion panier |
| `admin/produits.xhtml` | 178 | Élevée | Administration |
| `admin/users.xhtml` | 124 | Moyenne | Administration |
| `register.xhtml` | 89 | Moyenne | Inscription |
| `login.xhtml` | 65 | Faible | Connexion |
| `checkout.xhtml` | 39 | Faible | Confirmation |
| `debug-panier.xhtml` | 59 | Moyenne | Debug |
| `test.xhtml` | 53 | Faible | Test |
| `login-error.xhtml` | 42 | Faible | Erreur |

**Total** : **1,041 lignes** de code JSF/XHTML

---

## 🚀 Points forts du design

### ✅ **Avantages** :
- **Cohérence visuelle** : Thème unifié sur toutes les pages
- **Responsive design** : Adaptation mobile et desktop
- **UX moderne** : Animations et interactions fluides
- **Sécurité** : Contrôle d'accès basé sur les rôles
- **Maintenabilité** : Structure claire et réutilisable

### 🔧 **Améliorations possibles** :
- **Internationalisation** : Support multi-langues
- **Accessibilité** : Conformité WCAG
- **Performance** : Optimisation des images et CSS
- **SEO** : Métadonnées et structure sémantique

---

**Date de documentation** : Janvier 2025  
**Version** : 1.0-SNAPSHOT  
**Statut** : Documentation complète
