NotFound - Plateforme de Mise en Relation
Une plateforme moderne de mise en relation entre prestataires et clients, développée avec une architecture hexagonale.

🏗️ Architecture
Stack Technique
Frontend: React.js avec TypeScript
Backend: Spring Boot 3.x avec Java 21
Base de données: H2 (développement), PostgreSQL (production)
Containerisation: Docker & Docker Compose
Architecture: Hexagonale (Ports & Adapters)
Structure du projet
notfound/
├── frontend/                    # Application React
│   ├── src/
│   ├── public/
│   ├── package.json
│   └── Dockerfile
├── backend/                     # Application Spring Boot
│   ├── src/main/java/com/esgi/notfound/
│   │   ├── NotfoundApplication.java
│   │   ├── exposition/         # Couche exposition (controllers)
│   │   ├── domain/            # Couche domaine (métier)
│   │   └── infrastructure/    # Couche infrastructure (adapters)
│   ├── pom.xml
│   └── Dockerfile
├── docker-compose.yml
└── README.md

🚀 Démarrage rapide
Prérequis
Docker & Docker Compose
Git
Installation et lancement
```bash
# Cloner le projet
git clone <repository-url>
cd notfound

# Lancer l'application complète
docker-compose up --build

# Ou lancer individuellement
docker-compose up --build frontend    # Frontend seul
docker-compose up --build backend     # Backend seul
```

Accès aux services
Service	URL	Description
Frontend	http://localhost:3000	Interface utilisateur React
Backend API	http://localhost:8080	API REST Spring Boot
Health Check	http://localhost:8080/health	Statut du backend