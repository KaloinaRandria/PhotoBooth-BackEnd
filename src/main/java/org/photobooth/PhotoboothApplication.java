package org.photobooth;

import org.photobooth.restapi.dao.DataAccess;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Random;

@SpringBootApplication
public class PhotoboothApplication {
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		SpringApplication.run(PhotoboothApplication.class, args);
		//Connection co = DataAccess.getConnection();
		//insererDonnees(co);
	}

	public static void insererDonnees(Connection connection) throws SQLException {
		// Vérifier que la connexion n'est pas nulle
		if (connection == null) {
			throw new IllegalArgumentException("La connexion à la base de données est nulle.");
		}

		// Préparer la requête SQL pour l'insertion
		String sql = "INSERT INTO historique (id_historique, id_theme, date_action, date_debut, date_fin, montant_entrant) " +
				"VALUES (?, ?, ?, ?, ?, ?)";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			// Définir la date de début (1er janvier 2022)
			LocalDate startDate = LocalDate.of(2023, 1, 1);
			LocalDate currentDate = startDate;
			LocalDate endDate = LocalDate.now();
			Random random = new Random();

			// Boucler sur chaque jour depuis le début jusqu'à aujourd'hui
			int i = 0;
			while (!currentDate.isAfter(endDate)) {
				// Générer un montant aléatoire entre 200000 et 500000
				double montant = 200000 + random.nextDouble() * (300000 - 200000);

				// Générer des valeurs aléatoires pour id_historique et id_theme (pour l'exemple)
				String idHistorique = "HIST-" + random.nextInt(1000);
				String idTheme = "Theme-" + random.nextInt(10);

				// Définir les paramètres de l'instruction préparée
				statement.setString(1, idHistorique + i);
				statement.setString(2, idTheme);
				statement.setDate(3, Date.valueOf(currentDate));
				statement.setTimestamp(4, Timestamp.valueOf(LocalDateTime.of(currentDate, LocalTime.of(8, 0))));
				statement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.of(currentDate, LocalTime.of(17, 0))));
				statement.setDouble(6, montant);

				// Exécuter l'instruction d'insertion
				statement.executeUpdate();

				// Passer au jour suivant
				currentDate = currentDate.plusDays(1);
				i++;
			}
			System.out.println("Insertion de données terminée avec succès !");
		} catch (SQLException e) {
			throw new SQLException("Erreur lors de l'insertion des données : " + e.getMessage());
		}
	}

}
