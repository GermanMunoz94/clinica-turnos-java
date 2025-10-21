
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
        this.turnoRepositorio = new TurnoRepositorio();
        this.pacienteRepositorio = new PacienteRepositorio();
        this.medicoRepositorio = new MedicoRepositorio();
    }

    // === CREAR TURNO ===
    public boolean crearTurno(Turno turno) {
        // Validaciones básicas
        if (turno == null) return false;
        if (turno.getPaciente() == null || turno.getMedico() == null) return false;
        if (turno.getFecha() == null || turno.getFecha().isEmpty()) return false;
        if (turno.getHora() == null || turno.getHora().isEmpty()) return false;

        // Validar existencia en BD
        if (pacienteRepositorio.obtenerPorId(turno.getPaciente().getIdPaciente()) == null) {
            System.err.println("❌ Error: el paciente no existe en la base de datos.");
            return false;
        }
        if (medicoRepositorio.obtenerPorId(turno.getMedico().getIdMedico()) == null) {
            System.err.println("❌ Error: el médico no existe en la base de datos.");
            return false;
        }

        // Crear turno
        boolean resultado = turnoRepositorio.crearTurno(turno);
        if (!resultado) {
            System.err.println("⚠️ No se pudo crear el turno en la base de datos.");
        }
        return resultado;
    }

    // === OBTENER TODOS ===
    public List<Turno> obtenerTodos() {
        return turnoRepositorio.obtenerTodos();
    }

    // === OBTENER POR PACIENTE ===
    public List<Turno> obtenerPorPaciente(int idPaciente) {
        return turnoRepositorio.obtenerPorPaciente(idPaciente);
    }

    // === OBTENER POR MÉDICO ===
    public List<Turno> obtenerPorMedico(int idMedico) {
        return turnoRepositorio.obtenerTodos()
                .stream()
                .filter(t -> t.getMedico().getIdMedico() == idMedico)
                .toList();
    }

    // === OBTENER POR ID ===
    public Turno obtenerPorId(int idTurno) {
        return turnoRepositorio.obtenerPorId(idTurno);
    }

    // === ACTUALIZAR ===
    public boolean actualizarTurno(Turno turno) {
        return turnoRepositorio.actualizarTurno(turno);
    }

    // === ELIMINAR ===
    public boolean eliminarTurno(int idTurno) {
        return turnoRepositorio.eliminarTurno(idTurno);
    }
}
