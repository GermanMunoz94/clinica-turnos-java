package com.mycompany.proyectoparrinomunoz.Entity;

import java.time.LocalDateTime;

public class Diagnostico {
    private int idDiagnostico;
    private Turno turno;
    private LocalDateTime fecha;
    private String descripcion;
    private String receta;

    public Diagnostico(int idDiagnostico, Turno turno, LocalDateTime fecha, String descripcion, String receta) {
        this.idDiagnostico = idDiagnostico;
        this.turno = turno;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.receta = receta;
    }

    public Diagnostico() {}

    public int getIdDiagnostico() {
        return idDiagnostico;
    }

    public void setIdDiagnostico(int idDiagnostico) {
        this.idDiagnostico = idDiagnostico;
    }

    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getReceta() {
        return receta;
    }

    public void setReceta(String receta) {
        this.receta = receta;
    }

    @Override
    public String toString() {
        return "Diagnóstico del " + fecha +
                " | Paciente: " + (turno != null ? turno.getPaciente().getNombre() : "N/A") +
                " | Descripción: " + descripcion;
    }
}
