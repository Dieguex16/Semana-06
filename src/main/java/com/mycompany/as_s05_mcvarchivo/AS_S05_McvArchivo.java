/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.as_s05_mcvarchivo;

import Conexion.ConexionSQL;
import Vista.IRegistro;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author dgarc
 */
public class AS_S05_McvArchivo {

    public static void main(String[] args) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new IRegistro().setVisible(true);
            }
        });
        
        
        
        try (Connection conn = ConexionSQL.getConnection()) {
            if (conn != null) {
                System.out.println("Conexión a la base de datos establecida correctamente.");
            } else {
                System.out.println("No se pudo establecer la conexión.");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Imprimir la excepción para diagnosticar problemas
        }
        
    }
}
