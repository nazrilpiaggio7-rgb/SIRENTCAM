/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.is.sirentcam.connectiondb;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author Nazril
 */
public class ConnectionManager {
    private Connection con;
    private final String url = "jdbc:mysql://localhost:3306/sirentcam";
    private final String username = "root";
    private final String password = "basdat2025";
    private final String driver = "com.mysql.cj.jdbc.Driver";

public Connection logOn(){
        if(con == null){
            try {
                Class.forName(driver).newInstance();
                con = DriverManager.getConnection(url, username, password);
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return con;
    }
    public Connection connectDb() {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.err.println("Error saat membuka koneksi: " + e.getMessage());
            return null;
        }
    }
    
    public void disconnectDb(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Koneksi berhasil ditutup.");
            } catch (SQLException e) {
                System.err.println("Error saat menutup koneksi: " + e.getMessage());
            }
        }
    }
    
    public Connection logOff(){
        try {
            con.close();
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        return con;
    }
}

