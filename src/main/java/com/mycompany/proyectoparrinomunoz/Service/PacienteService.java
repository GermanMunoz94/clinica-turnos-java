package com.mycompany.proyectoparrinomunoz.Service;
import com.mycompany.proyectoparrinomunoz.Entity.Paciente;
import com.mycompany.proyectoparrinomunoz.repositorios.PacienteRepositorio;
import java.util.List;

public class PacienteService {

    private PacienteRepositorio pacienteRepositorio;

    public PacienteService() {
        pacienteRepositorio = new PacienteRepositorio();
    }

    // CREAR PACIENTE
    public boolean crearPaciente(Paciente paciente) {
        // Validaciones básicas
        if (paciente.getNombre() == null || paciente.getNombre().isEmpty()) return false;
        if (paciente.getApellido() == null || paciente.getApellido().isEmpty()) return false;
        if (paciente.getDni() == null || paciente.getDni().isEmpty()) return false;

        return pacienteRepositorio.crearPaciente(paciente);
    }

    // OBTENER PACIENTE POR ID
    public Paciente obtenerPacientePorId(int idPaciente) {
        return pacienteRepositorio.obtenerPorId(idPaciente);
    }

    // OBTENER TODOS LOS PACIENTES
    public List<Paciente> obtenerTodos() {
        return pacienteRepositorio.obtenerTodos();
    }

    // ACTUALIZAR PACIENTE
    public boolean actualizarPaciente(Paciente paciente) {
        if (paciente.getIdPaciente() <= 0) return false;
        return pacienteRepositorio.actualizarPaciente(paciente);
    }

    // ELIMINAR PACIENTE
    public boolean eliminarPaciente(int idPaciente) {
        if (idPaciente <= 0) return false;
        return pacienteRepositorio.eliminarPaciente(idPaciente);
    }

    // BUSCAR PACIENTES POR NOMBRE O APELLIDO
    public List<Paciente> buscarPacientes(String texto) {
        return pacienteRepositorio.buscarPorNombreApellido(texto);
    }
}
