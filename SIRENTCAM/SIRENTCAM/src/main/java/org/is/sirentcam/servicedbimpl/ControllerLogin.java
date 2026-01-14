/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.is.sirentcam.servicedbimpl;
import org.is.sirentcam.connectiondb.ConnectionManager;
import org.is.sirentcam.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Nazril
 */
public class ControllerLogin {
    ConnectionManager cm = new ConnectionManager();

    public boolean login(User user) {
        boolean hasil = false;
        Connection conn = cm.connectDb();

        try {
            String sql = "SELECT * FROM user WHERE username=? AND password=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                hasil = true;
            }
        } catch (Exception e) {
            System.err.println("Error login: " + e.getMessage());
        } finally {
            cm.disconnectDb(conn);
        }

        return hasil;
    }
	
	public boolean regist(User user){
		boolean hasil = false;
        Connection conn = cm.connectDb();
        
        String sql = "INSERT INTO user (username, password) VALUES (?, ?)";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            
            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                hasil = true;
            }
            
            ps.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Gagal Registrasi: " + e.getMessage());
        }
        
        return hasil;
	}
	
        public int update(String oldUsername, User user) {
            // Pastikan nama tabel dan kolom di database sudah sesuai (case-sensitive di beberapa OS)
            String sql = "UPDATE user SET username = ?, password = ? WHERE username = ?";
            int rowsAffected = 0;

            try (Connection conn = cm.connectDb()) {
                if (conn == null) {
                    System.out.println("Error: Koneksi database gagal (null)");
                    return 0;
                }

                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    // Menggunakan .trim() untuk menghindari spasi yang tidak sengaja terinput
                    ps.setString(1, user.getUsername().trim());
                    ps.setString(2, user.getPassword());
                    ps.setString(3, oldUsername.trim());

                    rowsAffected = ps.executeUpdate();

                    if (rowsAffected == 0) {
                        System.out.println("Peringatan: Tidak ada data yang diupdate untuk username: " + oldUsername);
                    } else {
                        System.out.println("Berhasil mengupdate " + rowsAffected + " baris.");
                    }
                }
            } catch (SQLException e) {
                System.err.println("SQL Error: " + e.getMessage());
                e.printStackTrace();
            }
            return rowsAffected;
        }
	
	public List<User> findAll() {
        List<User> listAkun = new ArrayList<>();
        String sql = "SELECT * FROM user";
        try (Connection conn = cm.connectDb();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                listAkun.add(new User(rs.getString("username"), rs.getString("password")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listAkun;
    }
	
	public boolean delete(String username) {
        String sql = "DELETE FROM user WHERE username = ?";
        try (Connection conn = cm.connectDb();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
