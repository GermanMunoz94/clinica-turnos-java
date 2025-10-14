
package com.mycompany.proyectoparrinomunoz.Service;

import com.mycompany.proyectoparrinomunoz.Entity.Turno;
import com.mycompany.proyectoparrinomunoz.repositorios.MedicoRepositorio;
import com.mycompany.proyectoparrinomunoz.repositorios.PacienteRepositorio;
import com.mycompany.proyectoparrinomunoz.repositorios.TurnoRepositorio;
import java.util.List;

public class TurnoService {

    private TurnoRepositorio turnoRepositorio;
    private PacienteRepositorio pacienteRepositorio;
    private MedicoRepositorio medicoRepositorio;

    public TurnoService() {
        turnoRepositorio = new TurnoRepositorio();
        pacienteRepositorio = new PacienteRepositorio();
        medicoRepositorio = new MedicoRepositorio();
    }

    // CREAR TURNO
    public boolean crearTurno(Turno turno) {
        // Validaciones básicas
        if (turno.getPaciente() == null || turno.getMedico() == null) return false;
        if (turno.getFecha() == null || turno.getFecha().isEmpty()) return false;
        if (turno.getHora() == null || turno.getHora().isEmpty()) return false;

        // Validar que paciente y medico existen
        if (pacienteRepositorio.obtenerPorId(turno.getPaciente().getIdPaciente()) == null) return false;
        if (medicoRepositorio.obtenerPorId(turno.getMedico().getIdMedico()) == null) return false;

        return turnoRepositorio.crearTurno(turno);
    }

    // OBTENER TURNO POR ID
    public Turno obtenerTurnoPorId(int idTurno) {
        return turnoRepositorio.obtenerPorId(idTurno);
    }

    // OBTENER TODOS LOS TURNOS
    public List<Turno> obtenerTodos() {
        return turnoRepositorio.obtenerTodos();
    }

    // ACTUALIZAR TURNO
    public boolean actualizarTurno(Turno turno) {
        if (turno.getIdTurno() <= 0) return false;
        return turnoRepositorio.actualizarTurno(turno);
    }

    // ELIMINAR TURNO
    public boolean eliminarTurno(int idTurno) {
        if (idTurno <= 0) return false;
        return turnoRepositorio.eliminarTurno(idTurno);
    }

    // BUSCAR TURNOS POR PACIENTE
    public List<Turno> obtenerTurnosPorPaciente(int idPaciente) {
        return turnoRepositorio.obtenerPorPaciente(idPaciente);
    }

    // BUSCAR TURNOS POR MÉDICO
    public List<Turno> obtenerTurnosPorMedico(int idMedico) {
        List<Turno> todos = turnoRepositorio.obtenerTodos();
        return todos.stream().filter(t -> t.getMedico().getIdMedico() == idMedico).toList();
    }
}

