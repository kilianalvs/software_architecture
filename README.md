# Frontend - Application React

## Lancement avec Docker

### Option 1 : Docker seul

```bash
# Se placer dans le dossier front
cd front

# Builder l'image
docker build -t front-app .

# Lancer le container
docker run -p 3000:80 front-app

# Ou en arrière-plan
docker run -d -p 3000:80 --name frontend-container front-app
```

Option 2 : Docker Compose (Recommandé)
```bash
# Se placer à la racine du projet
cd software_architecture/

# Construire et lancer
docker-compose up

# Ou en arrière-plan
docker-compose up -d

# Forcer la reconstruction
docker-compose up --build

# Arrêter les services
docker-compose down
```

Accès à l'application
Une fois lancée, l'application est accessible sur : http://localhost:3000

Commandes utiles
```bash
# Voir les containers en cours
docker ps

# Voir les logs (Docker seul)
docker logs <container_id>

# Voir les logs (Docker Compose)
docker-compose logs frontend

# Arrêter un container (Docker seul)
docker stop <container_id>

# Supprimer un container
docker rm <container_id>
```

TODO
Ajouter le backend
Ajouter les autres services
Configurer les variables d'environnement
Ajouter la base de données