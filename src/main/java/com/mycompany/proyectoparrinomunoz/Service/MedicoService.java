package com.mycompany.proyectoparrinomunoz.Service;

import com.mycompany.proyectoparrinomunoz.Entity.Medico;
import com.mycompany.proyectoparrinomunoz.repositorios.MedicoRepositorio;

import java.util.List;

public class MedicoService {

    private final MedicoRepositorio medicoRepositorio;

    public MedicoService() {
        medicoRepositorio = new MedicoRepositorio();
    }

    public boolean crearMedico(Medico m) {
        if (!validarMedico(m)) {
            System.err.println("Error: datos del médico incompletos o inválidos.");
            return false;
        }

        // Verificar duplicado (nombre + especialidad)
        List<Medico> existentes = medicoRepositorio.obtenerTodos(); // corregido
        boolean duplicado = existentes != null && existentes.stream()
                .anyMatch(existing ->
                        existing.getNombre().equalsIgnoreCase(m.getNombre()) &&
                                existing.getEspecialidad().equalsIgnoreCase(m.getEspecialidad()));

        if (duplicado) {
            System.err.println("Error: ya existe un médico con ese nombre y especialidad.");
            return false;
        }

        return medicoRepositorio.crearMedico(m);
    }

    public boolean actualizarMedico(Medico m) {
        if (m == null || m.getIdMedico() <= 0) {
            System.err.println("Error: ID de médico no válido.");
            return false;
        }


        if (!validarMedico(m)) {
            System.err.println("Error: datos del médico incompletos.");
            return false;
        }

        return medicoRepositorio.actualizarMedico(m);
    }

    public boolean eliminarMedico(int id) {
        if (id <= 0) {
            System.err.println("Error: ID de médico inválido.");
            return false;
        }
        return medicoRepositorio.eliminarMedico(id);
    }

    public List<Medico> listarMedicos() {
        return medicoRepositorio.obtenerTodos(); // corregido
    }

    public Medico obtenerPorId(int idMedico) {
        if (idMedico <= 0) {
            System.err.println("Error: ID de médico no válido.");
            return null;
        }

        try {
            return medicoRepositorio.obtenerPorId(idMedico);
        } catch (Exception e) {
            System.err.println("Error en obtenerPorId(): " + e.getMessage());
            return null;
        }
    }


    private boolean validarMedico(Medico m) {
        return m != null &&
                m.getNombre() != null && !m.getNombre().isBlank() &&
                m.getEspecialidad() != null && !m.getEspecialidad().isBlank();
    }

    public List<Medico> buscarPorEspecialidad(String especialidad) {
        if (especialidad == null || especialidad.isBlank()) {
            System.err.println("Error: especialidad vacía o nula.");
            return List.of();
        }

        try {
            return medicoRepositorio.buscarPorEspecialidad(especialidad);
        } catch (Exception e) {
            System.err.println("Error en buscarPorEspecialidad(): " + e.getMessage());
            return List.of();
        }
    }

}
