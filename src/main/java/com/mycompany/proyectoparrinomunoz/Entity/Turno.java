package com.mycompany.proyectoparrinomunoz.Entity;

public class Turno {

    private int idTurno;
    private Paciente paciente;
    private Medico medico;
    private String fecha;
    private String hora;

    // Constructores
    public Turno() {
    }

    public Turno(int idTurno, Paciente paciente, Medico medico, String fecha, String hora) {
        this.idTurno = idTurno;
        this.paciente = paciente;
        this.medico = medico;
        this.fecha = fecha;
        this.hora = hora;
    }

    // Getters y setters
    public int getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(int idTurno) {
        this.idTurno = idTurno;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public String getFecha() {
        return fecha != null ? fecha : "";
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora != null ? hora : "";
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    @Override
    public String toString() {
        String pacienteNombre = (paciente != null) ? paciente.getNombre() : "Paciente no asignado";
        String medicoNombre = (medico != null) ? medico.getNombre() : "Médico no asignado";
        return String.format("%s con %s - %s %s", pacienteNombre, medicoNombre, fecha, hora);
    }
}
