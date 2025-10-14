package com.mycompany.proyectoparrinomunoz.Entity;

public class Turno {

    private int idTurno;
    private String fecha;
    private String hora;
    private Paciente paciente;
    private Medico medico;

    public Turno(int idTurno, Paciente paciente, Medico medico, String fecha, String hora) {
        this.idTurno = idTurno;
        this.paciente = paciente;
        this.medico = medico;
        this.fecha = fecha;
        this.hora = hora;
    }

    @Override
    public String toString() {
        return "Turno " + idTurno + " | " + fecha + " " + hora
                + " | Paciente: " + paciente.getNombre() + " " + paciente.getApellido()
                + " | Médico: " + medico.getNombre() + " " + medico.getApellido();
    }

    // Getters y Setters
    public int getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(int idTurno) {
        this.idTurno = idTurno;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
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
}

