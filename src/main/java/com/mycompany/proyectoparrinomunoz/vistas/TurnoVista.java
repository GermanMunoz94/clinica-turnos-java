package com.mycompany.proyectoparrinomunoz.vistas;

import com.mycompany.proyectoparrinomunoz.Controller.TurnoController;
import com.mycompany.proyectoparrinomunoz.Controller.PacienteController;
import com.mycompany.proyectoparrinomunoz.Controller.MedicoController;
import com.mycompany.proyectoparrinomunoz.Entity.Turno;
import com.mycompany.proyectoparrinomunoz.Entity.Paciente;
import com.mycompany.proyectoparrinomunoz.Entity.Medico;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TurnoVista extends JFrame {
    private JComboBox<Paciente> cbPacientes;
    private JComboBox<Medico> cbMedicos;
    private JTextField txtFecha, txtHora;
    private JButton btnAgregar, btnActualizar, btnEliminar;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    private final TurnoController turnoController;
    private final PacienteController pacienteController;
    private final MedicoController medicoController;

    public TurnoVista(TurnoController turnoController,
                      PacienteController pacienteController,
                      MedicoController medicoController) {
        this.turnoController = turnoController;
        this.pacienteController = pacienteController;
        this.medicoController = medicoController;

        setTitle("Gestión de Turnos");
        setSize(750, 500);
        setLocationRelativeTo(null);

        // === Formulario ===
        JPanel form = new JPanel(new GridLayout(4, 2, 10, 10));
        form.setBorder(BorderFactory.createTitledBorder("Datos del Turno"));

        cbPacientes = new JComboBox<>();
        cbMedicos = new JComboBox<>();
        txtFecha = new JTextField("yyyy-mm-dd");
        txtHora = new JTextField("HH:mm");

        form.add(new JLabel("Paciente:")); form.add(cbPacientes);
        form.add(new JLabel("Médico:")); form.add(cbMedicos);
        form.add(new JLabel("Fecha:")); form.add(txtFecha);
        form.add(new JLabel("Hora:")); form.add(txtHora);

        // === Botones ===
        btnAgregar = new JButton("Agregar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");

        JPanel acciones = new JPanel(new FlowLayout());
        acciones.add(btnAgregar);
        acciones.add(btnActualizar);
        acciones.add(btnEliminar);

        // === Tabla ===
        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Paciente", "Médico", "Fecha", "Hora"}, 0);
        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Lista de Turnos"));

        // === Layout ===
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(form, BorderLayout.CENTER);
        topPanel.add(acciones, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        // === Eventos ===
        btnAgregar.addActionListener(e -> agregarTurno());
        btnActualizar.addActionListener(e -> actualizarTurno());
        btnEliminar.addActionListener(e -> eliminarTurno());

        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() != -1) {
                int fila = tabla.getSelectedRow();
                txtFecha.setText(modeloTabla.getValueAt(fila, 3).toString());
                txtHora.setText(modeloTabla.getValueAt(fila, 4).toString());

                // Selecciona automáticamente en los combos
                String pacienteNombre = modeloTabla.getValueAt(fila, 1).toString();
                for (int i = 0; i < cbPacientes.getItemCount(); i++) {
                    Paciente p = cbPacientes.getItemAt(i);
                    if ((p.getNombre() + " " + p.getApellido()).equals(pacienteNombre)) {
                        cbPacientes.setSelectedIndex(i);
                        break;
                    }
                }

                String medicoNombre = modeloTabla.getValueAt(fila, 2).toString();
                for (int i = 0; i < cbMedicos.getItemCount(); i++) {
                    Medico m = cbMedicos.getItemAt(i);
                    if ((m.getNombre() + " " + m.getApellido()).equals(medicoNombre)) {
                        cbMedicos.setSelectedIndex(i);
                        break;
                    }
                }
            }
        });

        cargarCombos();
        cargarTurnos();
    }

    // Métodos internos

    private void cargarCombos() {
        cbPacientes.removeAllItems();
        List<Paciente> pacientes = pacienteController.listarPacientes();
        for (Paciente p : pacientes) cbPacientes.addItem(p);

        cbMedicos.removeAllItems();
        List<Medico> medicos = medicoController.listarMedicos();
        for (Medico m : medicos) cbMedicos.addItem(m);
    }

    private void cargarTurnos() {
        modeloTabla.setRowCount(0);
        List<Turno> lista = turnoController.listarTurnos();
        for (Turno t : lista) {
            modeloTabla.addRow(new Object[]{t.getIdTurno(),
                    t.getPaciente().getNombre() + " " + t.getPaciente().getApellido(),
                    t.getMedico().getNombre() + " " + t.getMedico().getApellido(),
                    t.getFecha(),
                    t.getHora()});
        }
    }

    private void agregarTurno() {
        Paciente paciente = (Paciente) cbPacientes.getSelectedItem();
        Medico medico = (Medico) cbMedicos.getSelectedItem();
        Turno t = new Turno(0, paciente, medico, txtFecha.getText(), txtHora.getText());

        boolean ok = turnoController.agregarTurno(t);
        if (ok) {
            JOptionPane.showMessageDialog(this, "Turno agregado");
            cargarTurnos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al agregar turno");
        }
    }

    private void actualizarTurno() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un turno de la tabla");
            return;
        }

        int id = (int) modeloTabla.getValueAt(fila, 0);
        Paciente paciente = (Paciente) cbPacientes.getSelectedItem();
        Medico medico = (Medico) cbMedicos.getSelectedItem();
        Turno t = new Turno(id, paciente, medico, txtFecha.getText(), txtHora.getText());

        boolean ok = turnoController.actualizarTurno(t);
        if (ok) {
            JOptionPane.showMessageDialog(this, "Turno actualizado");
            cargarTurnos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar turno");
        }
    }

    private void eliminarTurno() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un turno de la tabla");
            return;
        }

        int id = (int) modeloTabla.getValueAt(fila, 0);
        boolean ok = turnoController.eliminarTurno(id);
        if (ok) {
            JOptionPane.showMessageDialog(this, "Turno eliminado");
            cargarTurnos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al eliminar turno");
        }
    }
}
