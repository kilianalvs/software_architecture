-- Ce script s'exécute automatiquement lors du démarrage de PostgreSQL
-- Pour plus tard, quand vous passerez de H2 à PostgreSQL

CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Ajoutez vos autres tables ici plus tard