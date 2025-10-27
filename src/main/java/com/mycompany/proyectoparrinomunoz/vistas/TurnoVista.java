package com.mycompany.proyectoparrinomunoz.vistas;

import com.formdev.flatlaf.FlatLightLaf;
import com.mycompany.proyectoparrinomunoz.Controller.TurnoController;
import com.mycompany.proyectoparrinomunoz.Entity.Turno;
import com.mycompany.proyectoparrinomunoz.Entity.Paciente;
import com.mycompany.proyectoparrinomunoz.Entity.Medico;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TurnoVista extends JFrame {

    private final TurnoController turnoController;
    private JComboBox<Paciente> cbPacientes;
    private JComboBox<Medico> cbMedicos;
    private JTextField txtFecha;
    private JTextField txtHora;
    private JTable tablaTurnos;
    private DefaultTableModel modelo;

    public TurnoVista() {
        FlatLightLaf.setup();
        turnoController = new TurnoController();
        initUI();
        cargarCombos();
        listarTurnos();
    }

    private void initUI() {
        setTitle("Gestor de Turnos");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        cbPacientes = new JComboBox<>();
        cbMedicos = new JComboBox<>();
        txtFecha = new JTextField();
        txtHora = new JTextField();

        JButton btnAgregar = new JButton("Agregar Turno");
        JButton btnEliminar = new JButton("Eliminar Turno");
        JButton btnActualizar = new JButton("Actualizar Turno");

        btnAgregar.addActionListener(e -> agregarTurno());
        btnEliminar.addActionListener(e -> eliminarTurno());
        btnActualizar.addActionListener(e -> actualizarTurno());

        panel.add(new JLabel("Paciente:"));
        panel.add(cbPacientes);
        panel.add(new JLabel("Médico:"));
        panel.add(cbMedicos);
        panel.add(new JLabel("Fecha (YYYY-MM-DD):"));
        panel.add(txtFecha);
        panel.add(new JLabel("Hora (HH:MM):"));
        panel.add(txtHora);
        panel.add(btnAgregar);
        panel.add(btnEliminar);
        panel.add(btnActualizar);

        modelo = new DefaultTableModel(new String[]{"ID", "Paciente", "Médico", "Fecha", "Hora"}, 0);
        tablaTurnos = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tablaTurnos);

        add(panel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        // === Habilitar selección y carga de campos ===
        tablaTurnos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaTurnos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int fila = tablaTurnos.getSelectedRow();
                if (fila != -1) {
                    txtFecha.setText(modelo.getValueAt(fila, 3).toString());
                    txtHora.setText(modelo.getValueAt(fila, 4).toString());

                    String pacienteStr = modelo.getValueAt(fila, 1).toString();
                    String medicoStr = modelo.getValueAt(fila, 2).toString();

                    // Selecciona el paciente correspondiente
                    for (int i = 0; i < cbPacientes.getItemCount(); i++) {
                        Paciente p = cbPacientes.getItemAt(i);
                        String nombreCompleto = p.getNombre() + " " + p.getApellido();
                        if (nombreCompleto.equals(pacienteStr)) {
                            cbPacientes.setSelectedIndex(i);
                            break;
                        }
                    }

                    // Selecciona el médico correspondiente
                    for (int i = 0; i < cbMedicos.getItemCount(); i++) {
                        Medico m = cbMedicos.getItemAt(i);
                        String nombreCompleto = m.getNombre() + " " + m.getApellido();
                        if (nombreCompleto.equals(medicoStr)) {
                            cbMedicos.setSelectedIndex(i);
                            break;
                        }
                    }
                }
            }
        });

    }

    private void cargarCombos() {
        cbPacientes.removeAllItems();
        cbMedicos.removeAllItems();

        List<Paciente> pacientes = turnoController.listarPacientes();
        List<Medico> medicos = turnoController.listarMedicos();

        if (pacientes != null) {
            for (Paciente p : pacientes) cbPacientes.addItem(p);
        }
        if (medicos != null) {
            for (Medico m : medicos) cbMedicos.addItem(m);
        }
    }

    private void listarTurnos() {
        modelo.setRowCount(0);
        List<Turno> turnos = turnoController.listarTurnos();

        for (Turno t : turnos) {
            modelo.addRow(new Object[]{
                    t.getIdTurno(),
                    t.getPaciente().getNombre() + " " + t.getPaciente().getApellido(),
                    t.getMedico().getNombre() + " " + t.getMedico().getApellido(),
                    t.getFecha(),
                    t.getHora()
            });
        }
    }

    private void agregarTurno() {
        Paciente paciente = (Paciente) cbPacientes.getSelectedItem();
        Medico medico = (Medico) cbMedicos.getSelectedItem();
        String fecha = txtFecha.getText().trim();
        String hora = txtHora.getText().trim();

        if (paciente == null || medico == null || fecha.isEmpty() || hora.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe completar todos los campos.");
            return;
        }

        Turno turno = new Turno(0, paciente, medico, fecha, hora);
        boolean ok = turnoController.crearTurno(turno);

        if (ok) {
            JOptionPane.showMessageDialog(this, "Turno agregado correctamente.");
            listarTurnos();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al agregar turno.");
        }
    }

    private void eliminarTurno() {
        int fila = tablaTurnos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un turno.");
            return;
        }
        int id = (int) modelo.getValueAt(fila, 0);
        if (turnoController.eliminarTurno(id)) {
            JOptionPane.showMessageDialog(this, "Turno eliminado.");
            listarTurnos();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al eliminar turno.");
        }
    }

    private void actualizarTurno() {
        int fila = tablaTurnos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un turno.");
            return;
        }

        int id = (int) modelo.getValueAt(fila, 0);
        Paciente paciente = (Paciente) cbPacientes.getSelectedItem();
        Medico medico = (Medico) cbMedicos.getSelectedItem();
        String fecha = txtFecha.getText().trim();
        String hora = txtHora.getText().trim();

        Turno t = new Turno(id, paciente, medico, fecha, hora);
        if (turnoController.actualizarTurno(t)) {
            JOptionPane.showMessageDialog(this, "Turno actualizado.");
            listarTurnos();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar turno.");
        }
    }

    private void limpiarCampos() {
        txtFecha.setText("");
        txtHora.setText("");
        cbPacientes.setSelectedIndex(-1);
        cbMedicos.setSelectedIndex(-1);
    }
}
