CREATE DATABASE pharmacie;
USE pharmacie;
CREATE TABLE Employe (
	idEmploye INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(50),
    prenom VARCHAR(50),
    tel INT UNIQUE,
    mail VARCHAR(100) UNIQUE NOT NULL,
    CONSTRAINT valid_email1 CHECK (mail LIKE '%@%._%'),
    mdp VARCHAR(100),
	privilege VARCHAR(7),
    CONSTRAINT valid_pri CHECK ( privilege in ('user', 'admin') )
);
CREATE TABLE Fournisseur(
	idFournisseur INT PRIMARY KEY AUTO_INCREMENT,
    entreprise VARCHAR(50),
    tel INT UNIQUE,
	mail VARCHAR(100) UNIQUE NOT NULL,
    CONSTRAINT valid_email2 CHECK (mail LIKE '%@%._%')
);
CREATE TABLE Commande(
	idCommande INT PRIMARY KEY AUTO_INCREMENT,
    idFournisseur INT,
    CONSTRAINT fk_idf FOREIGN KEY (idFournisseur) REFERENCES Fournisseur(idFournisseur),
    dateCommande DATE NOT NULL /* DEFAULT (CURDATE()) */,
    dateLivraisonPrevue DATE NOT NULL,
    dateLivraisonReelle DATE NOT NULL,
    CONSTRAINT dates_convenables CHECK(dateCommande<dateLivraisonPrevue),
    total INT NOT NULL,
    CONSTRAINT valid_tot2 CHECK (total > 0),
    etat VARCHAR(10),
    CONSTRAINT valid_etat CHECK (etat in ('livree', 'annulee', 'en cours')) 
);
CREATE TABLE Medicament(
	idMedicament INT PRIMARY KEY NOT NULL,
    label VARCHAR(50),
    quantite INT NOT NULL,
    CONSTRAINT valid_q2 CHECK(quantite > 0),
    texteDesc VARCHAR(400),
    prixUnitaireAchat FLOAT NOT NULL,
    CONSTRAINT valid_p2 CHECK(prixUnitaireAchat > 0),
    seuilMin INT NOT NULL,
    CONSTRAINT valid_s CHECK(seuilMin > 0)
);
CREATE TABLE DetailsCommande (
	idCommande INT,
    idMedicament INT,
    CONSTRAINT fk_idm FOREIGN KEY (idMedicament) REFERENCES Medicament(idMedicament),
    CONSTRAINT fk_idc FOREIGN KEY (idCommande) REFERENCES Commande(idCommande),
	CONSTRAINT pk_commande PRIMARY KEY (idCommande, idMedicament),
    quantite INT NOT NULL,
    CONSTRAINT valid_q1 CHECK(quantite > 0),
    prixUnitaireAchat FLOAT NOT NULL,
    CONSTRAINT valid_p1 CHECK(prixUnitaireAchat > 0)
);
CREATE TABLE Clients(
	idClient INT PRIMARY KEY NOT NULL,
    nom VARCHAR(50),
    prenom VARCHAR(50),
    tel INT UNIQUE
);
CREATE TABLE FactureVente (
	idVente INT PRIMARY KEY NOT NULL,
    idClient INT,
    CONSTRAINT fk_idc2 FOREIGN KEY (idClient) REFERENCES Clients(idClient),
    dateVente DATE NOT NULL,
    total INT NOT NULL,
    CONSTRAINT valid_tot1 CHECK (total > 0)
);
CREATE TABLE DetailsVente (
	idVente INT,
    idMedicament INT,
    CONSTRAINT fk_idm2 FOREIGN KEY (idMedicament) REFERENCES Medicament(idMedicament),
	CONSTRAINT fk_idv FOREIGN KEY (idVente) REFERENCES FactureVente(idVente),
	CONSTRAINT pk_DetailsVente PRIMARY KEY (idVente,idMedicament),
    prixUnitaireVente FLOAT NOT NULL,
    CONSTRAINT valid_p3 CHECK(prixUnitaireVente > 0),
    quantite INT NOT NULL,
    CONSTRAINT valid_q3 CHECK(quantite > 0)
);