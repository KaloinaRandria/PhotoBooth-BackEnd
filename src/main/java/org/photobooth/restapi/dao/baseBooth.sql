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
        image_url varchar(250),
        prix_achat decimal(10,2) not null
    );

    create table materiel_theme(
        id_materiel_theme varchar(20) PRIMARY KEY ,
        id_materiel varchar(20) not null references materiel(id_materiel),
        quantite int not null,
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
        icon varchar(50) not null,
        color varchar(15) not null
    );

    create table tarif_comp_service(
        id_tarif_service varchar(20) PRIMARY KEY ,
        id_comp_service varchar(20) references comp_service(id_comp_service),
        id_value_ranges int not null,
        prix decimal(10,2) not null
    );

    CREATE TABLE value_ranges (
          id SERIAL PRIMARY KEY,
          range_label VARCHAR(50) NOT NULL,
          min_value INT,
          max_value INT
    );


    create table client(
        id_client varchar(20) PRIMARY KEY ,
        nom varchar(250) not null,
        prenom varchar(250) not null,
        email varchar(250) not null unique,
        num_telephone varchar(250) not null unique,
        date_reservation timestamp not null 
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
        date_reservee date not null,
        id_client varchar(20) references client(id_client) not null,
        id_service varchar(20) references comp_service(id_comp_service) not null,
        nb_personne INT not null,
        heure_debut timestamp not null,
        heure_fin timestamp not null,
        prix decimal(10,2) not null,
        id_theme VARCHAR(20) references theme(id_theme) not null,
        id_salle VARCHAR(20) references salle(id_salle) not null,
        photograph boolean not null,
        isConfirmed boolean default false,
        isValid boolean default true
    );

    create table reservation_detail(
        id_reservation_detail varchar(20) PRIMARY KEY ,
        id_resevation varchar(20) references reservation (id_reservation)  not null,
        customDesc VARCHAR(250),
        custom BIT default false
    );

    create table reservation_detail_materiel (
        id_reservation_detail_materiel varchar(20) PRIMARY KEY,
        id_reservation_detail varchar(20) PRIMARY KEY ,
        id_materiel varchar(20) references materiel(id_materiel)
    );

    create table depense (
        id SERIAL PRIMARY KEY ,
        montant decimal(10,2) not null,
        libele varchar(50) not null,
        date_insertion date not null
    );

    create table historique(
        id_historique varchar(20) PRIMARY KEY ,
        id_theme varchar(20),
        date_action date not null,
        date_debut timestamp not null,
        date_fin timestamp not null,
        montant_entrant decimal(10,2) not null
    );

    INSERT INTO value_ranges (range_label, min_value, max_value) VALUES
     ('less than 3', NULL, 3),
     ('between 3 and 10', 3, 10),
     ('more than 10', 10, NULL);

    INSERT INTO comp_service VALUES ('S_1', 'album', 0);
    INSERT INTO comp_service VALUES ('S_2', 'photo', 0);

    INSERT INTO tarif_comp_service VALUES ('TCS_1', 'S_1', 1 , 100000);
    INSERT INTO tarif_comp_service VALUES ('TCS_2', 'S_1', 2 , 300000);
    INSERT INTO tarif_comp_service VALUES ('TCS_3', 'S_1', 3 , 500000);

    INSERT INTO tarif_comp_service VALUES ('TCS_4', 'S_2', 1 , 80000);
    INSERT INTO tarif_comp_service VALUES ('TCS_5', 'S_2', 2 , 150000);
    INSERT INTO tarif_comp_service VALUES ('TCS_6', 'S_2', 3 , 400000);

    /* vue  : */
    CREATE OR REPLACE VIEW v_theme_worth AS (SELECT SUM(mt.quantite * m.prix) as worth, t.id_theme FROM theme t LEFT JOIN materiel_theme mt ON t.id_theme = mt.id_theme JOIN materiel m ON mt.id_materiel = m.id_materiel group by t.id_theme);
    CREATE OR REPLACE VIEW v_used_materiel AS (SELECT m.intitule, SUM(mt.quantite) FROM materiel m LEFT JOIN materiel_theme mt ON m.id_materiel = mt.id_materiel GROUP BY m.id_materiel);

    CREATE VIEW v_service_stat_today AS
    SELECT
        cs.color AS color,
        cs.intitule AS service_intitule,
        COALESCE(SUM(r.prix), 0) AS total_prix
    FROM
        comp_service cs
            LEFT JOIN
        reservation r ON r.id_service = cs.id_comp_service AND DATE(r.date_reservee) = CURRENT_DATE
            GROUP BY
            cs.intitule , cs.color;

    CREATE OR REPLACE VIEW v_service_stat_last_30_days AS
    SELECT
        cs.color AS color,
        cs.intitule AS service_intitule,
        COALESCE(SUM(r.prix), 0) AS total_prix
    FROM
        comp_service cs
            LEFT JOIN
        reservation r ON r.id_service = cs.id_comp_service AND r.date_reservee >= CURRENT_DATE - INTERVAL '30 days'
            GROUP BY
            cs.intitule, cs.color;

    CREATE VIEW v_service_stat_this_year AS
    SELECT
        cs.color AS color,
        cs.intitule AS service_intitule,
        COALESCE(SUM(r.prix), 0) AS total_prix
    FROM
        comp_service cs
            LEFT JOIN
        reservation r ON r.id_service = cs.id_comp_service
            AND DATE_PART('year', r.date_reservee) = DATE_PART('year', CURRENT_DATE)
    GROUP BY
        cs.intitule , cs.color;





    create table historique(
           id_historique varchar(20) PRIMARY KEY ,
           id_theme varchar(20),
           date_action date not null,
           date_debut timestamp not null,
           date_fin timestamp not null,
           montant_entrant decimal(10,2) not null
    );

    create table depense (
                             id SERIAL PRIMARY KEY ,
                             montant decimal(10,2) not null,
                             libele varchar(50) not null,
                             date_insertion date not null
    );

    create table notification (
        id SERIAL PRIMARY KEY ,
        libele varchar(255) not null,
        type varchar(30) not null,
        icon varchar(40) ,
        action_date timestamp not null,
        id_membre VARCHAR(20) references membre(id_membre)
    );

creer moi une fonction

    SELECT
        EXTRACT(MONTH FROM date_insertion) AS mois,
        SUM(montant) AS total_depense
    FROM
        depense
    WHERE
        EXTRACT(YEAR FROM date_insertion) = 2024
    GROUP BY
        EXTRACT(MONTH FROM date_insertion)
    ORDER BY
        mois;

    /*WITH all_months AS (
        SELECT generate_series(1, 12) AS mois
    )
    SELECT
        all_months.mois,
        COALESCE(SUM(depense.montant), 0) AS total_depense
    FROM
        all_months
            LEFT JOIN
        depense ON EXTRACT(MONTH FROM depense.date_insertion) = all_months.mois
            AND EXTRACT(YEAR FROM depense.date_insertion) = 2024
    GROUP BY
        all_months.mois
    ORDER BY
        all_months.mois;*/

    SELECT all_months.mois, COALESCE(SUM(depense.montant), 0) AS total_depense FROM generate_series(1, 12) AS all_months(mois) LEFT JOIN depense ON EXTRACT(MONTH FROM depense.date_insertion) = all_months.mois AND EXTRACT(YEAR FROM depense.date_insertion) = 2024 GROUP BY all_months.mois ORDER BY all_months.mois;



    /*WITH all_months AS (
        SELECT generate_series(1, 12) AS mois
    )
    SELECT
        all_months.mois,
        COALESCE(SUM(historique.montant_entrant), 0) AS total_depense
    FROM
        all_months
            LEFT JOIN
        historique ON EXTRACT(MONTH FROM historique.date_action) = all_months.mois
            AND EXTRACT(YEAR FROM historique.date_action) = 2024
    GROUP BY
        all_months.mois
    ORDER BY
        all_months.mois;*/
    SELECT all_months.mois, COALESCE(SUM(historique.montant_entrant), 0) AS total_depense FROM generate_series(1, 12) AS all_months(mois) LEFT JOIN historique ON EXTRACT(MONTH FROM historique.date_action) = all_months.mois AND EXTRACT(YEAR FROM historique.date_action) = 2024 GROUP BY all_months.mois ORDER BY all_months.mois;


    /* resa par mois selon une annee donne */
    WITH months AS (SELECT generate_series(1, 12) AS mois), reservations_per_month AS (SELECT EXTRACT(MONTH FROM r.date_reservee) AS mois, COUNT(r.id_reservation) AS nombre FROM reservation r WHERE EXTRACT(YEAR FROM r.date_reservee) = 2024 GROUP BY EXTRACT(MONTH FROM r.date_reservee)) SELECT m.mois AS mois, COALESCE(r.nombre, 0) AS nombre FROM months m LEFT JOIN reservations_per_month r ON m.mois = r.mois ORDER BY m.mois;

    WITH months AS (SELECT generate_series(1, 12) AS mois),
         reservations_status AS (SELECT EXTRACT(MONTH FROM r.date_reservee) AS mois, COUNT(*) AS total_reservations, SUM(CASE WHEN r.isValid = false THEN 1 ELSE 0 END) AS reservations_annulees, SUM(CASE WHEN r.isValid = true AND r.isConfirmed = true THEN 1 ELSE 0 END) AS reservations_confirmees, SUM(CASE WHEN r.isValid = true AND r.isConfirmed = false THEN 1 ELSE 0 END) AS reservations_en_attente FROM reservation r WHERE EXTRACT(YEAR FROM r.date_reservee) = 2024 GROUP BY EXTRACT(MONTH FROM r.date_reservee))
    SELECT m.mois AS mois, COALESCE(rs.total_reservations, 0) AS total_reservations, COALESCE(rs.reservations_annulees, 0) AS reservations_annulees, COALESCE(rs.reservations_confirmees, 0) AS reservations_confirmees, COALESCE(rs.reservations_en_attente, 0) AS reservations_en_attente FROM months m LEFT JOIN reservations_status rs ON m.mois = rs.mois ORDER BY m.mois;


    CREATE VIEW v_client_stat AS
    SELECT
        c.id_client,
        CONCAT(c.prenom, ' ', c.nom) AS nom_client,
        COUNT(r.id_reservation) AS nb_reservations,
        COALESCE(SUM(r.prix), 0) AS total_prix
    FROM
        client c
            LEFT JOIN
        reservation r ON c.id_client = r.id_client AND r.isConfirmed = true
    GROUP BY
        c.id_client, c.prenom, c.nom
    ORDER BY
        c.nom, c.prenom;

    CREATE VIEW v_curr_state AS
    SELECT DISTINCT ON (s.id_salle)
                s.id_salle,
                t.id_theme,
                CASE
                WHEN r.id_reservation IS NULL THEN true
                ELSE false
        END AS isFree
    FROM
        salle s
    LEFT JOIN
        theme t ON s.id_salle = t.id_salle
        AND CURRENT_DATE BETWEEN t.date_debut AND t.date_fin
    LEFT JOIN
        reservation r ON s.id_salle = r.id_salle
        AND CURRENT_DATE = r.date_reservee
        AND CURRENT_TIMESTAMP BETWEEN r.heure_debut AND r.heure_fin
    ORDER BY
        s.id_salle,
        t.id_theme;

    CREATE VIEW v_profit AS
    WITH date_series AS (
        SELECT
            generate_series('2023-01-01'::date, CURRENT_DATE, '1 day') AS date_action
    )
    SELECT
        EXTRACT(EPOCH FROM d.date_action) * 1000 AS date_action,
        COALESCE(SUM(h.montant_entrant), 0) AS montant_total_par_jour
    FROM
        date_series d
            LEFT JOIN
        historique h ON d.date_action = h.date_action
    GROUP BY
        d.date_action
    ORDER BY
        d.date_action;



    SELECT id_service, COUNT(*) AS usage_count
    FROM reservation
    WHERE date_reservation BETWEEN '2024-01-01' AND '2024-07-31'
    GROUP BY id_service
    ORDER BY usage_count DESC LIMIT 1;

    WITH chiffre_affaire AS (
        SELECT SUM(montant_entrant) AS total_chiffre_affaire
        FROM historique
        WHERE date_action BETWEEN '2024-01-01' AND '2024-07-31'
    ),
         total_depenses AS (
             SELECT SUM(montant) AS total_depense
             FROM depense
             WHERE date_insertion BETWEEN '2024-01-01' AND '2024-07-31'
         )
    SELECT
        COALESCE(total_chiffre_affaire, 0) AS chiffre_affaire,
        COALESCE(total_depense, 0) AS depense,
        COALESCE(total_chiffre_affaire, 0) - COALESCE(total_depense, 0) AS benefice
    FROM
        chiffre_affaire, total_depenses;

    SELECT * FROM theme where date_debut >= '2023-01-01' AND date_fin <= '2026-01-01';

    CREATE VIEW v_stat_theme AS
    SELECT
        t.id_theme,
        COALESCE(SUM(r.nb_personne), 0) AS total_personnes,
        COUNT(r.id_reservation) AS visit
    FROM
        theme t
            LEFT JOIN
        reservation r ON t.id_theme = r.id_theme AND r.isConfirmed = true
    GROUP BY
        t.id_theme
    ORDER BY
        visit DESC;


    SELECT
        t.id_theme,
        COALESCE(SUM(r.nb_personne), 0) AS total_personnes,
        COUNT(r.id_reservation) AS visit
    FROM
        theme t
            LEFT JOIN
        reservation r ON t.id_theme = r.id_theme AND r.isConfirmed = true AND r.date_reservation BETWEEN '2023-01-01' AND '2023-12-31' WHERE t.id_theme = 'TH_32'
    GROUP BY
        t.id_theme
    ORDER BY
        visit DESC;


    SELECT
        ct.intitule AS categorie_theme,
        COUNT(r.id_reservation) AS nombre_reservations
    FROM
        reservation r
            JOIN
        theme t ON r.id_theme = t.id_theme
            JOIN
        categorie_theme ct ON t.id_categorie_theme = ct.id_categorie_theme
    WHERE
        EXTRACT(MONTH FROM r.heure_debut) = 6
      AND EXTRACT(YEAR FROM r.heure_debut) = 2024
    GROUP BY
        ct.intitule
    ORDER BY
        nombre_reservations DESC;

    CREATE VIEW v_tarif_service AS select vr.range_label , tcs.prix , cs.id_comp_service from tarif_comp_service tcs JOIN comp_service cs on tcs.id_comp_service = cs.id_comp_service JOIN value_ranges vr on tcs.id_value_ranges = vr.id;