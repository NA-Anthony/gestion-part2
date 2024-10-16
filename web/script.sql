CREATE TABLE demande_achat(
   id_demande_achat SERIAL,
   date_demande DATE NOT NULL,
   PRIMARY KEY(id_demande_achat)
);

CREATE TABLE pdg(
   id_pdg SERIAL,
   nom VARCHAR(50)  NOT NULL,
   email VARCHAR(50)  NOT NULL,
   mdp VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id_pdg)
);

CREATE TABLE societe(
   id_societe SERIAL,
   nom VARCHAR(50)  NOT NULL,
   id_pdg INTEGER NOT NULL,
   PRIMARY KEY(id_societe),
   FOREIGN KEY(id_pdg) REFERENCES pdg(id_pdg)
);

CREATE TABLE departement(
   id_departement SERIAL,
   nom VARCHAR(30)  NOT NULL,
   id_societe INTEGER NOT NULL,
   PRIMARY KEY(id_departement),
   FOREIGN KEY(id_societe) REFERENCES societe(id_societe)
);

CREATE TABLE chef_departement(
   id_chef SERIAL,
   nom VARCHAR(50)  NOT NULL,
   email VARCHAR(50)  NOT NULL,
   mdp VARCHAR(50)  NOT NULL,
   id_departement INTEGER NOT NULL,
   PRIMARY KEY(id_chef),
   FOREIGN KEY(id_departement) REFERENCES departement(id_departement)
);

CREATE TABLE stock(
   id_stock SERIAL,
   prix_unitaire NUMERIC(15,2)   NOT NULL,
   quantite NUMERIC(15,2)   NOT NULL,
   id_societe INTEGER NOT NULL,
   PRIMARY KEY(id_stock),
   FOREIGN KEY(id_societe) REFERENCES societe(id_societe)
);

CREATE TABLE produit(
   id_produit SERIAL,
   nom VARCHAR(50)  NOT NULL,
   id_stock INTEGER,
   PRIMARY KEY(id_produit),
   FOREIGN KEY(id_stock) REFERENCES stock(id_stock)
);

CREATE TABLE produit_dmd_achat(
   id_produit INTEGER,
   id_demande_achat INTEGER,
   quantite NUMERIC(15,2)   NOT NULL,
   etat INTEGER NOT NULL,
   PRIMARY KEY(id_produit, id_demande_achat),
   FOREIGN KEY(id_produit) REFERENCES produit(id_produit),
   FOREIGN KEY(id_demande_achat) REFERENCES demande_achat(id_demande_achat)
);
