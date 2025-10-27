
package com.mycompany.proyectoparrinomunoz.Service;

import com.mycompany.proyectoparrinomunoz.Entity.Medico;
import com.mycompany.proyectoparrinomunoz.Entity.Paciente;
import com.mycompany.proyectoparrinomunoz.Entity.Turno;
import com.mycompany.proyectoparrinomunoz.repositorios.TurnoRepositorio;

import java.util.List;

public class TurnoService {

    private final TurnoRepositorio turnoRepositorio;

    public TurnoService() {
        turnoRepositorio = new TurnoRepositorio();
    }

    // === Crear turno ===
    public boolean crearTurno(Turno t) {
        if (!validarTurno(t)) {
            System.err.println("Error: datos del turno inválidos.");
            return false;
        }

        // Verificar conflicto de horario del médico
        List<Turno> turnosMedico = turnoRepositorio.listarTurnosPorMedico(t.getMedico().getIdMedico());
        boolean conflicto = turnosMedico.stream()
                .anyMatch(existing -> existing.getFecha().equals(t.getFecha())
                        && existing.getHora().equals(t.getHora()));

        if (conflicto) {
            System.err.println("Error: el médico ya tiene un turno en esa fecha y hora.");
            return false;
        }

        return turnoRepositorio.crearTurno(t);
    }

    // === Actualizar turno ===
    public boolean actualizarTurno(Turno t) {
        if (!validarTurno(t) || t.getIdTurno() <= 0) {
            System.err.println("Error: turno no válido para actualización.");
            return false;
        }

        // El repositorio actual no implementa UPDATE, pero se deja preparado
        System.err.println("Advertencia: método actualizarTurno() aún no implementado en el repositorio.");
        return false;
    }

    // === Eliminar turno ===
    public boolean eliminarTurno(int id) {
        if (id <= 0) {
            System.err.println("Error: ID de turno no válido.");
            return false;
        }
        return turnoRepositorio.eliminarTurno(id);
    }

    // === Listar todos los turnos ===
    public List<Turno> listarTurnos() {
        return turnoRepositorio.listarTurnos();
    }

    // === Listar turnos por médico ===
    public List<Turno> listarTurnosPorMedico(int idMedico) {
        if (idMedico <= 0) {
            System.err.println("Error: ID de médico no válido.");
            return List.of();
        }
        return turnoRepositorio.listarTurnosPorMedico(idMedico);
    }

    // === Listar turnos por paciente ===
    public List<Turno> listarTurnosPorPaciente(int idPaciente) {
        if (idPaciente <= 0) {
            System.err.println("Error: ID de paciente no válido.");
            return List.of();
        }
        return turnoRepositorio.listarTurnosPorPaciente(idPaciente);
    }

    // === Validar turno ===
    public boolean validarTurno(Turno t) {
        if (t == null) return false;

        Paciente p = t.getPaciente();
        Medico m = t.getMedico();

        return p != null && m != null &&
                p.getIdPaciente() > 0 &&
                m.getIdMedico() > 0 &&
                t.getFecha() != null && !t.getFecha().isBlank() &&
                t.getHora() != null && !t.getHora().isBlank();
    }
}
