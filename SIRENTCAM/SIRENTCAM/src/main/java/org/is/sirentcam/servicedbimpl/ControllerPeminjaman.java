package org.is.sirentcam.servicedbimpl;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.is.sirentcam.connectiondb.ConnectionManager;
import org.is.sirentcam.model.Peminjaman;
import org.is.sirentcam.service.CrudService;

/**
 * @author Nazril
 */
public class ControllerPeminjaman implements CrudService<Peminjaman> {
    private final ConnectionManager connMan = new ConnectionManager();
	
	
	public ControllerPeminjaman() {
	}
	
	

    @Override
    public void create(Peminjaman peminjam) {
        // Query disesuaikan dengan kolom tabel penyewaan
        String query = "INSERT INTO penyewaan (nama_penyewa, no_telp, alamat, id_kamera, tgl_sewa, "
                     + "tgl_kembali, durasi,status, denda, total_biaya) VALUES (?, ?, ?, ?, ?, ?, ?,?, ?, ?)";

        try (Connection connection = connMan.connectDb();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, peminjam.getNama_penyewa());
            statement.setString(2, peminjam.getNo_telp());
            statement.setString(3, peminjam.getAlamat());
            statement.setInt(4, peminjam.getId_kamera()); 
            statement.setString(5, peminjam.getTgl_sewa());
            statement.setString(6, peminjam.getTgl_kembali());
			 statement.setInt(7, peminjam.getDurasi());
            statement.setString(8, peminjam.getStatus());
            statement.setDouble(9, peminjam.getDenda());
            statement.setDouble(10, peminjam.getTotal_biaya());
            
            statement.executeUpdate();
            System.out.println("Data penyewaan berhasil ditambahkan.");
        } catch (SQLException e) {
            System.err.println("Error saat menambahkan data: " + e.getMessage());
        }
    }

    @Override
    public Peminjaman findOne(int id_sewa) {
        Peminjaman peminjam = null;
        String query = "SELECT * FROM penyewaan WHERE id_sewa = ?";

        try (Connection connection = connMan.connectDb();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id_sewa);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return  new Peminjaman(
						resultSet.getInt("id_sewa"),
						resultSet.getString("nama_penyewa"),
						resultSet.getString("no_telp"),
						resultSet.getString("alamat"),
						resultSet.getInt("id_kamera"),
						resultSet.getString("tgl_sewa"),
						resultSet.getString("tgl_kembali"),
						resultSet.getInt("durasi"),
						resultSet.getString("status"),
						resultSet.getDouble("denda"),
						resultSet.getDouble("total_biaya")
					);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return peminjam;
    }

    @Override
    public List<Peminjaman> findAll() {
        String query = "SELECT * FROM penyewaan";
        List<Peminjaman> peminjamList = new ArrayList<>();

        try (Connection connection = connMan.connectDb();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Peminjaman peminjam =  new Peminjaman(
						resultSet.getInt("id_sewa"),
						resultSet.getString("nama_penyewa"),
						resultSet.getString("no_telp"),
						resultSet.getString("alamat"),
						resultSet.getInt("id_kamera"),
						resultSet.getString("tgl_sewa"),
						resultSet.getString("tgl_kembali"),
						resultSet.getInt("durasi"),
						resultSet.getString("status"),
						resultSet.getDouble("denda"),
						resultSet.getDouble("total_biaya")
					);
                peminjamList.add(peminjam);
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil data: " + e.getMessage());
        }
        return peminjamList;
    }

    @Override
    public void update(int id_sewa, Peminjaman peminjam) {
        String query = "UPDATE penyewaan SET nama_penyewa = ?, no_telp = ?, alamat = ?, id_kamera = ?, "
                     + "durasi = ?, tgl_sewa = ?, tgl_kembali = ?, status = ?, denda = ?, total_biaya = ? "
                     + "WHERE id_sewa = ?";

        try (Connection connection = connMan.connectDb();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, peminjam.getNama_penyewa());
            statement.setString(2, peminjam.getNo_telp());
            statement.setString(3, peminjam.getAlamat());
            statement.setInt(4, peminjam.getId_kamera());
            statement.setInt(5, peminjam.getDurasi()); 
            statement.setString(6, peminjam.getTgl_sewa());
            statement.setString(7, peminjam.getTgl_kembali());
            statement.setString(8, peminjam.getStatus()); //
            statement.setDouble(9, peminjam.getDenda());
            statement.setDouble(10, peminjam.getTotal_biaya());
            statement.setInt(11, id_sewa); 

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Data penyewaan dengan ID " + id_sewa + " berhasil diperbarui.");
            }
        } catch (SQLException e) {
            System.err.println("Error saat memperbarui data: " + e.getMessage());
        }
    }

    @Override
    public boolean delete(int id_sewa) {
        String query = "DELETE FROM penyewaan WHERE id_sewa = ?";
        try (Connection connection = connMan.connectDb();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_sewa);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error saat menghapus data: " + e.getMessage());
            return false;
        }
    }

    @Override
    public void updatePs(int id_sewa, Peminjaman object) {
        update(id_sewa, object);
    }
}