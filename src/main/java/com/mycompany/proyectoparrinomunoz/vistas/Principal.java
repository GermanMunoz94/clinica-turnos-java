package com.mycompany.proyectoparrinomunoz.vistas;

import com.mycompany.proyectoparrinomunoz.Controller.MedicoController;
import com.mycompany.proyectoparrinomunoz.Controller.PacienteController;
import com.mycompany.proyectoparrinomunoz.Controller.TurnoController;

import javax.swing.*;
import java.awt.*;

public class Principal extends JFrame {
    public Principal() {
        setTitle("Sistema Médico - Menú Principal");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 🔗 Controladores compartidos
        PacienteController pacienteController = new PacienteController();
        MedicoController medicoController = new MedicoController();
        TurnoController turnoController = new TurnoController(pacienteController, medicoController);

        // 🔘 Botones principales
        JButton btnPacientes = new JButton("Gestionar Pacientes");
        JButton btnMedicos = new JButton("Gestionar Médicos");
        JButton btnTurnos = new JButton("Gestionar Turnos");
        JButton btnPanelMedico = new JButton("Panel Médico");
        JButton btnSalir = new JButton("Salir");

        // 🎨 Layout del menú
        JPanel panel = new JPanel(new GridLayout(5, 1, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        panel.add(btnPacientes);
        panel.add(btnMedicos);
        panel.add(btnTurnos);
        panel.add(btnPanelMedico);
        panel.add(btnSalir);

        add(panel, BorderLayout.CENTER);

        // 🧩 Acciones
        btnPacientes.addActionListener(e -> new PacienteVista(pacienteController).setVisible(true));
        btnMedicos.addActionListener(e -> new MedicoVista(medicoController).setVisible(true));
        btnTurnos.addActionListener(e -> new TurnoVista(turnoController, pacienteController, medicoController).setVisible(true));

        // 🩺 Abre el panel del médico (simulamos un médico logueado)
        btnPanelMedico.addActionListener(e -> {
            int idMedicoLogueado = 1; // ⚠️ Cambia según el médico real en tu base de datos
            new MedicoTurnoVista(idMedicoLogueado, turnoController, pacienteController, medicoController).setVisible(true);
        });

        // 🚪 Cierra la app
        btnSalir.addActionListener(e -> System.exit(0));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Principal().setVisible(true));
    }
}
