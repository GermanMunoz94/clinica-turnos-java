package com.mycompany.proyectoparrinomunoz.Controller;

import com.mycompany.proyectoparrinomunoz.Entity.Medico;
import com.mycompany.proyectoparrinomunoz.Entity.Paciente;
import com.mycompany.proyectoparrinomunoz.Entity.Turno;
import com.mycompany.proyectoparrinomunoz.Service.TurnoService;

import java.util.List;

public class TurnoController {

    private final TurnoService turnoService;
    private final PacienteController pacienteController;
    private final MedicoController medicoController;

    public TurnoController() {
        turnoService = new TurnoService();
        pacienteController = new PacienteController();
        medicoController = new MedicoController();
    }

    // === Crear turno ===
    public boolean crearTurno(Turno t) {
        if (t == null || t.getPaciente() == null || t.getMedico() == null ||
                t.getFecha() == null || t.getHora() == null ||
                t.getFecha().isBlank() || t.getHora().isBlank()) {
            System.err.println("Error: datos del turno incompletos o inválidos.");
            return false;
        }

        try {
            return turnoService.crearTurno(t);
        } catch (Exception e) {
            System.err.println("Error en crearTurno(): " + e.getMessage());
            return false;
        }
    }

    // === Eliminar turno ===
    public boolean eliminarTurno(int id) {
        try {
            return turnoService.eliminarTurno(id);
        } catch (Exception e) {
            System.err.println("Error en eliminarTurno(): " + e.getMessage());
            return false;
        }
    }

    // === Listar todos los turnos ===
    public List<Turno> listarTurnos() {
        try {
            return turnoService.listarTurnos();
        } catch (Exception e) {
            System.err.println("Error en listarTurnos(): " + e.getMessage());
            return List.of();
        }
    }

    // === Listar turnos por médico ===
    public List<Turno> listarTurnosPorMedico(int idMedico) {
        try {
            return turnoService.listarTurnosPorMedico(idMedico);
        } catch (Exception e) {
            System.err.println("Error en listarTurnosPorMedico(): " + e.getMessage());
            return List.of();
        }
    }

    // === Listar turnos por paciente ===
    public List<Turno> listarTurnosPorPaciente(int idPaciente) {
        try {
            return turnoService.listarTurnosPorPaciente(idPaciente);
        } catch (Exception e) {
            System.err.println("Error en listarTurnosPorPaciente(): " + e.getMessage());
            return List.of();
        }
    }

    // === Listar pacientes disponibles ===
    public List<Paciente> listarPacientes() {
        return pacienteController.listarPacientes();
    }

    // === Listar médicos disponibles ===
    public List<Medico> listarMedicos() {
        return medicoController.listarMedicos();
    }

    // === Actualizar turno ===
    public boolean actualizarTurno(Turno turno) {
        if (turno == null || turno.getIdTurno() <= 0) {
            System.err.println("Error: turno inválido para actualizar.");
            return false;
        }

        try {
            return turnoService.actualizarTurno(turno);
        } catch (Exception e) {
            System.err.println("Error en actualizarTurno(): " + e.getMessage());
            return false;
        }

    }
}
