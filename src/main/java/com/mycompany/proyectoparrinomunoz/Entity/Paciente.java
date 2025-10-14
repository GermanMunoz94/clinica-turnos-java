/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectoparrinomunoz.Entity;

public class Paciente {

    private int id_paciente;
    private String nombre;
    private String apellido;
    private String dni;
    private String email;
    private String telefono;

    public Paciente(int idPaciente, String nombre, String apellido, String dni, String email, String telefono) {
        this.id_paciente = idPaciente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.email = email;
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return apellido + ", " + nombre;
    }

    public int getIdPaciente() {
        return id_paciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.id_paciente = idPaciente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

}
