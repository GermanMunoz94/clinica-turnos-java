package com.mycompany.proyectoparrinomunoz.Service;

import com.mycompany.proyectoparrinomunoz.Entity.Diagnostico;
import com.mycompany.proyectoparrinomunoz.repositorios.DiagnosticoRepositorio;
import java.util.List;

public class DiagnosticoService {

    private final DiagnosticoRepositorio diagnosticoRepo;

    public DiagnosticoService() {
        this.diagnosticoRepo = new DiagnosticoRepositorio();
    }

    // === Crear Diagnóstico ===
    // === Crear Diagnóstico ===
    public boolean crearDiagnostico(Diagnostico diagnostico, int idTurno) {
        if (diagnostico == null) return false;
        if (diagnostico.getDescripcion() == null || diagnostico.getDescripcion().isEmpty()) return false;
        if (idTurno <= 0) {
            System.err.println("Error: ID de turno inválido.");
            return false;
        }
        return diagnosticoRepo.crearDiagnostico(diagnostico, idTurno);
    }


    // === Actualizar Diagnóstico ===
    public boolean actualizarDiagnostico(Diagnostico diagnostico) {
        if (diagnostico == null || diagnostico.getIdDiagnostico() <= 0) {
            System.err.println("Error: ID de diagnóstico inválido.");
            return false;
        }
        if (!validarDiagnostico(diagnostico)) {
            System.err.println("Error: datos del diagnóstico incompletos.");
            return false;
        }
        return diagnosticoRepo.actualizarDiagnostico(diagnostico);
    }

    // === Eliminar Diagnóstico ===
    public boolean eliminarDiagnostico(int idDiagnostico) {
        if (idDiagnostico <= 0) {
            System.err.println("Error: ID de diagnóstico inválido.");
            return false;
        }
        return diagnosticoRepo.eliminarDiagnostico(idDiagnostico);
    }

    // === Listar todos ===
    public List<Diagnostico> listarDiagnosticos() {
        return diagnosticoRepo.listarDiagnosticos();
    }

    // === Buscar por ID ===
    public Diagnostico buscarPorId(int idDiagnostico) {
        if (idDiagnostico <= 0) {
            System.err.println("Error: ID de diagnóstico inválido para búsqueda.");
            return null;
        }
        return diagnosticoRepo.obtenerPorId(idDiagnostico);
    }

    // === Validar Diagnóstico ===
    private boolean validarDiagnostico(Diagnostico d) {
        return d != null &&
                d.getPaciente() != null &&
                d.getMedico() != null &&
                d.getDescripcion() != null &&
                !d.getDescripcion().isBlank();
    }
}
