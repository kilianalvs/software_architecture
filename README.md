Software architecture

# Architecture

## Stack Technique

Frontend: React.js
Backend: Spring Boot 3.x avec Java 17
Base de donnees: H2 (en memoire)
Containerisation: Docker & Docker Compose

## Structure du projet
```bash
notfound/
├── frontend/                    # Application React
│   ├── src/
│   ├── public/
│   ├── package.json
│   └── Dockerfile
├── backend/                     # Application Spring Boot  
│   ├── src/main/java/com/esgi/notfound/
│   │   ├── NotfoundApplication.java
│   │   └── exposition/
│   │       └── controllers/
│   ├── pom.xml
│   └── Dockerfile
├── docker-compose.yml
└── README.md
```
Demarrage
Prerequis
Docker & Docker Compose
Installation et lancement
# Cloner le projet
```bash
git clone https://github.com/kilianalvs/software_architecture.git
cd software_architecture

# Lancer l'application complete
docker-compose up --build

# Ou lancer individuellement
docker-compose up --build frontend    # Frontend seul
docker-compose up --build backend     # Backend seul
```

Acces aux services
Service	URL	Description
Frontend	http://localhost:3000	Interface utilisateur React
Backend API	http://localhost:8080	API REST Spring Boot
Health Check	http://localhost:8080/health	Statut du backend
Base H2	http://localhost:8080/h2-console	Console base de donnees
Configuration H2 Console
JDBC URL: jdbc:h2:mem:testdb
Username: sa
Password: (vide)
Commandes utiles
```bash
# Voir les logs
docker-compose logs frontend
docker-compose logs backend

# Rebuild sans cache
docker-compose build --no-cache

# Arreter tous les services
docker-compose down

Troubleshooting
Le backend ne demarre pas
# Verifier les logs
docker-compose logs backend

# Rebuild complet
docker-compose down
docker-compose build --no-cache backend
docker-compose up backend
```
Verifications
Backend repond : http://localhost:8080/health
Frontend accessible : http://localhost:3000
Base H2 accessible : http://localhost:8080/h2-console
Status: Frontend operationnel | Backend operationnel | Base H2 operationnelle

# lancer les test en back :


1️⃣ LANCER LES TESTS NORMALEMENT
# Tests seuls
docker-compose run --rm backend-tests

# Tests avec Maven explicite
docker-compose run --rm backend-tests mvn test

# Tests avec compilation
docker-compose run --rm backend-tests mvn clean test

en cas de pb :
2️⃣ FORCER LE REBUILD COMPLET
# Rebuild l'image Docker + tests
docker-compose build backend-tests
docker-compose run --rm backend-tests mvn clean test

# OU en une seule commande
docker-compose build backend-tests && docker-compose run --rm backend-tests mvn clean test

3️⃣ REBUILD TOTAL (cache Docker + Maven)
# Supprimer tout et rebuild
docker-compose down
docker-compose build --no-cache backend-tests
docker-compose run --rm backend-tests mvn clean compile test

# Supprimer les volumes aussi
docker-compose down -v
docker system prune -f
docker-compose build --no-cache backend-tests
docker-compose run --rm backend-tests mvn clean test