/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.io.Serializable;

/**
 *
 * @author dgarc
 */
public class Estudiante implements Serializable {

    private String codigo;
    private String nombre;
    private String apellidom;
    private String apellidop;
    private String direccion;
    private int edad;

    // Constructor por defecto
    public Estudiante() {
        this.codigo = "";
        this.nombre = "";
        this.apellidom = "";
        this.apellidop = "";
        this.direccion = "";
        this.edad = 0;
    }

    public Estudiante(String codigo, String nombre, String apellidom, String apellidop, String direccion, int edad) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.apellidom = apellidom;
        this.apellidop = apellidop;
        this.direccion = direccion;
        this.edad = edad;
    }

    // Getters y Setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidom() {
        return apellidom;
    }

    public void setApellidom(String apellidom) {
        this.apellidom = apellidom;
    }

    public String getApellidop() {
        return apellidop;
    }

    public void setApellidop(String apellidop) {
        this.apellidop = apellidop;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    @Override
    public String toString() {
        return "Estudiante{" + "codigo=" + codigo + ", nombre=" + nombre + ", apellidom=" + apellidom + ", apellidop=" + apellidop + ", direccion=" + direccion + ", edad=" + edad + '}';
    }
}
