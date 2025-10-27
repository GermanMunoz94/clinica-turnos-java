package com.mycompany.proyectoparrinomunoz.vistas;

import com.formdev.flatlaf.FlatLightLaf;
import com.mycompany.proyectoparrinomunoz.Controller.TurnoController;
import com.mycompany.proyectoparrinomunoz.Entity.Turno;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MedicoTurnoVista extends JFrame {

    private final TurnoController turnoController;
    private JTable tablaTurnos;
    private DefaultTableModel modelo;
    private final int idMedico;

    public MedicoTurnoVista(int idMedico) {
        FlatLightLaf.setup();
        this.idMedico = idMedico;
        this.turnoController = new TurnoController();
        initUI();
        listarTurnos();
    }

    private void initUI() {
        setTitle("Turnos Asignados - Médico");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel lblTitulo = new JLabel("Turnos del Médico", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(lblTitulo, BorderLayout.NORTH);

        modelo = new DefaultTableModel(new String[]{"ID", "Paciente", "Fecha", "Hora"}, 0);
        tablaTurnos = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tablaTurnos);
        add(scroll, BorderLayout.CENTER);

        JButton btnActualizar = new JButton("Actualizar lista");
        btnActualizar.addActionListener(e -> listarTurnos());
        add(btnActualizar, BorderLayout.SOUTH);
    }

    private void listarTurnos() {
        modelo.setRowCount(0);

        if (idMedico <= 0) {
            JOptionPane.showMessageDialog(this, "ID de médico no válido.");
            return;
        }

        List<Turno> turnos = turnoController.listarTurnosPorMedico(idMedico);
        if (turnos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay turnos asignados.");
            return;
        }

        for (Turno t : turnos) {
            modelo.addRow(new Object[]{
                    t.getIdTurno(),
                    t.getPaciente().getNombre(),
                    t.getFecha(),
                    t.getHora()
            });
        }
    }
}
