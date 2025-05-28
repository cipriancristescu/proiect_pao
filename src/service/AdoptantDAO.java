package service;

import database.DatabaseConnection;
import model.Adoptant;

import java.sql.*;

public class AdoptantDAO {

    public void createTable() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS adoptant (
                    id TEXT PRIMARY KEY,
                    nume TEXT NOT NULL,
                    telefon TEXT,
                    adresa TEXT
                )
            """);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void adaugaAdoptant(Adoptant a) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO adoptant(id, nume, telefon, adresa) VALUES (?, ?, ?, ?)")) {

            stmt.setString(1, a.getId());
            stmt.setString(2, a.getNume());
            stmt.setString(3, a.getTelefon());
            stmt.setString(4, a.getAdresa());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Adoptant getAdoptantById(String id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM adoptant WHERE id = ?")) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Adoptant(
                        rs.getString("id"),
                        rs.getString("nume"),
                        rs.getString("telefon"),
                        rs.getString("adresa")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
