package com.mycompany.proyectoparrinomunoz.vistas;

import com.mycompany.proyectoparrinomunoz.Controller.MedicoController;
import com.mycompany.proyectoparrinomunoz.Controller.PacienteController;
import com.mycompany.proyectoparrinomunoz.Controller.TurnoController;

import javax.swing.*;
import java.awt.*;

public class Principal extends JFrame {

    public Principal() {
        setTitle("Sistema Médico - Menú Principal (Administrador)");
        setSize(400, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 🔗 Controladores compartidos
        PacienteController pacienteController = new PacienteController();
        MedicoController medicoController = new MedicoController();
        TurnoController turnoController = new TurnoController();

        // 🔘 Botones principales
        JButton btnPacientes = new JButton("Gestionar Pacientes");
        JButton btnMedicos = new JButton("Gestionar Médicos");
        JButton btnTurnos = new JButton("Gestionar Turnos");
        JButton btnPanelMedico = new JButton("Panel Médico");
        JButton btnCerrarSesion = new JButton("Cerrar sesión");
        JButton btnSalir = new JButton("Salir del sistema");

        // 🎨 Layout del menú
        JPanel panel = new JPanel(new GridLayout(6, 1, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        panel.add(btnPacientes);
        panel.add(btnMedicos);
        panel.add(btnTurnos);
        panel.add(btnPanelMedico);
        panel.add(btnCerrarSesion);
        panel.add(btnSalir);

        add(panel, BorderLayout.CENTER);

        // 🧩 Acciones
        btnPacientes.addActionListener(e -> new PacienteVista(1).setVisible(true));
        btnMedicos.addActionListener(e -> new MedicoVista(medicoController).setVisible(true));
        btnTurnos.addActionListener(e -> new TurnoVista(turnoController, pacienteController, medicoController).setVisible(true));

        // 🩺 Simula un médico logueado
        btnPanelMedico.addActionListener(e -> {
            int idMedicoLogueado = 1; // Cambiar según base de datos
            new MedicoTurnoVista(idMedicoLogueado, turnoController, pacienteController, medicoController).setVisible(true);
        });

        // 🔄 Cerrar sesión: vuelve al Login
        btnCerrarSesion.addActionListener(e -> {
            dispose(); // Cierra la ventana actual
            new LoginVista().setVisible(true);
        });

        // 🚪 Cierra la aplicación completa
        btnSalir.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "¿Seguro que desea salir del sistema?",
                    "Confirmar salida",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }
}
