package com.mycompany.proyectoparrinomunoz.Controller;

import com.mycompany.proyectoparrinomunoz.Entity.Diagnostico;
import com.mycompany.proyectoparrinomunoz.Service.DiagnosticoService;
import java.util.List;

public class DiagnosticoController {

    private DiagnosticoService diagnosticoService;

    public DiagnosticoController() {
        this.diagnosticoService = new DiagnosticoService();
    }

    // === CREAR DIAGNOSTICO ===
    public boolean crearDiagnostico(Diagnostico diagnostico) {
        return diagnosticoService.crearDiagnostico(diagnostico);
    }

    // === LISTAR TODOS ===
    public List<Diagnostico> listarDiagnosticos() {
        return diagnosticoService.obtenerTodos();
    }

    // === BUSCAR POR ID ===
    public Diagnostico buscarDiagnosticoPorId(int idDiagnostico) {
        return diagnosticoService.obtenerDiagnosticoPorId(idDiagnostico);
    }

    // === LISTAR POR TURNO ===
    public List<Diagnostico> listarDiagnosticosPorTurno(int idTurno) {
        return diagnosticoService.obtenerPorTurno(idTurno);
    }

    // === LISTAR POR PACIENTE === ✅ corregido
    public List<Diagnostico> obtenerDiagnosticosPorPaciente(int idPaciente) {
        return diagnosticoService.obtenerPorPaciente(idPaciente);
    }

    // === ACTUALIZAR ===
    public boolean actualizarDiagnostico(Diagnostico diagnostico) {
        return diagnosticoService.actualizarDiagnostico(diagnostico);
    }

    // === ELIMINAR ===
    public boolean eliminarDiagnostico(int idDiagnostico) {
        return diagnosticoService.eliminarDiagnostico(idDiagnostico);
    }
}
