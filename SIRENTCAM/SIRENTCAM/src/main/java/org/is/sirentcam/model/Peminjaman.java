package org.is.sirentcam.model;

/**
 * @author Nazril
 */
public class Peminjaman {
    private int id_sewa;          
    private String nama_penyewa;
    private String no_telp;    
    private String alamat;        
    private int id_kamera;        
    private String tgl_sewa;      
    private String tgl_kembali; 
	private int durasi;
    private String status;
	private double denda;
    private double total_biaya;   

    public Peminjaman() {
    }

	public Peminjaman(int id_sewa, String nama_penyewa, String no_telp, String alamat, 
                      int id_kamera, String tgl_sewa, String tgl_kembali,int durasi, 
                      String status, double denda, double total_biaya) {
        this.id_sewa = id_sewa;
        this.nama_penyewa = nama_penyewa;
        this.no_telp = no_telp;
        this.alamat = alamat;
        this.id_kamera = id_kamera;
        this.tgl_sewa = tgl_sewa;
        this.tgl_kembali = tgl_kembali;
		this.durasi = durasi;
        this.status = status;
        this.denda = denda;
        this.total_biaya = total_biaya;
    }


    public int getId_sewa() {
        return id_sewa;
    }

	public int getDurasi() {
		return durasi;
	}

	public void setDurasi(int durasi) {
		this.durasi = durasi;
	}

	
    public void setId_sewa(int id_sewa) {
        this.id_sewa = id_sewa;
    }

    public String getNama_penyewa() {
        return nama_penyewa;
    }

    public void setNama_penyewa(String nama_penyewa) {
        this.nama_penyewa = nama_penyewa;
    }

    public String getNo_telp() {
        return no_telp;
    }

    public void setNo_telp(String no_telp) {
        this.no_telp = no_telp;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public int getId_kamera() {
        return id_kamera;
    }

	public double getDenda() {
		return denda;
	}

	public void setDenda(double denda) {
		this.denda = denda;
	}
	
	

    public void setId_kamera(int id_kamera) {
        this.id_kamera = id_kamera;
    }

    public String getTgl_sewa() {
        return tgl_sewa;
    }

    public void setTgl_sewa(String tgl_sewa) {
        this.tgl_sewa = tgl_sewa;
    }

    public String getTgl_kembali() {
        return tgl_kembali;
    }

    public void setTgl_kembali(String tgl_kembali) {
        this.tgl_kembali = tgl_kembali;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotal_biaya() {
        return total_biaya;
    }

    public void setTotal_biaya(double total_biaya) {
        this.total_biaya = total_biaya;
    }
}