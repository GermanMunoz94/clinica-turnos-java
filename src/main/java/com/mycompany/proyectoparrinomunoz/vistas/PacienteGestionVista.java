package com.mycompany.proyectoparrinomunoz.vistas;

import com.formdev.flatlaf.FlatLightLaf;
import com.mycompany.proyectoparrinomunoz.Controller.TurnoController;
import com.mycompany.proyectoparrinomunoz.Controller.MedicoController;
import com.mycompany.proyectoparrinomunoz.Entity.Paciente;
import com.mycompany.proyectoparrinomunoz.Entity.Medico;
import com.mycompany.proyectoparrinomunoz.Entity.Turno;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PacienteGestionVista extends JFrame {

    private final TurnoController turnoController;
    private final MedicoController medicoController;
    private Paciente paciente = new Paciente();
    private JTable tablaTurnos;
    private DefaultTableModel modelo;
    private JComboBox<Medico> cbMedicos;
    private JTextField txtFecha;
    private JTextField txtHora;

    public PacienteGestionVista() {
        FlatLightLaf.setup();
        this.paciente = paciente;
        this.turnoController = new TurnoController();
        this.medicoController = new MedicoController();
        initUI();
        cargarMedicos();
        listarTurnos();
    }

    private void initUI() {
        setTitle("Gestión de Turnos del Paciente");
        setSize(750, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel lblTitulo = new JLabel("Mis Turnos", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(lblTitulo, BorderLayout.NORTH);

        JPanel panelFormulario = new JPanel(new GridLayout(4, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        cbMedicos = new JComboBox<>();
        txtFecha = new JTextField();
        txtHora = new JTextField();

        JButton btnNuevo = new JButton("Reservar turno");
        JButton btnCancelar = new JButton("Cancelar turno");
        JButton btnActualizar = new JButton("Actualizar lista");

        btnNuevo.addActionListener(e -> reservarTurno());
        btnCancelar.addActionListener(e -> cancelarTurno());
        btnActualizar.addActionListener(e -> listarTurnos());

        panelFormulario.add(new JLabel("Médico:"));
        panelFormulario.add(cbMedicos);
        panelFormulario.add(new JLabel("Fecha (YYYY-MM-DD):"));
        panelFormulario.add(txtFecha);
        panelFormulario.add(new JLabel("Hora (HH:MM):"));
        panelFormulario.add(txtHora);
        panelFormulario.add(btnNuevo);
        panelFormulario.add(btnActualizar);

        add(panelFormulario, BorderLayout.NORTH);

        modelo = new DefaultTableModel(new String[]{"ID", "Médico", "Fecha", "Hora"}, 0);
        tablaTurnos = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tablaTurnos);

        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.add(scroll, BorderLayout.CENTER);
        panelTabla.add(btnCancelar, BorderLayout.SOUTH);
        add(panelTabla, BorderLayout.CENTER);
    }

    private void cargarMedicos() {
        cbMedicos.removeAllItems();
        List<Medico> medicos = medicoController.listarMedicos();
        for (Medico m : medicos) cbMedicos.addItem(m);
    }

    private void listarTurnos() {
        if (paciente == null || paciente.getIdPaciente() <= 0) {
            JOptionPane.showMessageDialog(this, "Paciente no válido o no autenticado.");
            return;
        }

        modelo.setRowCount(0);
        List<Turno> turnos = turnoController.listarTurnosPorPaciente(paciente.getIdPaciente());
        for (Turno t : turnos) {
            modelo.addRow(new Object[]{
                    t.getIdTurno(),
                    t.getMedico().getNombre(),
                    t.getFecha(),
                    t.getHora()
            });
        }
    }

    private void reservarTurno() {
        Medico medico = (Medico) cbMedicos.getSelectedItem();
        String fecha = txtFecha.getText().trim();
        String hora = txtHora.getText().trim();

        if (medico == null || fecha.isEmpty() || hora.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe completar todos los campos.");
            return;
        }

        Turno turno = new Turno(0, paciente, medico, fecha, hora);
        if (turnoController.crearTurno(turno)) {
            JOptionPane.showMessageDialog(this, "Turno reservado correctamente.");
            listarTurnos();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al reservar turno.");
        }
    }

    private void cancelarTurno() {
        int fila = tablaTurnos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un turno para cancelar.");
            return;
        }

        int id = (int) modelo.getValueAt(fila, 0);
        int confirmar = JOptionPane.showConfirmDialog(this,
                "¿Desea cancelar este turno?", "Confirmación", JOptionPane.YES_NO_OPTION);

        if (confirmar == JOptionPane.YES_OPTION) {
            if (turnoController.eliminarTurno(id)) {
                JOptionPane.showMessageDialog(this, "Turno cancelado correctamente.");
                listarTurnos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al cancelar turno.");
            }
        }
    }

    private void limpiarCampos() {
        txtFecha.setText("");
        txtHora.setText("");
        cbMedicos.setSelectedIndex(0);
    }
}

