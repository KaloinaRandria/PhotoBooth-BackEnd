    CREATE SEQUENCE role_seq START 10 INCREMENT 1;
    CREATE SEQUENCE poste_seq START 1 INCREMENT 1;
    CREATE SEQUENCE membre_seq START 1 INCREMENT 1;
    CREATE SEQUENCE image_membre_seq START 1 INCREMENT 1;
    CREATE SEQUENCE salle_seq START 1 INCREMENT 1;
    CREATE SEQUENCE image_salle_seq START 1 INCREMENT 1;
    CREATE SEQUENCE categorie_theme_seq START 1 INCREMENT 1;
    CREATE SEQUENCE theme_seq START 1 INCREMENT 1;
    CREATE SEQUENCE image_theme_seq START 1 INCREMENT 1;
    CREATE SEQUENCE materiel_seq START 1 INCREMENT 1;
    CREATE SEQUENCE materiel_theme_seq START 1 INCREMENT 1;
    CREATE SEQUENCE salaire_seq START 1 INCREMENT 1;
    CREATE SEQUENCE comp_service_seq START 1 INCREMENT 1;
    CREATE SEQUENCE tarif_comp_service_seq START 1 INCREMENT 1;
    CREATE SEQUENCE client_seq START 1 INCREMENT 1;
    CREATE SEQUENCE image_client_seq START 1 INCREMENT 1;
    CREATE SEQUENCE reservation_seq START 1 INCREMENT 1;
    CREATE SEQUENCE resevation_detail_seq START 1 INCREMENT 1;
    CREATE SEQUENCE reservation_detail_materiel_seq START 1 INCREMENT 1;
    CREATE SEQUENCE historique_seq START 1 INCREMENT 1;

    create table role(
        id_role varchar(20) PRIMARY KEY ,
        intitule varchar(250) not null unique
    );

     create table poste(
        id_poste varchar(20) PRIMARY KEY ,
        intitule varchar(250) not null unique
    );

	create table membre(
        id_membre varchar(20) PRIMARY KEY ,
        id_role varchar(20) not null references role(id_role),
        id_poste varchar(20) not null references poste(id_poste),
        nom varchar(250) not null,
        prenom varchar(250) not null,
        username varchar(250) not null unique,
        email varchar(250) not null unique,
        date_naissance date not null,
        date_embauche date not null,
        mot_de_passe varchar(250)  not null
    );


    create table image_membre(
        id_image_membre varchar(20) PRIMARY KEY ,
        id_membre varchar(20) references membre(id_membre),
        image_url varchar(250) not null,
        date_insertion timestamp 
    );

    create table salle(
        id_salle varchar(20) PRIMARY KEY ,
        numero int not null
    );

    create table image_salle(
        id_image_salle varchar(20) PRIMARY KEY ,
        id_salle varchar(20) references salle(id_salle),
        image_url varchar(250) not null,
        date_insertion timestamp 
    );

  create table categorie_theme(
        id_categorie_theme varchar(20) PRIMARY KEY,
        intitule varchar(100) not null unique
    );

    create table theme(
        id_theme varchar(20) PRIMARY KEY ,
        id_salle varchar(20) not null references salle(id_salle),
        id_categorie_theme varchar(20) not null references categorie_theme(id_categorie_theme),
        intitule varchar(100) not null unique,
        date_debut date not null,
        date_fin date not null,
        description varchar(250)
    );

    create table image_theme(
        id_image_theme varchar(20) PRIMARY KEY ,
        id_theme varchar(20) references theme(id_theme),
        image_url varchar(250) not null,
        date_insertion timestamp 
    );

    create table materiel(
        id_materiel varchar(20) PRIMARY KEY ,
        intitule varchar(250) not null unique,
        quantite int not null,
        prix decimal(10,2) not null,
        image_url varchar(250)
    );

    create table materiel_theme(
        id_materiel_theme varchar(20) PRIMARY KEY ,
        id_materiel varchar(20) not null references materiel(id_materiel),
        id_theme varchar(20) not null references theme(id_theme)
    );

    create table salaire(
        id_salaire varchar(20) PRIMARY KEY ,
        id_membre varchar(20) references membre(id_membre)  not null,
        montant decimal(10,2) not null,
        date_insertion timestamp not null
    );

    create table comp_service (
        id_comp_service varchar(20) PRIMARY KEY ,
        intitule varchar(250) not null unique,
        prix_unitaire decimal(10,2) not null
    );

    create table tarif_comp_service(
        id_tarif_service varchar(20) PRIMARY KEY ,
        id_comp_service varchar(20) references comp_service(id_comp_service),
        nombre int
    );


    create table client(
        id_client varchar(20) PRIMARY KEY ,
        nom varchar(250) not null,
        prenom varchar(250) not null,
        email varchar(250) not null unique,
        num_telephone varchar(250) not null unique,
        date_de_naissance date not null
    );

    create table image_client(
        id_image_client varchar(20) PRIMARY KEY ,
        id_client varchar(20) references client(id_client),
        image_url varchar(250) not null,
        date_insertion timestamp 
    );

    create table reservation(
        id_reservation varchar(20) PRIMARY KEY ,
        date_reservation timestamp not null ,
        date_reservee timestamp not null,
        id_client varchar(20) references client(id_client) not null,
        id_service varchar(20) references comp_service(id_comp_service) not null,
        heure_debut timestamp not null,
        heure_fin timestamp not null,
        prix decimal(10,2) not null
    );

    create table reservation_detail(
        id_reservation_detail varchar(20) PRIMARY KEY ,
        id_resevation varchar(20) references reservation (id_reservation)  not null,
        id_theme VARCHAR(20) references theme(id_theme),
        customDesc VARCHAR(250),
        custom BIT default false
    );

    create table reservation_detail_materiel (
        id_reservation_detail_materiel varchar(20) PRIMARY KEY,
        id_reservation_detail varchar(20) PRIMARY KEY ,
        id_materiel varchar(20) references materiel(id_materiel)
    );

    create table historique(
        id_historique varchar(20) PRIMARY KEY ,
        id_theme varchar(20),
        date_debut timestamp not null,
        date_fin timestamp not null,
        montant_entrant decimal(10,2) not null
    );
