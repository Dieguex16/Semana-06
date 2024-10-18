/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Estudiante;
import Modelo.EstudianteDAO;
import Vista.IRegistro;
import java.util.Calendar;
import javax.swing.*;
import java.util.Date;

/**
 *
 * @author dgarc
 */
public class Controlador {

    private IRegistro vista;
    private EstudianteDAO gestor;

    public Controlador(IRegistro vista, EstudianteDAO gestor) {
        this.vista = vista;
        this.gestor = gestor;

        // Inicializar eventos
        this.vista.getBtnGuardar().addActionListener(e -> guardarEstudiante());
        this.vista.getBtnCargar().addActionListener(e -> cargarEstudiantes());
        this.vista.getBtnModificar().addActionListener(e -> modificarEstudiante());
        this.vista.getBtnEliminar().addActionListener(e -> eliminarEstudiante());
        
        // Evento para seleccionar una fila de la tabla y cargar los datos en los campos
        this.vista.getTabla().getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) { // Evitar que se ejecute dos veces
                cargarDatosSeleccionados();
            }
        });
    }

    public void guardarEstudiante() {
        // Validar campos
        if (camposVacios()) {
            JOptionPane.showMessageDialog(vista, "Por favor, complete todos los campos.");
            return; // Salir si hay campos vacíos
        }

        // Crear un nuevo estudiante
        Estudiante estudiante = new Estudiante(
                vista.getTxtCodigo().getText(),
                vista.getTxtNombre().getText(),
                vista.getTxtApPaterno().getText(),
                vista.getTxtApMaterno().getText(),
                vista.getTxtDireccion().getText(),
                calcularEdad(vista.getDateChooserEdad().getDate())
        );

        // Guardar el estudiante en la base de datos, si no existe
        gestor.agregarEstudiante(estudiante); // Este método ya verifica duplicados
        
        // Limpiar los campos del formulario
        vista.limpiarCampos();
    }
    


    public void cargarEstudiantes() {
        gestor.cargarEstudiantesDesdeBaseDeDatos(); // Cargar los datos y actualizar la tabla en la vista
    }

    private boolean camposVacios() {
        return vista.getTxtNombre().getText().isEmpty()
                || vista.getTxtApPaterno().getText().isEmpty()
                || vista.getTxtApMaterno().getText().isEmpty()
                || vista.getTxtDireccion().getText().isEmpty()
                || vista.getDateChooserEdad().getDate() == null;
    }

    private int calcularEdad(Date fechaNacimiento) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(fechaNacimiento);
        int añoNacimiento = cal.get(java.util.Calendar.YEAR);
        int añoActual = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        return añoActual - añoNacimiento;
    }
    
    // Métodos para modificar y eliminar estudiantes
    public void modificarEstudiante() {
        int filaSeleccionada = vista.getTabla().getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccione un estudiante para modificar.");
            return;
        }

        // Obtener datos del estudiante seleccionado
        String codigo = vista.getTabla().getValueAt(filaSeleccionada, 0).toString();

        // Validar campos modificados
        if (camposVacios()) {
            JOptionPane.showMessageDialog(vista, "Por favor, complete todos los campos.");
            return;
        }

        // Crear el estudiante con los nuevos datos
        Estudiante estudianteModificado = new Estudiante(
                codigo,
                vista.getTxtNombre().getText(),
                vista.getTxtApPaterno().getText(),
                vista.getTxtApMaterno().getText(),
                vista.getTxtDireccion().getText(),
                calcularEdad(vista.getDateChooserEdad().getDate())
        );

        // Actualizar en la base de datos
        gestor.modificarEstudiante(estudianteModificado);

        // Limpiar campos y actualizar tabla
        vista.limpiarCampos();
        cargarEstudiantes();
    }

    public void eliminarEstudiante() {
        int filaSeleccionada = vista.getTabla().getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccione un estudiante para eliminar.");
            return;
        }

        // Obtener el código del estudiante seleccionado
        String codigo = vista.getTabla().getValueAt(filaSeleccionada, 0).toString();

        // Confirmar eliminación
        int confirmacion = JOptionPane.showConfirmDialog(vista, "¿Está seguro de eliminar este estudiante?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            // Eliminar el estudiante de la base de datos
            gestor.eliminarEstudiante(codigo);

            // Limpiar campos y actualizar tabla
            vista.limpiarCampos();
            cargarEstudiantes();
        }
    }
    
    private void cargarDatosSeleccionados() {
        int filaSeleccionada = vista.getTabla().getSelectedRow();

        if (filaSeleccionada != -1) {
            String codigo = vista.getTabla().getValueAt(filaSeleccionada, 0).toString();
            String nombre = vista.getTabla().getValueAt(filaSeleccionada, 1).toString();
            String apellidoP = vista.getTabla().getValueAt(filaSeleccionada, 2).toString();
            String apellidoM = vista.getTabla().getValueAt(filaSeleccionada, 3).toString();
            String direccion = vista.getTabla().getValueAt(filaSeleccionada, 4).toString();
            int edad = Integer.parseInt(vista.getTabla().getValueAt(filaSeleccionada, 5).toString());

            // Calcular la fecha de nacimiento desde la edad
            Date fechaNacimiento = calcularFechaNacimiento(edad);

            // Cargar datos en los campos
            vista.getTxtCodigo().setText(codigo);
            vista.getTxtNombre().setText(nombre);
            vista.getTxtApPaterno().setText(apellidoP);
            vista.getTxtApMaterno().setText(apellidoM);
            vista.getTxtDireccion().setText(direccion);
            vista.getDateChooserEdad().setDate(fechaNacimiento); // Cargar la fecha de nacimiento
        }
    }

    private Date calcularFechaNacimiento(int edad) {
        Calendar cal = Calendar.getInstance();
        // Establecer el año a partir de la edad
        cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - edad);
        // Mantener el mes y el día actuales
        return cal.getTime();
    }

}
