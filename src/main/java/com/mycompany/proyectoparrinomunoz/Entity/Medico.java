package com.mycompany.proyectoparrinomunoz.Entity;

public class Medico {

    private int idMedico;
    private String nombre;
    private String apellido;
    private String especialidad;
    private String telefono;

    // === Constructores ===
    public Medico() {}

    public Medico(int idMedico, String nombre, String apellido, String especialidad, String telefono) {
        this.idMedico = idMedico;
        this.nombre = nombre;
        this.apellido = apellido;
        this.especialidad = especialidad;
        this.telefono = telefono;
    }

    public Medico(String nombre, String apellido, String especialidad, String telefono) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.especialidad = especialidad;
        this.telefono = telefono;
    }

    // === Getters y Setters ===
    public int getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
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

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    // === Representación en texto (para JComboBox, logs, etc.) ===
    @Override
    public String toString() {
        return nombre + " " + apellido + " (" + especialidad + ")";
    }
}
