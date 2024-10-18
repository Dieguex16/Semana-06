/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Conexion.ConexionSQL;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author dgarc
 */
public class EstudianteDAO {

    private ArrayList<Estudiante> listaEstudiantes;
    private DefaultTableModel modelo;

    public EstudianteDAO(DefaultTableModel modelo) {
        this.listaEstudiantes = new ArrayList<>();
        this.modelo = modelo;
    }

    public void agregarEstudiante(Estudiante estudiante) {
        // Verificar si ya existe el estudiante antes de agregarlo
        if (existeEstudiante(estudiante.getNombre(), estudiante.getApellidop(), estudiante.getApellidom(), estudiante.getDireccion(), estudiante.getEdad())) {
            JOptionPane.showMessageDialog(null, "El estudiante ya está registrado con los mismos datos.");
            return; // Salir si el estudiante ya existe
        }

        // Solo se insertan los campos que no son auto-incrementales
        String sql = "INSERT INTO Estudiante (nombre, apellido_p, apellido_m, direccion, edad) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionSQL.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, estudiante.getNombre());
            stmt.setString(2, estudiante.getApellidop());
            stmt.setString(3, estudiante.getApellidom());
            stmt.setString(4, estudiante.getDireccion());
            stmt.setInt(5, estudiante.getEdad());
            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al agregar el estudiante: " + e.getMessage());
        }
    }

    private boolean existeEstudiante(String nombre, String apellidoP, String apellidoM, String direccion, int edad) {
        try (Connection con = ConexionSQL.getConnection()) {
            String sql = "SELECT COUNT(*) FROM Estudiante WHERE nombre = ? AND apellido_p = ? AND apellido_m = ? AND direccion = ? AND edad = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, apellidoP);
            ps.setString(3, apellidoM);
            ps.setString(4, direccion);
            ps.setInt(5, edad);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al verificar los datos del estudiante: " + e.getMessage());
        }
        return false;
    }

    public void cargarEstudiantesDesdeBaseDeDatos() {
        listaEstudiantes.clear(); // Limpiar la lista actual antes de cargar nuevos datos
        String sql = "SELECT * FROM Estudiante";

        try (Connection conn = ConexionSQL.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Estudiante estudiante = new Estudiante(
                        rs.getString("codigo"),
                        rs.getString("nombre"),
                        rs.getString("apellido_p"),
                        rs.getString("apellido_m"),
                        rs.getString("direccion"),
                        rs.getInt("edad")
                );
                listaEstudiantes.add(estudiante);
            }

            // Actualizar la tabla después de cargar los datos
            actualizarTabla();

            // Mostrar mensaje de éxito
            JOptionPane.showMessageDialog(null, "¡Datos cargados exitosamente!", "Éxito", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            // Mejorar el manejo de errores con un mensaje
            JOptionPane.showMessageDialog(null, "Ocurrió un error al cargar los datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); // Solo para depuración
        }
    }

    public void actualizarTabla() {
        modelo.setRowCount(0); // Limpiar la tabla
        for (Estudiante e : listaEstudiantes) {
            modelo.addRow(new Object[]{e.getCodigo(), e.getNombre(), e.getApellidop(), e.getApellidom(), e.getDireccion(), e.getEdad()});
        }
    }
    
    
    public void modificarEstudiante(Estudiante estudiante) {
        String sql = "UPDATE Estudiante SET nombre = ?, apellido_p = ?, apellido_m = ?, direccion = ?, edad = ? WHERE codigo = ?";

        try (Connection conn = ConexionSQL.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, estudiante.getNombre());
            stmt.setString(2, estudiante.getApellidop());
            stmt.setString(3, estudiante.getApellidom());
            stmt.setString(4, estudiante.getDireccion());
            stmt.setInt(5, estudiante.getEdad());
            stmt.setString(6, estudiante.getCodigo()); // Usamos el código como referencia

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Estudiante modificado exitosamente.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar el estudiante: " + e.getMessage());
        }
    }

    public void eliminarEstudiante(String codigo) {
        String sql = "DELETE FROM Estudiante WHERE codigo = ?";

        try (Connection conn = ConexionSQL.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codigo);

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Estudiante eliminado exitosamente.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el estudiante: " + e.getMessage());
        }
    }
}
