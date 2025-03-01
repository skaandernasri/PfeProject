-- schema.sql
CREATE TYPE role AS ENUM ('CLIENT', 'GESTIONNAIRE', 'ADMIN', 'REDACTEUR', 'SUPER_ADMIN');
CREATE TYPE type_authentification AS ENUM ('EMAIL', 'FACEBOOK', 'GOOGLE');
CREATE TYPE statut_commande AS ENUM ('EN_COURS', 'EXPEDIEE', 'LIVREE');
CREATE TYPE mode_paiement AS ENUM ('CARTE_BANCAIRE', 'VIREMENT', 'A_LIVRAISON');

-- Table: utilisateur
CREATE TABLE IF NOT EXISTS utilisateur (
    id BIGSERIAL PRIMARY KEY,
    nom VARCHAR(25),
    email VARCHAR(25) NOT NULL UNIQUE,
    password VARCHAR(25),
    roles role
);

-- Table: authentification
CREATE TABLE IF NOT EXISTS authentification (
    id BIGSERIAL PRIMARY KEY,
    type type_authentification,
    motDePasse VARCHAR(25) ,
    providerId VARCHAR(2),
    utilisateur_id BIGINT REFERENCES utilisateur(id)
);

-- Table: categorie
CREATE TABLE IF NOT EXISTS categorie (
    id BIGSERIAL PRIMARY KEY,
    nom VARCHAR(25) NOT NULL,
    description TEXT
);

-- Table: produit
CREATE TABLE IF NOT EXISTS produit (
    id BIGSERIAL PRIMARY KEY,
    nom VARCHAR(25) NOT NULL,
    description TEXT,
    prix DOUBLE PRECISION NOT NULL,
    stock INT NOT NULL,
    categorie_id BIGINT NOT NULL REFERENCES categorie(id)
);

-- Table: codepromo
CREATE TABLE IF NOT EXISTS codepromo (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(25) NOT NULL UNIQUE,
    reduction DOUBLE PRECISION NOT NULL,
    dateExpiration TIMESTAMP NOT NULL
);

-- Table: commande
CREATE TABLE IF NOT EXISTS commande (
    id BIGSERIAL PRIMARY KEY,
    date TIMESTAMP NOT NULL,
    statut statut_commande,
    utilisateur_id BIGINT NOT NULL REFERENCES utilisateur(id),
    modePaiement mode_paiement,
    codePromo_id BIGINT REFERENCES codepromo(id)
);

-- Table: lignecommande
CREATE TABLE IF NOT EXISTS lignecommande (
    id BIGSERIAL PRIMARY KEY,
    produit_id BIGINT NOT NULL REFERENCES produit(id),
    quantite INT NOT NULL,
    prixTotal DOUBLE PRECISION NOT NULL,
    commande_id BIGINT NOT NULL REFERENCES commande(id)
);

-- Table: blogpost
CREATE TABLE IF NOT EXISTS blogpost (
    id BIGSERIAL PRIMARY KEY,
    titre VARCHAR(50) NOT NULL,
    contenu TEXT NOT NULL,
    datePublication TIMESTAMP NOT NULL,
    auteur_id BIGINT NOT NULL REFERENCES utilisateur(id)
);

-- Table: commentaire
CREATE TABLE IF NOT EXISTS commentaire (
    id BIGSERIAL PRIMARY KEY,
    contenu TEXT NOT NULL,
    datePublication TIMESTAMP NOT NULL,
    auteur_id BIGINT NOT NULL REFERENCES utilisateur(id),
    blogpost_id BIGINT NOT NULL REFERENCES blogpost(id)
);

-- Table: promotion
CREATE TABLE IF NOT EXISTS promotion (
    id BIGSERIAL PRIMARY KEY,
    nom VARCHAR(25) NOT NULL,
    description TEXT,
    pourcentageReduction DOUBLE PRECISION NOT NULL,
    dateDebut TIMESTAMP NOT NULL,
    dateFin TIMESTAMP NOT NULL,
    produit_id BIGINT NOT NULL REFERENCES produit(id)
);

-- Table: imageproduit
CREATE TABLE IF NOT EXISTS imageproduit (
    id BIGSERIAL PRIMARY KEY,
    url VARCHAR(60) NOT NULL,
    produit_id BIGINT NOT NULL REFERENCES produit(id)
);

-- Table: imageblogpost
CREATE TABLE IF NOT EXISTS imageblogpost (
    id BIGSERIAL PRIMARY KEY,
    url VARCHAR(60) NOT NULL,
    blogpost_id BIGINT NOT NULL REFERENCES blogpost(id)
);

-- Table: facture
CREATE TABLE IF NOT EXISTS facture (
    id BIGSERIAL PRIMARY KEY,
    commande_id BIGINT NOT NULL REFERENCES commande(id),
    dateEmission TIMESTAMP NOT NULL,
    total DOUBLE PRECISION NOT NULL
);

-- Table: avis
CREATE TABLE IF NOT EXISTS avis (
    id BIGSERIAL PRIMARY KEY,
    note INT NOT NULL,
    commentaire TEXT,
    datePublication TIMESTAMP NOT NULL,
    utilisateur_id BIGINT NOT NULL REFERENCES utilisateur(id),
    produit_id BIGINT NOT NULL REFERENCES produit(id)
);

-- Table: historiquecommande
CREATE TABLE IF NOT EXISTS historiquecommande (
    id BIGSERIAL PRIMARY KEY,
    dateCommande TIMESTAMP NOT NULL,
    statut VARCHAR(50) NOT NULL,
    utilisateur_id BIGINT NOT NULL REFERENCES utilisateur(id)
);

-- Table: suiviclient
CREATE TABLE IF NOT EXISTS suiviclient (
    id BIGSERIAL PRIMARY KEY,
    utilisateur_id BIGINT NOT NULL REFERENCES utilisateur(id),
    action VARCHAR(20) NOT NULL,
    date TIMESTAMP NOT NULL
);