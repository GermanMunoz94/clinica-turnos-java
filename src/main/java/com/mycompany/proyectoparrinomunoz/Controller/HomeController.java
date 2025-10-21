package com.mycompany.proyectoparrinomunoz.Controller;

import com.mycompany.proyectoparrinomunoz.vistas.Principal;

import javax.swing.*;


public class HomeController {

    private final PacienteController pacienteController;
    private final MedicoController medicoController;
    private final TurnoController turnoController;

    public HomeController() {
        // Instanciamos los controladores una sola vez
        this.pacienteController = new PacienteController();
        this.medicoController = new MedicoController();
        this.turnoController = new TurnoController();
    }

    // Cambio de página
    public void cambio_ventana(int ventana) {
        if (ventana == 1) {
            new Principal().setVisible(true);
        } else  {
            // ✅ Ahora pasamos los controladores requeridos
            JOptionPane.showMessageDialog(null, "Ventana no reconocida");
        }
    }
}

