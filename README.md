# Structure du projet ecommerce-app

Ce projet est une application Spring Boot 3 utilisant Java 21 et suit l'architecture hexagonale.
L'objectif est de créer un site web e-commerce avec une base de données PostgreSQL, la gestion des utilisateurs via Spring Security et JWT, et l'intégration d'un système de paiement tiers.

## Architecture du projet
Le projet est structuré en 3 couches principales, respectant l'architecture hexagonale :

1. **Application** :
    - Contient les cas d'utilisation (services et contrôleurs REST).
    - Expose les interfaces vers le monde extérieur.
    - Gestion de la sécurité, des DTO et des mappers.

2. **Domaine** :
    - Contient les entités principales (`Product`, `Order`, `User`), les objets de valeur, et la logique métier.
    - Comprend également les interfaces des repositories.

3. **Infrastructure** :
    - Implémente les repositories (via Spring Data JPA ou Couchbase), les adaptateurs pour les API externes (ex. paiement avec Stripe), et la configuration Spring.

## Dossier Conception
Le dossier **"conception"** centralise toute la documentation relative à la conception du système :

1. **Diagrammes** : Ce dossier contient les diagrammes utilisés pour la modélisation de l'architecture et des interactions du système.
    - **Diagramme de cas d'utilisation** : Décrit les actions effectuées par les utilisateurs (ex. navigation, ajout au panier, commande, paiement).
    - **Diagramme de classes** : Montre la structure des classes principales du domaine et leurs relations.
    - **Diagramme de composants** : Illustrations des différents composants du système et leurs interactions.
    - **Diagramme de séquence** : Détaille le flux des événements dans un scénario typique (ex. commande, paiement).

2. **Architecture.md** : Fournit une vue d'ensemble de l'architecture du projet, expliquant les choix d'architecture et la division du projet en couches.

3. **Exigences.md** : Décrit les exigences fonctionnelles et non fonctionnelles du système.

4. **Choix techniques.md** : Explique les choix techniques réalisés, notamment les choix des technologies (Spring Boot, PostgreSQL, Stripe, etc.), ainsi que les décisions concernant la sécurité, la gestion des utilisateurs, etc.

5. **Swagger Documentation** : Le projet utilise [Springdoc OpenAPI](https://springdoc.org/) pour générer automatiquement la documentation de l'API REST. Vous pouvez accéder à la documentation Swagger de l'API via le lien suivant :
    - [Swagger UI Documentation](http://localhost:8080/swagger-ui.html)

## Dépendances
- Spring Boot 3
- Java 21
- PostgreSQL
- Spring Security + JWT pour la gestion des utilisateurs
- Stripe pour le paiement
- Springdoc OpenAPI pour Swagger

## Instructions de démarrage
1. Clonez le projet
2. Configurez votre base de données PostgreSQL
3. Exécutez `mvn spring-boot:run` pour démarrer l'application
4. Vous pouvez aussi exécuter l'application via Docker avec la commande `docker-compose up`
5. Accédez à la documentation Swagger UI : [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)