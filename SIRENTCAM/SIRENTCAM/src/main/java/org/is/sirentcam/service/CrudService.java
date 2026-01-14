/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.is.sirentcam.service;

import java.util.List;

/**
 *
 * @author Nazril
 */
public interface CrudService<T> {
    void create(T object);
    T findOne(int id_Peminjaman);
    List<T> findAll();
    void update(int id_Peminjaman, T object);
    void updatePs(int id_Peminjaman, T object);
    boolean delete(int id_Peminjaman);
}

