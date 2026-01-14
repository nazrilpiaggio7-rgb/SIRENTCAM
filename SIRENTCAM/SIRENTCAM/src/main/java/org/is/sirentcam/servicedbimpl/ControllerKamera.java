package org.is.sirentcam.servicedbimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.is.sirentcam.connectiondb.ConnectionManager;
import org.is.sirentcam.model.Kamera;
import org.is.sirentcam.service.CrudService;

public class ControllerKamera implements CrudService<Kamera> {

    ConnectionManager cm = new ConnectionManager();

    @Override
    public void create(Kamera object) {
        String sql = "INSERT INTO kamera (nama_kamera, harga, status) VALUES (?, ?, ?)";
        try (Connection conn = cm.connectDb();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, object.getNama_kamera());
            ps.setDouble(2, object.getHarga());
            ps.setString(3, object.getStatus()); 
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Kamera findOne(int id_kamera) {
        Kamera kamera = null;
        String sql = "SELECT * FROM kamera WHERE id_kamera = ?";
        try (Connection conn = cm.connectDb();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id_kamera);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                kamera = new Kamera();
                kamera.setId_kamera(rs.getInt("id_kamera"));
                kamera.setNama_kamera(rs.getString("nama_kamera"));
                kamera.setHarga(rs.getDouble("harga"));
                kamera.setStatus(rs.getString("status")); // Mengambil status dari DB
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return kamera;
    }

    @Override
    public List<Kamera> findAll() {
        List<Kamera> listKamera = new ArrayList<>();
        String sql = "SELECT * FROM kamera";
        try (Connection conn = cm.connectDb();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Kamera kamera = new Kamera();
                kamera.setId_kamera(rs.getInt("id_kamera"));
                kamera.setNama_kamera(rs.getString("nama_kamera"));
                kamera.setHarga(rs.getDouble("harga"));
                kamera.setStatus(rs.getString("status")); // Mengambil status dari DB
                listKamera.add(kamera);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listKamera;
    }

    @Override
    public void update(int id_kamera, Kamera object) {
        // Revisi query Statement untuk menyertakan status
        String sql = "UPDATE kamera SET nama_kamera = '" + object.getNama_kamera() + 
                     "', harga = " + object.getHarga() + 
                     ", status = '" + object.getStatus() + "' WHERE id_kamera = " + id_kamera;
        try (Connection conn = cm.connectDb();
             Statement st = conn.createStatement()) {
            st.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePs(int id_kamera, Kamera object) {
        // Revisi PreparedStatement untuk menyertakan status
        String sql = "UPDATE kamera SET nama_kamera = ?, harga = ?, status = ? WHERE id_kamera = ?";
        try (Connection conn = cm.connectDb();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, object.getNama_kamera());
            ps.setDouble(2, object.getHarga());
            ps.setString(3, object.getStatus()); 
            ps.setInt(4, id_kamera);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean delete(int id_kamera) {
        boolean hasil = false;
        String sql = "DELETE FROM kamera WHERE id_kamera = ?";
        try (Connection conn = cm.connectDb();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id_kamera);
            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                hasil = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hasil;
    }
}