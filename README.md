# Structure du projet tempo-rise-api

Ce projet est une application Spring Boot 3 utilisant Java 21 et suit l'architecture hexagonale.
L'objectif est de créer un site web e-commerce avec une base de données PostgreSQL, la gestion des utilisateurs via Spring Security et JWT, et l'intégration d'un système de paiement tiers.

## Architecture du projet

L'architecture du projet est composée de trois couches principales :

- **Domaine (Core)** : Cœur de l'application, contenant la logique métier.
- **Application** : Service de gestion des cas d'usage, orchestration des règles métier.
- **Infrastructure** : Adaptateurs qui implémentent les interactions externes (base de données, API REST, etc.).

### 📌 Vue d'ensemble de l'architecture
```
[ Infrastructure ]     [ Application ]        [ Domaine ]
---------------------------------------------------
ProductController --> ProductService --> Product (modèle métier)
                   |                 |
                   |                 --> ProductRepository (port)
                   |
                   --> ProductRepositoryImpl (adaptateur base de données)
```

## 💡 Description des Couches

### 1. Domaine (Core)

**Rôle** : Le domaine contient les entités métiers et les règles métier qui n'ont aucune dépendance avec les technologies utilisées dans le projet.

**Composants clés** :
- **Entités** : Modèles de données représentant des concepts métiers (par exemple, `Product`).
- **Ports** : Interfaces définissant les opérations autorisées, comme `ProductRepository`.
- **Logique métier** : Les règles et processus métier appliqués aux entités.

**Exemple de code** :

```java
public class Product {
    private Long id;
    private String name;
    private int stock;

    public void addStock(int quantity) {
        if (quantity < 0) throw new IllegalArgumentException("Quantity must be positive");
        this.stock += quantity;
    }
}
```

### 2. Application (Service)

**Rôle** : La couche applicative contient les services qui coordonnent les appels aux ports définis dans le domaine. Elle orchestre la logique métier en s'appuyant sur les entités du domaine et interagit avec les adaptateurs pour effectuer des opérations spécifiques.

**Composants clés** :
- **Services** : Logique de gestion des cas d'usage, comme `ProductService`.
- **Gestion des transactions** : Coordination des opérations nécessaires pour accomplir un cas d'usage.
- **Communication avec le domaine** : Appels aux ports du domaine pour manipuler les entités.

**Exemple de code** :

```java
@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void addStock(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        product.addStock(quantity);
        productRepository.save(product);
    }
}
```

### 3. Infrastructure (Adaptateurs)

**Rôle** : Cette couche implémente les adaptateurs qui permettent à l’application de communiquer avec le monde extérieur, comme une base de données, une API, un système de messagerie, etc.

**Composants clés** :
- **Adaptateurs d’interface** : Implémentations concrètes des interfaces définies dans le domaine.
- **Infrastructure technique** : Accès à la base de données, envoi de messages, etc.

**Exemple de code** :

```java
@Repository
public class ProductRepositoryImpl implements ProductRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProductRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Product> findById(Long id) {
        String sql = "SELECT * FROM products WHERE id = ?";
        Product product = jdbcTemplate.queryForObject(sql, new Object[]{id}, new ProductRowMapper());
        return Optional.ofNullable(product);
    }

    @Override
    public void save(Product product) {
        String sql = "UPDATE products SET stock = ? WHERE id = ?";
        jdbcTemplate.update(sql, product.getStock(), product.getId());
    }
}
```

## 🚀 Flux de données dans l'architecture

- **Appel via une API REST (Infrastructure)** :  
  Le contrôleur REST reçoit une requête HTTP et appelle le service applicatif approprié (ProductService).

- **Traitement des données dans l'application** :  
  Le service applicatif orchestre la logique métier en appelant les méthodes définies dans le domaine (Product.addStock).  
  Le service interagit avec le port de dépôt (ProductRepository) pour obtenir et enregistrer les entités.

- **Interaction avec l'infrastructure** :  
  L'adaptateur de dépôt (ProductRepositoryImpl) interagit avec la base de données pour stocker ou récupérer les données.

- **Réponse à l'utilisateur** :  
  Le contrôleur REST renvoie la réponse après traitement de la logique dans la couche applicative.

---

## 💡 Avantages de l'Architecture Hexagonale

- **Indépendance de l'infrastructure** :  
  Le domaine n'est jamais dépendant de la base de données, des API externes ou d'autres technologies. On peut changer la technologie de la base de données sans impacter la logique métier.

- **Testabilité** :  
  La logique métier peut être testée indépendamment de l'infrastructure en simulant les adaptateurs externes avec des mocks ou des fakes.

- **Évolutivité et modularité** :  
  L'architecture permet d'ajouter de nouveaux adaptateurs (par exemple, une API GraphQL ou un autre système de messagerie) sans perturber l'existant.

- **Séparation des responsabilités** :  
  Chaque couche a une responsabilité bien définie, ce qui facilite la gestion du code et améliore la maintenabilité.

---

## Structure des Dossiers

Voici une description détaillée de chaque dossier dans le projet, organisé selon l'architecture hexagonale.

```
src/
├── domain/
│   ├── model/
│   │   └── Product.java
│   ├── port/
│   │   └── ProductRepository.java
│   └── exception/
│       └── ProductNotFoundException.java
├── application/
│   ├── service/
│   │   └── ProductService.java
│   ├── mapping/
│   │   └── ProductMapper.java
│   └── exception/
│       └── ServiceException.java
├── infrastructure/
│   ├── repository/
│   │   └── ProductRepositoryImpl.java
│   ├── controller/
│   │   └── ProductController.java
│   ├── persistence/
│   │   └── entity/
│   │       └── ProductEntity.java
│   ├── security/
│   │   └── utils/
│   │       └── JwtUtils.java
│   └── client/
│   │    ├── ExternalApiClient.java
│   │
│   └── dto/
│     └── ProductResponse.java
└── config/
    └── FeignConfig.java
```


## 📂 `domain/`
Le dossier `domain` contient le cœur de l'application, c'est-à-dire la logique métier et les concepts fondamentaux du projet.

### 📁 `domain/model/`
- **Rôle** : Contient les entités métiers qui représentent les concepts fondamentaux de l'application.
- **Exemple** : `Product.java` (représente un produit dans le système).

### 📁 `domain/port/`
- **Rôle** : Contient les interfaces (ports) qui définissent les contrats pour interagir avec le monde extérieur (par exemple, les repositories).
- **Exemple** : `ProductRepository.java` (définit les opérations pour accéder aux produits).

### 📁 `domain/exception/`
- **Rôle** : Contient les exceptions métiers personnalisées.
- **Exemple** : `ProductNotFoundException.java` (levée lorsqu'un produit n'est pas trouvé).

---

## 📂 `application/`
Le dossier `application` contient la logique applicative, c'est-à-dire les services qui orchestrent les cas d'usage.

### 📁 `application/service/`
- **Rôle** : Contient les services applicatifs qui implémentent les cas d'usage.
- **Exemple** : `ProductService.java` (gère la logique pour ajouter, modifier ou récupérer des produits).

### 📁 `application/mapping/`
- **Rôle** : Contient les classes de mappage (par exemple, les DTOs et les mappers) pour convertir entre les entités métiers et les objets techniques.
- **Exemple** : `ProductMapper.java` (convertit entre `Product` et `ProductDTO`).

### 📁 `application/exception/`
- **Rôle** : Contient les exceptions liées à la couche applicative.
- **Exemple** : `ServiceException.java` (levée en cas d'erreur dans un service).

---

## 📂 `infrastructure/`
Le dossier `infrastructure` contient les implémentations techniques qui permettent à l'application d'interagir avec le monde extérieur.

### 📁 `infrastructure/repository/`
- **Rôle** : Contient les implémentations des repositories (adaptateurs pour la persistance des données).
- **Exemple** : `ProductRepositoryImpl.java` (implémente `ProductRepository` pour interagir avec la base de données).

### 📁 `infrastructure/controller/`
- **Rôle** : Contient les contrôleurs qui exposent les endpoints de l'API.
- **Exemple** : `ProductController.java` (expose les endpoints REST pour gérer les produits).

### 📁 `infrastructure/persistence/entity/`
- **Rôle** : Contient les entités JPA pour la persistance des données.
- **Exemple** : `ProductEntity.java` (représente un produit dans la base de données).

### 📁 `infrastructure/security/`
- **Rôle** : Contient les composants liés à la sécurité (filtres, intercepteurs, utilitaires).
- **Exemple** : `JwtAuthenticationFilter.java` (filtre pour l'authentification JWT).

### 📁 `infrastructure/client/`
- **Rôle** : Contient les clients pour interagir avec des API externes (par exemple, Feign).
- **Exemple** : `ExternalApiClient.java` (interface Feign pour appeler une API externe).

### 📁 `infrastructure/client/dto/`
- **Rôle** : Contient les DTOs pour les réponses et requêtes des API externes.
- **Exemple** : `ProductResponse.java` (représente la réponse d'une API externe).

---

## 📂 `config/`
Le dossier `config` contient les fichiers de configuration de l'application.

### 📄 `AppConfig.java`
- **Rôle** : Configuration globale de l'application (par exemple, les beans Spring).
- **Exemple** : Configuration des intercepteurs, des filtres, ou des clients Feign.

### 📄 `SecurityConfig.java`
- **Rôle** : Configuration spécifique à la sécurité (par exemple, la configuration JWT, les règles d'accès).

---

## 📂 `utils/`
Le dossier `utils` contient des classes utilitaires réutilisables dans tout le projet.

### 📄 `JwtUtils.java`
- **Rôle** : Contient des méthodes utilitaires pour la gestion des tokens JWT.
- **Exemple** : Génération et validation de tokens JWT.

---

## 📂 `exception/`
Le dossier `exception` peut être utilisé pour centraliser les exceptions globales de l'application.

### 📄 `GlobalExceptionHandler.java`
- **Rôle** : Gère les exceptions globales et renvoie des réponses HTTP appropriées.
- **Exemple** : Gestion des exceptions métiers et techniques.

---

## 📂 `mapping/`
Le dossier `mapping` peut être utilisé pour centraliser les classes de mappage (DTOs, mappers).

### 📄 `ProductDTO.java`
- **Rôle** : Représente un produit sous forme de DTO pour les échanges avec les clients.
- **Exemple** : Utilisé dans les contrôleurs pour exposer des données.

---

## 📂 `resources/`
Le dossier `resources` contient les fichiers de configuration externes et les ressources statiques.

### 📄 `application.yml`
- **Rôle** : Configuration de l'application (par exemple, URL de la base de données, clés API).

### 📄 `logback-spring.xml`
- **Rôle** : Configuration des logs de l'application.

### 📁 `resources/db/changelog/` (Liquibase)
- **Rôle** : Contient les fichiers de changelog Liquibase pour la gestion des migrations de la base de données.
- **Exemple** :
   - `db.changelog-master.xml` : Fichier maître qui référence tous les changelogs.
   - `db.changelog-1.0.xml` : Fichier de changelog pour la version 1.0 de la base de données.
   - `db.changelog-1.1.xml` : Fichier de changelog pour la version 1.1 de la base de données.

### 📁 `resources/api/` (OpenAPI/Swagger)
- **Rôle** : Contient les fichiers de spécification OpenAPI (Swagger) pour documenter l'API.
- **Exemple** :
   - `openapi.yaml` : Fichier de spécification OpenAPI au format YAML.

---

## 📂 `test/`
Le dossier `test` contient les tests unitaires et d'intégration.

### 📁 `test/domain/`
- **Rôle** : Tests unitaires pour la couche Domaine.
- **Exemple** : Tests des entités et de la logique métier.

### 📁 `test/application/`
- **Rôle** : Tests unitaires pour la couche Application.
- **Exemple** : Tests des services applicatifs.

### 📁 `test/infrastructure/`
- **Rôle** : Tests d'intégration pour la couche Infrastructure.
- **Exemple** : Tests des contrôleurs, des repositories et des clients externes.

---

## 📂 `conception/`
Le dossier `conception` contient les diagrammes utilisés pour la modélisation de l'architecture et des interactions du système :
- **Diagramme de cas d'utilisation** : Décrit les actions effectuées par les utilisateurs (ex. navigation, ajout au panier, commande, paiement).
- **Diagramme de classes** : Montre la structure des classes principales du domaine et leurs relations.
- **Diagramme de composants** : Illustrations des différents composants du système et leurs interactions.
- **Diagramme de séquence** : Détaille le flux des événements dans un scénario typique (ex. commande, paiement).

---

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
5. Accédez à la documentation Swagger UI : [http://localhost:8080/tempo-rise/api/swagger-ui.html](http://localhost:8080/tempo-rise/api/swagger-ui.html)
6. Pour mettre à jour le swagger, utilisez https://editor.swagger.io/
7. Pour lancer la base de données, utilisez le docker-compose
