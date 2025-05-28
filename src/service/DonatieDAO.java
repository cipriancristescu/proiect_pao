package service;

import database.DatabaseConnection;
import model.Donatie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DonatieDAO {

    public void createTable() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS donatie (
                    id TEXT PRIMARY KEY,
                    tip_donatie TEXT,
                    valoare REAL,
                    donator_nume TEXT
                )
            """);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void adaugaDonatie(Donatie d) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO donatie(id, tip_donatie, valoare, donator_nume) VALUES (?, ?, ?, ?)")) {

            stmt.setString(1, d.getId());
            stmt.setString(2, d.getTipDonatie());
            stmt.setDouble(3, d.getValoare());
            stmt.setString(4, d.getDonatorNume());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Donatie> getDonatii() {
        List<Donatie> lista = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM donatie")) {

            while (rs.next()) {
                Donatie d = new Donatie(
                        rs.getString("id"),
                        rs.getString("tip_donatie"),
                        rs.getDouble("valoare"),
                        rs.getString("donator_nume")
                );
                lista.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
