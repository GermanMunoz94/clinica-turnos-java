package com.mycompany.proyectoparrinomunoz.Entity;

import java.time.LocalDate;

public class Diagnostico {

    private int idDiagnostico;
    private Paciente paciente;
    private Medico medico;
    private Turno turno; // ← nuevo
    private String descripcion;
    private String receta;
    private LocalDate fecha;

    // === Constructores ===
    public Diagnostico() {
        this.fecha = LocalDate.now();
    }

    public Diagnostico(int idDiagnostico, Paciente paciente, Medico medico, String descripcion) {
        this.idDiagnostico = idDiagnostico;
        this.paciente = paciente;
        this.medico = medico;
        this.descripcion = descripcion;
        this.receta = "";
        this.fecha = LocalDate.now();
    }

    public Diagnostico(int idDiagnostico, Paciente paciente, Medico medico, Turno turno, String descripcion, String receta, LocalDate fecha) {
        this.idDiagnostico = idDiagnostico;
        this.paciente = paciente;
        this.medico = medico;
        this.turno = turno;
        this.descripcion = descripcion;
        this.receta = receta;
        this.fecha = fecha != null ? fecha : LocalDate.now();
    }

    // === Getters y Setters ===
    public int getIdDiagnostico() { return idDiagnostico; }
    public void setIdDiagnostico(int idDiagnostico) { this.idDiagnostico = idDiagnostico; }

    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }

    public Medico getMedico() { return medico; }
    public void setMedico(Medico medico) { this.medico = medico; }

    public Turno getTurno() { return turno; }
    public void setTurno(Turno turno) { this.turno = turno; }

    public String getDescripcion() { return descripcion != null ? descripcion : ""; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getReceta() { return receta != null ? receta : ""; }
    public void setReceta(String receta) { this.receta = receta; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    // === Representación en texto ===
    @Override
    public String toString() {
        String pacienteNombre = (paciente != null) ? paciente.getNombre() : "Paciente no asignado";
        String medicoNombre = (medico != null) ? medico.getNombre() : "Médico no asignado";
        String turnoTexto = (turno != null) ? turno.getFecha() + " " + turno.getHora() : "Sin turno";
        return String.format("%s - %s [%s]: %s", pacienteNombre, medicoNombre, turnoTexto, descripcion);
    }
}
