package com.mycompany.proyectoparrinomunoz.Controller;

import com.mycompany.proyectoparrinomunoz.Entity.Diagnostico;
import com.mycompany.proyectoparrinomunoz.Service.DiagnosticoService;
import java.util.Collections;
import java.util.List;

public class DiagnosticoController {

    private final DiagnosticoService diagnosticoService;

    public DiagnosticoController() {
        this.diagnosticoService = new DiagnosticoService();
    }

    // === Crear diagnóstico ===
    public boolean crearDiagnostico(Diagnostico d, int idTurno) {
        return diagnosticoService.crearDiagnostico(d, idTurno);
    }


    // === Actualizar diagnóstico ===
    public boolean actualizarDiagnostico(Diagnostico d) {
        try {
            return diagnosticoService.actualizarDiagnostico(d);
        } catch (Exception e) {
            System.err.println("Error en actualizarDiagnostico(): " + e.getMessage());
            return false;
        }
    }

    // === Eliminar diagnóstico ===
    public boolean eliminarDiagnostico(int idDiagnostico) {
        try {
            return diagnosticoService.eliminarDiagnostico(idDiagnostico);
        } catch (Exception e) {
            System.err.println("Error en eliminarDiagnostico(): " + e.getMessage());
            return false;
        }
    }

    // === Listar diagnósticos ===
    public List<Diagnostico> listarDiagnosticos() {
        try {
            List<Diagnostico> lista = diagnosticoService.listarDiagnosticos();
            return lista != null ? lista : Collections.emptyList();
        } catch (Exception e) {
            System.err.println("Error en listarDiagnosticos(): " + e.getMessage());
            return Collections.emptyList();
        }
    }

    // === Buscar diagnóstico por ID ===
    public Diagnostico buscarDiagnosticoPorId(int idDiagnostico) {
        try {
            return diagnosticoService.buscarPorId(idDiagnostico);
        } catch (Exception e) {
            System.err.println("Error en buscarDiagnosticoPorId(): " + e.getMessage());
            return null;
        }
    }
}
