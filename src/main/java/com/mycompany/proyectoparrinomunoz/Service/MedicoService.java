package com.mycompany.proyectoparrinomunoz.Service;
import com.mycompany.proyectoparrinomunoz.Entity.Medico;
import com.mycompany.proyectoparrinomunoz.repositorios.MedicoRepositorio;
import java.util.List;

public class MedicoService {

    private MedicoRepositorio medicoRepositorio;

    public MedicoService() {
        medicoRepositorio = new MedicoRepositorio();
    }

    // CREAR MÉDICO
    public boolean crearMedico(Medico medico) {
        if (medico.getNombre() == null || medico.getNombre().isEmpty()) return false;
        if (medico.getApellido() == null || medico.getApellido().isEmpty()) return false;
        if (medico.getEspecialidad() == null || medico.getEspecialidad().isEmpty()) return false;
        if (medico.getMatricula() == null || medico.getMatricula().isEmpty()) return false;

        return medicoRepositorio.crearMedico(medico);
    }

    // OBTENER MÉDICO POR ID
    public Medico obtenerMedicoPorId(int idMedico) {
        return medicoRepositorio.obtenerPorId(idMedico);
    }

    // OBTENER TODOS LOS MÉDICOS
    public List<Medico> obtenerTodos() {
        return medicoRepositorio.obtenerTodos();
    }

    // ACTUALIZAR MÉDICO
    public boolean actualizarMedico(Medico medico) {
        if (medico.getIdMedico() <= 0) return false;
        return medicoRepositorio.actualizarMedico(medico);
    }

    // ELIMINAR MÉDICO
    public boolean eliminarMedico(int idMedico) {
        if (idMedico <= 0) return false;
        return medicoRepositorio.eliminarMedico(idMedico);
    }

    // BUSCAR MÉDICOS POR ESPECIALIDAD
    public List<Medico> buscarPorEspecialidad(String especialidad) {
        if (especialidad == null || especialidad.isEmpty()) return List.of();
        return medicoRepositorio.buscarPorEspecialidad(especialidad);
    }
}
