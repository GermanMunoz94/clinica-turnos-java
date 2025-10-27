package com.mycompany.proyectoparrinomunoz.Controller;

import com.mycompany.proyectoparrinomunoz.Entity.Paciente;
import com.mycompany.proyectoparrinomunoz.Service.PacienteService;

import java.util.Collections;
import java.util.List;

public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController() {
        this.pacienteService = new PacienteService();
    }

    // === Crear Paciente ===
    public boolean crearPaciente(Paciente p) {
        try {
            return pacienteService.crearPaciente(p);
        } catch (Exception e) {
            System.err.println("Error en crearPaciente(): " + e.getMessage());
            return false;
        }
    }

    // === Actualizar Paciente ===
    public boolean actualizarPaciente(Paciente p) {
        try {
            return pacienteService.actualizarPaciente(p);
        } catch (Exception e) {
            System.err.println("Error en actualizarPaciente(): " + e.getMessage());
            return false;
        }
    }

    // === Eliminar Paciente ===
    public boolean eliminarPaciente(int idPaciente) {
        try {
            return pacienteService.eliminarPaciente(idPaciente);
        } catch (Exception e) {
            System.err.println("Error en eliminarPaciente(): " + e.getMessage());
            return false;
        }
    }

    // === Listar Pacientes ===
    public List<Paciente> listarPacientes() {
        try {
            List<Paciente> lista = pacienteService.obtenerTodos();
            return lista != null ? lista : Collections.emptyList();
        } catch (Exception e) {
            System.err.println("Error en listarPacientes(): " + e.getMessage());
            return Collections.emptyList();
        }
    }

    // === Buscar por ID ===
    public Paciente buscarPacientePorId(int idPaciente) {
        try {
            return pacienteService.obtenerPacientePorId(idPaciente);
        } catch (Exception e) {
            System.err.println("Error en buscarPacientePorId(): " + e.getMessage());
            return null;
        }
    }

    // === Buscar por DNI ===
    public Paciente buscarPacientePorDni(String dni) {
        try {
            return pacienteService.buscarPorDni(dni);
        } catch (Exception e) {
            System.err.println("Error en buscarPacientePorDni(): " + e.getMessage());
            return null;
        }
    }
}
