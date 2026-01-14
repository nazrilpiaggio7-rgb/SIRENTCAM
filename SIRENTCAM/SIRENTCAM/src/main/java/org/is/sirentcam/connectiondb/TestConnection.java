/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.is.sirentcam.connectiondb;

import java.sql.Connection;

/**
 *
 * @author Nazril
 */
public class TestConnection {
    public static void main(String[] args) {
        // Create DatabaseConnection instance
        ConnectionManager connMan = new ConnectionManager();

         //Connect to the database
        Connection conn = connMan.connectDb();

        // Disconnect from the database
        connMan.disconnectDb(conn);
    }
}

