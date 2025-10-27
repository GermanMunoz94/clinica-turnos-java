package com.mycompany.proyectoparrinomunoz.Service;

import com.mycompany.proyectoparrinomunoz.Entity.Paciente;
import com.mycompany.proyectoparrinomunoz.repositorios.PacienteRepositorio;

import java.util.List;

public class PacienteService {

    private final PacienteRepositorio pacienteRepo;

    public PacienteService() {
        this.pacienteRepo = new PacienteRepositorio();
    }

    // === Crear Paciente ===
    public boolean crearPaciente(Paciente paciente) {
        if (!validarPaciente(paciente)) {
            System.err.println("Error: datos del paciente incompletos o inválidos.");
            return false;
        }

        // Evita duplicados por DNI
        List<Paciente> existentes = pacienteRepo.obtenerTodos();
        boolean duplicado = existentes.stream()
                .anyMatch(p -> p.getDni().equalsIgnoreCase(paciente.getDni()));

        if (duplicado) {
            System.err.println("Error: ya existe un paciente con ese DNI.");
            return false;
        }

        return pacienteRepo.crearPaciente(paciente);
    }

    // === Actualizar Paciente ===
    public boolean actualizarPaciente(Paciente paciente) {
        if (paciente == null || paciente.getIdPaciente() <= 0) {
            System.err.println("Error: ID de paciente no válido.");
            return false;
        }

        if (!validarPaciente(paciente)) {
            System.err.println("Error: datos del paciente incompletos.");
            return false;
        }

        return pacienteRepo.actualizarPaciente(paciente);
    }

    // === Eliminar Paciente ===
    public boolean eliminarPaciente(int idPaciente) {
        if (idPaciente <= 0) {
            System.err.println("Error: ID de paciente inválido.");
            return false;
        }
        return pacienteRepo.eliminarPaciente(idPaciente);
    }

    // === Obtener todos los pacientes ===
    public List<Paciente> obtenerTodos() {
        return pacienteRepo.obtenerTodos();
    }

    // === Buscar paciente por ID ===
    public Paciente obtenerPacientePorId(int idPaciente) {
        if (idPaciente <= 0) {
            System.err.println("Error: ID de paciente inválido.");
            return null;
        }
        return pacienteRepo.obtenerPorId(idPaciente);
    }

    // === Buscar paciente por DNI ===
    public Paciente buscarPorDni(String dni) {
        if (dni == null || dni.isBlank()) {
            System.err.println("Error: DNI vacío o nulo.");
            return null;
        }
        return pacienteRepo.buscarPorDni(dni);
    }

    // === Validación interna ===
    private boolean validarPaciente(Paciente p) {
        return p != null &&
                p.getNombre() != null && !p.getNombre().isBlank() &&
                p.getApellido() != null && !p.getApellido().isBlank() &&
                p.getDni() != null && !p.getDni().isBlank();
    }
}
