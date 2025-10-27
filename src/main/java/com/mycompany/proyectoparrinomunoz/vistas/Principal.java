package com.mycompany.proyectoparrinomunoz.vistas;

import com.formdev.flatlaf.FlatLightLaf;
import com.mycompany.proyectoparrinomunoz.Entity.Usuario;

import javax.swing.*;
import java.awt.*;

public class Principal extends JFrame {

    private final Usuario usuario;

    public Principal(Usuario usuario) {
        FlatLightLaf.setup();
        this.usuario = usuario;
        initUI();
    }

    private void initUI() {
        setTitle("Gestor de Turnos - Menú Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel lblTitulo = new JLabel("Gestor de Turnos", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(lblTitulo, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new GridLayout(3, 2, 15, 15));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JButton btnMedicos = new JButton("Médicos");
        JButton btnPacientes = new JButton("Pacientes");
        JButton btnTurnos = new JButton("Turnos");
        JButton btnDiagnosticos = new JButton("Diagnósticos");
        JButton btnCerrar = new JButton("Cerrar sesión");

        // Acciones
        btnMedicos.addActionListener(e -> abrirVista("medicos"));
        btnPacientes.addActionListener(e -> abrirVista("pacientes"));
        btnTurnos.addActionListener(e -> abrirVista("turnos"));
        btnDiagnosticos.addActionListener(e -> abrirVista("diagnosticos"));
        btnCerrar.addActionListener(e -> cerrarSesion());

        // Mostrar botones según rol
        switch (usuario.getRol().toLowerCase()) {
            case "admin" -> {
                panelBotones.add(btnMedicos);
                panelBotones.add(btnPacientes);
                panelBotones.add(btnTurnos);
                panelBotones.add(btnDiagnosticos);
            }
            case "medico" -> {
                panelBotones.add(btnTurnos);
                panelBotones.add(btnDiagnosticos);
            }
            case "paciente" -> {
                panelBotones.add(btnTurnos);
            }
        }

        panelBotones.add(new JLabel());
        panelBotones.add(btnCerrar);

        add(panelBotones, BorderLayout.CENTER);
    }

    private void abrirVista(String tipo) {
        try {
            switch (tipo) {
                case "medicos" -> new MedicoVista().setVisible(true);
                case "pacientes" -> new PacienteVista().setVisible(true);
                case "turnos" -> {
                    if (usuario.getRol().equalsIgnoreCase("paciente")) {
                        // Buscar paciente por id_usuario o simplemente abrir la vista general
                        new PacienteGestionVista().setVisible(true);
                    } else if (usuario.getRol().equalsIgnoreCase("medico")) {
                        new MedicoTurnoVista(usuario.getIdUsuario()).setVisible(true);
                    } else {
                        new TurnoVista().setVisible(true);
                    }
                }
                case "diagnosticos" -> new DiagnosticoVista().setVisible(true);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al abrir la vista: " + e.getMessage(),
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
        // Ejemplo de uso sin login real
        Usuario admin = new Usuario(1, "admin", "admin", "Administrador", "admin");
        SwingUtilities.invokeLater(() -> new Principal(admin).setVisible(true));
    }
}
