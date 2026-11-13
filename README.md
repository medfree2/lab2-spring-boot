# Lab 2 · API REST CRUD avec Spring Boot

API de gestion de produits en mémoire, avec Java 21, Maven et Spring Boot 3.

## Démarrage

Avec Java 21 et Maven installés, lancer `mvn spring-boot:run` à la racine. Le serveur écoute sur `http://localhost:8080`.

Importer `Lab2.postman_collection.json` dans Postman et lancer la collection dans l’ordre fourni. La première requête mémorise l’identifiant créé pour les requêtes suivantes.

| Opération | Route | Statut |
| --- | --- | --- |
| Créer | POST /api/products | 201 |
| Lister | GET /api/products | 200 |
| Consulter | GET /api/products/{id} | 200 |
| Rechercher par nom | GET /api/products/search?name=portable | 200 |
| Modifier | PUT /api/products/{id} | 200 |
| Supprimer | DELETE /api/products/{id} | 204 |

La validation renvoie 400 pour des données invalides, notamment une quantité supérieure à 1000. Une ressource absente renvoie 404. Les DTO utilisent des records Java. Les produits sont stockés en mémoire et disparaissent au redémarrage.

## Résultats vérifiés en local

10 scénarios HTTP exécutés contre le serveur Spring Boot. Le visuel est généré à partir de la sortie réelle des tests.

![Résultats des tests HTTP](docs/resultats-http.png)

[Sortie texte des tests](docs/resultats-http.txt)
