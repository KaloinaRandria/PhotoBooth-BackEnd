CREATE SEQUENCE id_role_seq START 1;
CREATE SEQUENCE id_poste_seq START 1;
CREATE SEQUENCE id_membre_seq START 3;
CREATE SEQUENCE id_salaire_seq START 1;


CREATE TABLE role (
    id_role VARCHAR(20) PRIMARY KEY,
    intitule VARCHAR(100) UNIQUE
);

CREATE TABLE poste (
    id_poste VARCHAR(20) PRIMARY KEY,
    intitule VARCHAR(100) UNIQUE
);

CREATE TABLE membre (
    id_membre VARCHAR(20) PRIMARY KEY,
    id_role VARCHAR(20) REFERENCES role(id_role) NOT NULL ,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    date_de_naissance DATE NOT NULL,
    username VARCHAR(100) UNIQUE NOT NULL,
    mail VARCHAR(100) UNIQUE NOT NULL,
    mot_de_passe VARCHAR(250) NOT NULL,
    date_embauche DATE NOT NULL,
    id_poste VARCHAR(20) REFERENCES poste(id_poste) NOT NULL
);

CREATE TABLE salaire(
    id_salaire VARCHAR(20) PRIMARY KEY,
    id_membre VARCHAR(20) REFERENCES membre(id_membre) NOT NULL,
    montant DECIMAL(10,2) NOT NULL,
    date_insertion TIMESTAMP NOT NULL
);

/* test, example de donnes */
INSERT INTO role VALUES ('ROLE_1', 'Level1');
INSERT INTO role VALUES ('ROLE_2', 'Level2');

INSERT INTO poste VALUES ('POSTE_1', 'secretary');
INSERT INTO poste VALUES ('POSTE_2', 'stock_manager');

INSERT INTO membre VALUES (
    'MBR_1',
    'ROLE_2',
    'Rabary',
    'Fifaliana',
    '2004-12-06',
    'fifa01',
    'fifa@gmail.com',
    'mdp001',
    '2024-05-01',
    'POSTE_1'
);

INSERT INTO membre VALUES (
    'MBR_2',
    'ROLE_1',
    'Rajoelina',
    'Rohy',
    '2003-01-16',
    'Rohy007',
    'Rohy@yahoo.com',
    'mdp001',
    '2024-05-02',
    'POSTE_2'
);

INSERT INTO salaire VALUES (
    'SAL_1',
    'MBR_1',
    250000,
    '2024-05-02 01:15:50'
);

CREATE TABLE test (
    id SERIAL PRIMARY KEY,
    intitule VARCHAR(100)
);

INSERT INTO test VALUES (1, 'test1');
INSERT INTO test VALUES (2, 'test2');