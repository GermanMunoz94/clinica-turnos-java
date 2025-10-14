package com.mycompany.proyectoparrinomunoz.Controller;

import com.mycompany.proyectoparrinomunoz.Entity.Medico;
import com.mycompany.proyectoparrinomunoz.Service.MedicoService;
import java.util.List;

public class MedicoController {

    private MedicoService medicoService;

    public MedicoController() {
        this.medicoService = new MedicoService();
    }

    // CREAR MÉDICO a partir de datos sueltos
    public boolean crearMedico(String nombre, String apellido, String especialidad, String matricula) {
        Medico medico = new Medico(0, nombre, apellido, especialidad, matricula);
        return medicoService.crearMedico(medico);
    }

    // AGREGAR MÉDICO desde un objeto Medico
    public boolean agregarMedico(Medico m) {
        return medicoService.crearMedico(m);
    }

    // ACTUALIZAR MÉDICO
    public boolean actualizarMedico(Medico m) {
        return medicoService.actualizarMedico(m);
    }

    // LISTAR TODOS LOS MÉDICOS
    public List<Medico> listarMedicos() {
        return medicoService.obtenerTodos();
    }

    // BUSCAR MÉDICO POR ID
    public Medico buscarMedicoPorId(int idMedico) {
        return medicoService.obtenerMedicoPorId(idMedico);
    }

    // LISTAR MÉDICOS POR ESPECIALIDAD
    public List<Medico> listarMedicosPorEspecialidad(String especialidad) {
        return medicoService.buscarPorEspecialidad(especialidad);
    }

    // ELIMINAR MÉDICO POR ID
    public boolean eliminarMedico(int idMedico) {
        return medicoService.eliminarMedico(idMedico);
    }
}
