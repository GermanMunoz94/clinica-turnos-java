package com.mycompany.proyectoparrinomunoz.vistas;

import com.formdev.flatlaf.FlatLightLaf;
import com.mycompany.proyectoparrinomunoz.Controller.DiagnosticoController;
import com.mycompany.proyectoparrinomunoz.Controller.MedicoController;
import com.mycompany.proyectoparrinomunoz.Controller.PacienteController;
import com.mycompany.proyectoparrinomunoz.Controller.TurnoController;
import com.mycompany.proyectoparrinomunoz.Entity.Diagnostico;
import com.mycompany.proyectoparrinomunoz.Entity.Medico;
import com.mycompany.proyectoparrinomunoz.Entity.Paciente;
import com.mycompany.proyectoparrinomunoz.Entity.Turno;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DiagnosticoVista extends JFrame {

    private final DiagnosticoController diagnosticoController;
    private final PacienteController pacienteController;
    private final MedicoController medicoController;
    private final TurnoController turnoController;

    private JTable tablaDiagnosticos;
    private DefaultTableModel modelo;
    private JComboBox<Paciente> cmbPaciente;
    private JComboBox<Medico> cmbMedico;
    private JComboBox<Turno> cmbTurno;
    private JTextArea txtDescripcion;

    public DiagnosticoVista() {
        FlatLightLaf.setup();
        diagnosticoController = new DiagnosticoController();
        pacienteController = new PacienteController();
        medicoController = new MedicoController();
        turnoController = new TurnoController();

        initUI();
        cargarCombos();
        listarDiagnosticos();
    }

    private void initUI() {
        setTitle("Gestión de Diagnósticos");
        setSize(950, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel panelFormulario = new JPanel(new GridLayout(5, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        cmbPaciente = new JComboBox<>();
        cmbMedico = new JComboBox<>();
        cmbTurno = new JComboBox<>();
        txtDescripcion = new JTextArea(3, 20);

        JButton btnAgregar = new JButton("Agregar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");

        btnAgregar.addActionListener(e -> agregarDiagnostico());
        btnActualizar.addActionListener(e -> actualizarDiagnostico());
        btnEliminar.addActionListener(e -> eliminarDiagnostico());

        panelFormulario.add(new JLabel("Paciente:"));
        panelFormulario.add(cmbPaciente);
        panelFormulario.add(new JLabel("Médico:"));
        panelFormulario.add(cmbMedico);
        panelFormulario.add(new JLabel("Turno:"));
        panelFormulario.add(cmbTurno);
        panelFormulario.add(new JLabel("Descripción:"));
        panelFormulario.add(new JScrollPane(txtDescripcion));
        panelFormulario.add(btnAgregar);
        panelFormulario.add(btnActualizar);

        add(panelFormulario, BorderLayout.NORTH);

        modelo = new DefaultTableModel(new String[]{
                "ID", "Paciente", "Médico", "Turno", "Descripción"
        }, 0);
        tablaDiagnosticos = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tablaDiagnosticos);

        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.add(scroll, BorderLayout.CENTER);
        panelInferior.add(btnEliminar, BorderLayout.SOUTH);

        add(panelInferior, BorderLayout.CENTER);

        // === Habilitar selección y carga de campos ===
        tablaDiagnosticos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaDiagnosticos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int fila = tablaDiagnosticos.getSelectedRow();
                if (fila != -1) {
                    txtDescripcion.setText(modelo.getValueAt(fila, 4).toString());

                    String pacienteStr = modelo.getValueAt(fila, 1).toString();
                    String medicoStr = modelo.getValueAt(fila, 2).toString();
                    String turnoStr = modelo.getValueAt(fila, 3).toString();

                    // Seleccionar paciente
                    for (int i = 0; i < cmbPaciente.getItemCount(); i++) {
                        Paciente p = cmbPaciente.getItemAt(i);
                        if (p.toString().equals(pacienteStr)) {
                            cmbPaciente.setSelectedIndex(i);
                            break;
                        }
                    }

                    // Seleccionar médico
                    for (int i = 0; i < cmbMedico.getItemCount(); i++) {
                        Medico m = cmbMedico.getItemAt(i);
                        if (m.toString().equals(medicoStr)) {
                            cmbMedico.setSelectedIndex(i);
                            break;
                        }
                    }

                    // Seleccionar turno
                    for (int i = 0; i < cmbTurno.getItemCount(); i++) {
                        Turno t = cmbTurno.getItemAt(i);
                        if (t.toString().equals(turnoStr)) {
                            cmbTurno.setSelectedIndex(i);
                            break;
                        }
                    }
                }
            }
        });

    }

    private void cargarCombos() {
        cmbPaciente.removeAllItems();
        for (Paciente p : pacienteController.listarPacientes()) {
            cmbPaciente.addItem(p);
        }

        cmbMedico.removeAllItems();
        for (Medico m : medicoController.listarMedicos()) {
            cmbMedico.addItem(m);
        }

        cmbTurno.removeAllItems();
        for (Turno t : turnoController.listarTurnos()) {
            cmbTurno.addItem(t);
        }
    }

    private void listarDiagnosticos() {
        modelo.setRowCount(0);
        List<Diagnostico> diagnosticos = diagnosticoController.listarDiagnosticos();

        for (Diagnostico d : diagnosticos) {
            modelo.addRow(new Object[]{
                    d.getIdDiagnostico(),
                    d.getPaciente() != null ? d.getPaciente().toString() : "Sin asignar",
                    d.getMedico() != null ? d.getMedico().toString() : "Sin asignar",
                    (d.getTurno() != null) ? d.getTurno().toString() : "Sin turno",
                    d.getDescripcion()
            });
        }
    }

    private void agregarDiagnostico() {
        Paciente paciente = (Paciente) cmbPaciente.getSelectedItem();
        Medico medico = (Medico) cmbMedico.getSelectedItem();
        Turno turno = (Turno) cmbTurno.getSelectedItem();
        String descripcion = txtDescripcion.getText().trim();

        if (paciente == null || medico == null || turno == null || descripcion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe completar todos los campos antes de continuar.");
            return;
        }

        Diagnostico d = new Diagnostico(0, paciente, medico, descripcion);
        if (diagnosticoController.crearDiagnostico(d, turno.getIdTurno())) {
            JOptionPane.showMessageDialog(this, "Diagnóstico agregado correctamente.");
            listarDiagnosticos();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al agregar diagnóstico.");
        }
    }

    private void actualizarDiagnostico() {
        int fila = tablaDiagnosticos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un diagnóstico para actualizar.");
            return;
        }

        int id = (int) modelo.getValueAt(fila, 0);
        Paciente paciente = (Paciente) cmbPaciente.getSelectedItem();
        Medico medico = (Medico) cmbMedico.getSelectedItem();
        String descripcion = txtDescripcion.getText().trim();

        Diagnostico d = new Diagnostico(id, paciente, medico, descripcion);
        if (diagnosticoController.actualizarDiagnostico(d)) {
            JOptionPane.showMessageDialog(this, "Diagnóstico actualizado correctamente.");
            listarDiagnosticos();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar diagnóstico.");
        }
    }

    private void eliminarDiagnostico() {
        int fila = tablaDiagnosticos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un diagnóstico para eliminar.");
            return;
        }

        int id = (int) modelo.getValueAt(fila, 0);
        int confirmar = JOptionPane.showConfirmDialog(this,
                "¿Desea eliminar este diagnóstico?", "Confirmación", JOptionPane.YES_NO_OPTION);

        if (confirmar == JOptionPane.YES_OPTION) {
            if (diagnosticoController.eliminarDiagnostico(id)) {
                JOptionPane.showMessageDialog(this, "Diagnóstico eliminado correctamente.");
                listarDiagnosticos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar diagnóstico.");
            }
        }
    }

    private void limpiarCampos() {
        txtDescripcion.setText("");
    }
}
