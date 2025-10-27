package com.mycompany.proyectoparrinomunoz.Controller;

import com.mycompany.proyectoparrinomunoz.Entity.Medico;
import com.mycompany.proyectoparrinomunoz.Service.MedicoService;

import java.util.Collections;
import java.util.List;

public class MedicoController {

    private final MedicoService medicoService;

    public MedicoController() {
        medicoService = new MedicoService();
    }

    // === Crear médico ===
    public boolean crearMedico(Medico medico) {
        try {
            return medicoService.crearMedico(medico);
        } catch (Exception e) {
            System.err.println("Error en crearMedico(): " + e.getMessage());
            return false;
        }
    }

    // === Listar todos los médicos ===
    public List<Medico> listarMedicos() {
        try {
            List<Medico> lista = medicoService.listarMedicos();
            return lista != null ? lista : Collections.emptyList();
        } catch (Exception e) {
            System.err.println("Error en listarMedicos(): " + e.getMessage());
            return Collections.emptyList();
        }
    }

    // === Buscar médico por ID ===
    public Medico obtenerPorId(int idMedico) {
        try {
            return medicoService.obtenerPorId(idMedico);
        } catch (Exception e) {
            System.err.println("Error en obtenerPorId(): " + e.getMessage());
            return null;
        }
    }

    // === Actualizar médico ===
    public boolean actualizarMedico(Medico medico) {
        try {
            return medicoService.actualizarMedico(medico);
        } catch (Exception e) {
            System.err.println("Error en actualizarMedico(): " + e.getMessage());
            return false;
        }
    }

    // === Eliminar médico ===
    public boolean eliminarMedico(int idMedico) {
        try {
            return medicoService.eliminarMedico(idMedico);
        } catch (Exception e) {
            System.err.println("Error en eliminarMedico(): " + e.getMessage());
            return false;
        }
    }

    // === Buscar médicos por especialidad ===
    public List<Medico> buscarPorEspecialidad(String especialidad) {
        try {
            List<Medico> lista = medicoService.buscarPorEspecialidad(especialidad);
            return lista != null ? lista : Collections.emptyList();
        } catch (Exception e) {
            System.err.println("Error en buscarPorEspecialidad(): " + e.getMessage());
            return Collections.emptyList();
        }
    }
}
