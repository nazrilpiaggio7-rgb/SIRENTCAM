/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.is.sirentcam.model;

/**
 *
 * @author Nazril
 */
public class Kamera {
    private int id_kamera;
    private String nama_kamera;
    private double harga;
	private String status;
	
    
    public Kamera() {
    }

    public Kamera(int id_kamera, String nama_kamera, double harga) {
        this.id_kamera = id_kamera;
        this.nama_kamera = nama_kamera;
        this.harga = harga;
    }

    public int getId_kamera() {
        return id_kamera;
    }

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	

    public void setId_kamera(int id_kamera) {
        this.id_kamera = id_kamera;
    }

    public String getNama_kamera() {
        return nama_kamera;
    }

    public void setNama_kamera(String nama_kamera) {
        this.nama_kamera = nama_kamera;
    }


    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }
}
