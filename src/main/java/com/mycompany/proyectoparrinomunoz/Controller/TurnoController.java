package com.mycompany.proyectoparrinomunoz.Controller;

import com.mycompany.proyectoparrinomunoz.Entity.Medico;
import com.mycompany.proyectoparrinomunoz.Entity.Paciente;
import com.mycompany.proyectoparrinomunoz.Entity.Turno;
import com.mycompany.proyectoparrinomunoz.Service.TurnoService;
import java.util.List;

public class TurnoController {

    private TurnoService turnoService;

    public TurnoController(PacienteController pacienteController, MedicoController medicoController) {
        this.turnoService = new TurnoService();
    }

    // CREAR TURNO a partir de datos sueltos
    public boolean crearTurno(Paciente paciente, Medico medico, String fecha, String hora) {
        Turno turno = new Turno(0, paciente, medico, fecha, hora);
        return turnoService.crearTurno(turno);
    }

    // AGREGAR TURNO desde un objeto Turno
    public boolean agregarTurno(Turno t) {
        return turnoService.crearTurno(t);
    }

    // ACTUALIZAR TURNO
    public boolean actualizarTurno(Turno t) {
        return turnoService.actualizarTurno(t);
    }

    // LISTAR TODOS LOS TURNOS
    public List<Turno> listarTurnos() {
        return turnoService.obtenerTodos();
    }

    // BUSCAR TURNO POR ID
    public Turno buscarTurnoPorId(int idTurno) {
        return turnoService.obtenerTurnoPorId(idTurno);
    }

    // LISTAR TURNOS POR PACIENTE
    public List<Turno> listarTurnosPorPaciente(int idPaciente) {
        return turnoService.obtenerTurnosPorPaciente(idPaciente);
    }

    // LISTAR TURNOS POR MÉDICO
    public List<Turno> listarTurnosPorMedico(int idMedico) {
        return turnoService.obtenerTurnosPorMedico(idMedico);
    }

    // ELIMINAR TURNO POR ID
    public boolean eliminarTurno(int idTurno) {
        return turnoService.eliminarTurno(idTurno);
    }
}
