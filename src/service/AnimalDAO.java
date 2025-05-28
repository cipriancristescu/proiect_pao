package service;

import database.DatabaseConnection;
import model.Animal;
import model.Caine;
import model.Pisica;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnimalDAO {

    public void createTable() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS animal (
                    id TEXT PRIMARY KEY,
                    nume TEXT NOT NULL,
                    varsta INTEGER,
                    specie TEXT,
                    rasa TEXT,
                    stare_sanatate TEXT,
                    este_vaccinat INTEGER
                )
            """);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void adaugaAnimal(Animal a) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO animal(id, nume, varsta, specie, rasa, stare_sanatate, este_vaccinat) VALUES (?, ?, ?, ?, ?, ?, ?)")
        ) {
            stmt.setString(1, a.getId());
            stmt.setString(2, a.getNume());
            stmt.setInt(3, a.getVarsta());
            stmt.setString(4, a.getSpecie());

            String rasa = null;
            if (a instanceof Caine) {
                rasa = ((Caine) a).getRasa();
            } else if (a instanceof Pisica) {
                rasa = ((Pisica) a).getRasa();
            }

            stmt.setString(5, rasa);
            stmt.setString(6, a.getStareSanatate());
            stmt.setInt(7, a.isEsteVaccinat() ? 1 : 0);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Animal> getAnimale() {
        List<Animal> lista = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM animal")) {

            while (rs.next()) {
                String id = rs.getString("id");
                String nume = rs.getString("nume");
                int varsta = rs.getInt("varsta");
                String specie = rs.getString("specie");
                String rasa = rs.getString("rasa");
                String stareSanatate = rs.getString("stare_sanatate");
                boolean esteVaccinat = rs.getInt("este_vaccinat") == 1;

                Animal a = null;
                if ("Caine".equals(specie)) {
                    Caine caine = new Caine(id, nume, varsta, rasa);
                    caine.setStareSanatate(stareSanatate);
                    caine.setEsteVaccinat(esteVaccinat);
                    a = caine;
                } else if ("Pisica".equals(specie)) {
                    Pisica pisica = new Pisica(id, nume, varsta, rasa);
                    pisica.setStareSanatate(stareSanatate);
                    pisica.setEsteVaccinat(esteVaccinat);
                    a = pisica;
                }

                if (a != null) {
                    lista.add(a);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
