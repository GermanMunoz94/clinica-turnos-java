package com.mycompany.proyectoparrinomunoz.Controller;

import com.mycompany.proyectoparrinomunoz.Entity.Paciente;
import com.mycompany.proyectoparrinomunoz.Service.PacienteService;
import java.util.List;

public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController() {
        this.pacienteService = new PacienteService();
    }

    // CREAR PACIENTE
    public boolean crearPaciente(String nombre, String apellido, String dni, String email, String telefono) {
        Paciente paciente = new Paciente(0, nombre, apellido, dni, email, telefono);
        return pacienteService.crearPaciente(paciente);
    }

    // AGREGAR PACIENTE desde un objeto
    public boolean agregarPaciente(Paciente p) {
        return pacienteService.crearPaciente(p);
    }

    // ACTUALIZAR PACIENTE
    public boolean actualizarPaciente(Paciente p) {
        return pacienteService.actualizarPaciente(p);
    }

    // LISTAR TODOS LOS PACIENTES
    public List<Paciente> listarPacientes() {
        return pacienteService.obtenerTodos();
    }

    // BUSCAR PACIENTE POR ID
    public Paciente buscarPacientePorId(int id) {
        return pacienteService.obtenerPacientePorId(id);
    }

    // ELIMINAR PACIENTE POR ID
    public boolean eliminarPaciente(int idPaciente) {
        return pacienteService.eliminarPaciente(idPaciente);
    }
}
