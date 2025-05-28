package service;

import database.DatabaseConnection;
import model.Adoptie;
import model.Animal;
import model.Adoptant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AdoptieDAO {

    public void createTable() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS adoptie (
                    id TEXT PRIMARY KEY,
                    id_animal TEXT,
                    id_adoptant TEXT,
                    data_adoptie TEXT,
                    FOREIGN KEY (id_animal) REFERENCES animal(id),
                    FOREIGN KEY (id_adoptant) REFERENCES adoptant(id)
                )
            """);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void adaugaAdoptie(Adoptie adoptie) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO adoptie(id, id_animal, id_adoptant, data_adoptie) VALUES (?, ?, ?, ?)")) {

            stmt.setString(1, adoptie.getId());
            stmt.setString(2, adoptie.getAnimal().getId());
            stmt.setString(3, adoptie.getAdoptant().getId());
            stmt.setString(4, adoptie.getDataAdoptie().toString());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Adoptie> getAdoptii() {
        List<Adoptie> lista = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM adoptie")) {

            while (rs.next()) {
                String id = rs.getString("id");
                String idAnimal = rs.getString("id_animal");
                String idAdoptant = rs.getString("id_adoptant");
                LocalDate dataAdoptie = LocalDate.parse(rs.getString("data_adoptie"));

                // In acest punct, trebuie sa incarcam Animal si Adoptant dupa ID din alte DAO-uri (nu e inclus aici)
                Animal animal = null; // trebuie implementat getAnimalById(idAnimal)
                Adoptant adoptant = null; // trebuie implementat getAdoptantById(idAdoptant)

                Adoptie a = new Adoptie(id, animal, adoptant);
                lista.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
