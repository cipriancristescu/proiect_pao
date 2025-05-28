package service;

import database.DatabaseConnection;
import model.Angajat;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AngajatDAO {

    public void createTable() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS angajat (
                    id TEXT PRIMARY KEY,
                    nume TEXT NOT NULL,
                    telefon TEXT,
                    functie TEXT,
                    salariu REAL
                )
            """);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void adaugaAngajat(Angajat a) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO angajat(id, nume, telefon, functie, salariu) VALUES (?, ?, ?, ?, ?)")) {

            stmt.setString(1, a.getId());
            stmt.setString(2, a.getNume());
            stmt.setString(3, a.getTelefon());
            stmt.setString(4, a.getFunctie());
            stmt.setDouble(5, a.getSalariu());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Angajat> getAngajati() {
        List<Angajat> lista = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM angajat")) {

            while (rs.next()) {
                Angajat a = new Angajat(
                        rs.getString("id"),
                        rs.getString("nume"),
                        rs.getString("telefon"),
                        rs.getString("functie"),
                        rs.getDouble("salariu")
                );
                lista.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
