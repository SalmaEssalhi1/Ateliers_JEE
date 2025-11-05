## Gestion de Stations-Service – Monorepo (Spring Boot + Angular)

Ce dépôt contient :
- `Atelier4-Restapi` – API REST Spring Boot 3 (packaging WAR, MySQL, JPA)
- `Atelier5-angular` – Application Angular consommant l’API

Les deux projets sont reliés pour le développement: Angular via un proxy → Spring Boot sur le port 8080 avec des routes `/api/*`.

---

### Prérequis
- Java JDK 17+ (fonctionne aussi avec JDK 21/23)
- Maven (wrapper fourni : `mvnw` / `mvnw.cmd`)
- Node.js 18+ et npm
- Angular CLI (`npm i -g @angular/cli`)
- MySQL en local

---

## Backend – Spring Boot (`Atelier4-Restapi`)

### Stack
- Spring Boot 3.3, JPA/Hibernate, Lombok
- MySQL
- Packaging WAR (exécutable embarqué ou déployable sur Tomcat)

### Configuration
Fichier : `Atelier4-Restapi/src/main/resources/application.properties`

```
spring.datasource.url=jdbc:mysql://localhost:3306/fuelstation?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

server.port=8080
server.servlet.context-path=
```

Adaptez les identifiants MySQL si besoin.

### Build & Run (embarqué)
```bash
cd Atelier4-Restapi
# Si JAVA_HOME n’est pas défini (exemple PowerShell pour JDK 23 sous Windows) :
# $env:JAVA_HOME = "C:\\Program Files\\Java\\jdk-23"; $env:Path = "$env:JAVA_HOME\\bin;$env:Path"

./mvnw -q clean package
./mvnw spring-boot:run
```

Health check : `http://localhost:8080/api/health`

### Modèle de domaine (simplifié)
- `Station(id, nom, ville, adresse)`
- `Carburant(id, nom, description)`
- `HistoCarb(date, station, carburant, prix)` avec clé composite `HistoCarbId(date, stationId, carburantId)`

Pour éviter la récursivité JSON, les références inverses sont ignorées et les endpoints de liste renvoient des DTO.

### Endpoints
- Santé : `GET /api/health`
- Stations :
  - `GET /api/stations` → `StationDTO[]`
  - `GET /api/stations/{id}`
  - `POST /api/stations`
  - `PUT /api/stations/{id}`
  - `DELETE /api/stations/{id}`
- Carburants :
  - `GET /api/carburants` → `CarburantDTO[]`
  - `GET /api/carburants/{id}`
  - `POST /api/carburants`
  - `PUT /api/carburants/{id}`
  - `DELETE /api/carburants/{id}`
- Prix journaliers :
  - `GET /api/prix`
  - `GET /api/prix/{date}/{stationId}/{carburantId}`
  - `GET /api/prix/station/{stationId}` (optionnel `?date=YYYY-MM-DD`)
  - `POST /api/prix`
  - `PUT /api/prix/{date}/{stationId}/{carburantId}`
  - `DELETE /api/prix/{date}/{stationId}/{carburantId}`

### Notes Lombok & Maven
- Lombok génère getters/setters/constructeurs.
- `maven-compiler-plugin` est configuré pour le processor Lombok.
Sous IntelliJ, installez le plugin Lombok et activez « Annotation Processing » si besoin.

### Option : Tomcat externe
Construire le WAR et le déposer dans `webapps` (renommez en `ROOT.war` pour servir sous `/`). Si vous utilisez un autre contexte, mettez à jour l’`apiUrl` Angular ou gardez le proxy pour le dev.

---

## Frontend – Angular (`Atelier5-angular`)

### Proxy de développement
`Atelier5-angular/proxy.conf.json`
```
{
  "/api": {
    "target": "http://localhost:8080",
    "secure": false,
    "changeOrigin": true,
    "logLevel": "debug"
  }
}
```

### Environnement
`Atelier5-angular/src/environments/environment.ts`
```
export const environment = {
  production: false,
  apiUrl: ''
};
```
Les services construisent les URLs comme `${apiUrl}/api/...` → en dev, les appels vers `/api/*` sont proxyés vers Spring Boot.

### Installation & Lancement
```bash
cd Atelier5-angular
npm install
ng serve
```

Application : `http://localhost:4200`

### UI
- Listes Stations, Carburants et Prix avec styles modernes
- Formulaires Créer/Modifier (layout en carte)
- Recherche côté client dans les listes
- Indicateurs de chargement et états vides

### Structure
```
Atelier5-angular/src/app/
  components/
    stations/ (liste + formulaire)
    carburants/ (liste + formulaire)
    prix/ (liste + filtres)
  services/
    station.service.ts
    carburant.service.ts
    prix.service.ts
  models/
    *.model.ts
```

---

## Vérification End-to-End (Dev)
1) Démarrer le backend
   - `./mvnw spring-boot:run`
   - Vérifier `http://localhost:8080/api/health` → JSON `{ status: "UP" }`
2) Démarrer le frontend
   - `ng serve`
   - Vérifier via proxy :
     - `http://localhost:4200/api/health`
     - `http://localhost:4200/api/stations`

---

## Dépannage

### JAVA_HOME introuvable
Définir JAVA_HOME (ex. PowerShell) :
```
$env:JAVA_HOME = "C:\\Program Files\\Java\\jdk-23"
$env:Path = "$env:JAVA_HOME\\bin;$env:Path"
java -version
./mvnw -v
```
Rendre persistant (utilisateur courant) :
```
setx JAVA_HOME "C:\\Program Files\\Java\\jdk-23"
setx PATH "%PATH%;C:\\Program Files\\Java\\jdk-23\\bin"
```

### Port 8080 occupé
Arrêtez Tomcat externe (ou tout processus) avant d’exécuter Spring Boot embarqué.

### 404 depuis Angular vers l’API
- Vérifiez que le backend tourne sur `http://localhost:8080` et que `server.servlet.context-path` est vide.
- Redémarrez `ng serve` après toute modification du proxy.

### Erreurs de parsing JSON côté Angular
- Résolu en ignorant les références inverses et en renvoyant des DTO pour les listes. Pour de nouvelles relations, privilégiez des DTOs ou `@JsonIgnore`/`@JsonManagedReference`.

---

## Notes / Décisions
- Chemin de base unifié en dev: `/api/*` (pas de context-path global).
- Endpoint de santé: `/api/health`.
- Styles Angular améliorés (cards, loaders, recherche, compteurs).

---

## Démarrage Rapide (Windows PowerShell)
```powershell
# Backend
cd C:\Users\HP\IdeaProjects\t\Atelier4-Restapi
$env:JAVA_HOME = "C:\\Program Files\\Java\\jdk-23"; $env:Path = "$env:JAVA_HOME\\bin;$env:Path"
./mvnw -q clean package
./mvnw spring-boot:run

# Frontend (nouvelle fenêtre)
cd C:\Users\HP\IdeaProjects\t\Atelier5-angular
npm install
ng serve
```

---

## Licence
Projet éducatif / démonstration.


