/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author dgarc
 */
public class ConexionSQL {

    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=DBEjemplo;encrypt=true;trustServerCertificate=true;"; // Cambia por la URL de tu base de datos
    private static final String USER = "sa"; // Cambia por tu usuario
    private static final String PASSWORD = "1010"; // Cambia por tu contraseña

    public static Connection getConnection() throws SQLException {
        // Cargar el controlador SQL Server
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se pudo encontrar el controlador SQL Server.", e);
        }

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
