package com.mycompany.proyectoparrinomunoz.Service;

import com.mycompany.proyectoparrinomunoz.Entity.Diagnostico;
import com.mycompany.proyectoparrinomunoz.repositorios.DiagnosticoRepositorio;

import java.util.ArrayList;
import java.util.List;

public class DiagnosticoService {

    private DiagnosticoRepositorio diagnosticoRepositorio;

    public DiagnosticoService() {
        this.diagnosticoRepositorio = new DiagnosticoRepositorio();
    }

    // === CREAR DIAGNOSTICO ===
    public boolean crearDiagnostico(Diagnostico diagnostico) {
        if (diagnostico == null || diagnostico.getTurno() == null) {
            System.err.println("❌ Error: diagnóstico o turno nulo");
            return false;
        }
        return diagnosticoRepositorio.crearDiagnostico(diagnostico);
    }

    // === OBTENER DIAGNOSTICO POR ID ===
    public Diagnostico obtenerDiagnosticoPorId(int idDiagnostico) {
        return diagnosticoRepositorio.obtenerPorId(idDiagnostico);
    }

    // === OBTENER TODOS LOS DIAGNOSTICOS ===
    public List<Diagnostico> obtenerTodos() {
        return diagnosticoRepositorio.obtenerTodos();
    }

    // === OBTENER POR TURNO ===
    public List<Diagnostico> obtenerPorTurno(int idTurno) {
        return diagnosticoRepositorio.obtenerPorTurno(idTurno);
    }

    // === ACTUALIZAR ===
    public boolean actualizarDiagnostico(Diagnostico diagnostico) {
        if (diagnostico == null || diagnostico.getIdDiagnostico() <= 0) {
            System.err.println("❌ Error: diagnóstico inválido para actualizar");
            return false;
        }
        return diagnosticoRepositorio.actualizarDiagnostico(diagnostico);
    }

    // === ELIMINAR ===
    public boolean eliminarDiagnostico(int idDiagnostico) {
        if (idDiagnostico <= 0) {
            System.err.println("❌ Error: ID de diagnóstico inválido");
            return false;
        }
        return diagnosticoRepositorio.eliminarDiagnostico(idDiagnostico);
    }

    public List<Diagnostico> obtenerPorPaciente(int idPaciente) {
        if (idPaciente <= 0) {
            System.err.println("⚠️ ID de paciente inválido: " + idPaciente);
            return new ArrayList<>();
        }
        return diagnosticoRepositorio.obtenerPorPaciente(idPaciente);
    }

}

