package com.mycompany.proyectoparrinomunoz.vistas;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;

public class AdminVista extends JFrame {

    public AdminVista() {
        FlatLightLaf.setup();
        initUI();
    }

    private void initUI() {
        setTitle("Panel de Administración - Gestor de Turnos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Panel de Administración", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titulo, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new GridLayout(3, 2, 15, 15));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JButton btnMedicos = new JButton("Gestionar Médicos");
        JButton btnPacientes = new JButton("Gestionar Pacientes");
        JButton btnTurnos = new JButton("Gestionar Turnos");
        JButton btnDiagnosticos = new JButton("Gestionar Diagnósticos");
        JButton btnSalir = new JButton("Cerrar sesión");

        btnMedicos.addActionListener(e -> abrirVentana(new MedicoVista()));
        btnPacientes.addActionListener(e -> abrirVentana(new PacienteVista()));
        btnTurnos.addActionListener(e -> abrirVentana(new TurnoVista()));
        btnDiagnosticos.addActionListener(e -> abrirVentana(new DiagnosticoVista()));
        btnSalir.addActionListener(e -> cerrarSesion());

        panelBotones.add(btnMedicos);
        panelBotones.add(btnPacientes);
        panelBotones.add(btnTurnos);
        panelBotones.add(btnDiagnosticos);
        panelBotones.add(new JLabel());
        panelBotones.add(btnSalir);

        add(panelBotones, BorderLayout.CENTER);
    }

    private void abrirVentana(JFrame vista) {
        try {
            vista.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al abrir ventana: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void cerrarSesion() {
        int confirmar = JOptionPane.showConfirmDialog(this,
                "¿Desea cerrar sesión?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (confirmar == JOptionPane.YES_OPTION) {
            dispose();
            new LoginVista().setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminVista().setVisible(true));
    }
}
