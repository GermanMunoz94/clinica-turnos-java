package com.mycompany.proyectoparrinomunoz.vistas;

import com.formdev.flatlaf.FlatLightLaf;
import com.mycompany.proyectoparrinomunoz.Controller.PacienteController;
import com.mycompany.proyectoparrinomunoz.Entity.Paciente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PacienteVista extends JFrame {

    private final PacienteController pacienteController;
    private JTable tablaPacientes;
    private DefaultTableModel modelo;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtDni;
    private JTextField txtTelefono;
    private JTextField txtEmail;

    public PacienteVista() {
        FlatLightLaf.setup();
        pacienteController = new PacienteController();
        initUI();
        listarPacientes();
    }

    private void initUI() {
        setTitle("Gestión de Pacientes");
        setSize(850, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelFormulario = new JPanel(new GridLayout(6, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        txtNombre = new JTextField();
        txtApellido = new JTextField();
        txtDni = new JTextField();
        txtTelefono = new JTextField();
        txtEmail = new JTextField();

        JButton btnAgregar = new JButton("Agregar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");

        btnAgregar.addActionListener(e -> agregarPaciente());
        btnActualizar.addActionListener(e -> actualizarPaciente());
        btnEliminar.addActionListener(e -> eliminarPaciente());

        panelFormulario.add(new JLabel("Nombre:"));
        panelFormulario.add(txtNombre);
        panelFormulario.add(new JLabel("Apellido:"));
        panelFormulario.add(txtApellido);
        panelFormulario.add(new JLabel("DNI:"));
        panelFormulario.add(txtDni);
        panelFormulario.add(new JLabel("Teléfono:"));
        panelFormulario.add(txtTelefono);
        panelFormulario.add(new JLabel("Email:"));
        panelFormulario.add(txtEmail);
        panelFormulario.add(btnAgregar);
        panelFormulario.add(btnActualizar);

        add(panelFormulario, BorderLayout.NORTH);

        modelo = new DefaultTableModel(
                new String[]{"ID", "Nombre", "Apellido", "DNI", "Teléfono", "Email"}, 0
        ) {
            // Evita que las celdas sean editables
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaPacientes = new JTable(modelo);
        tablaPacientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaPacientes.getSelectionModel().addListSelectionListener(e -> cargarCamposDesdeTabla());
        JScrollPane scroll = new JScrollPane(tablaPacientes);

        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.add(scroll, BorderLayout.CENTER);
        panelInferior.add(btnEliminar, BorderLayout.SOUTH);

        add(panelInferior, BorderLayout.CENTER);
    }

    private void listarPacientes() {
        modelo.setRowCount(0);
        List<Paciente> pacientes = pacienteController.listarPacientes();
        for (Paciente p : pacientes) {
            modelo.addRow(new Object[]{
                    p.getIdPaciente(),
                    p.getNombre(),
                    p.getApellido(),
                    p.getDni(),
                    p.getTelefono(),
                    p.getEmail()
            });
        }
    }

    private void agregarPaciente() {
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String dni = txtDni.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String email = txtEmail.getText().trim();

        if (nombre.isEmpty() || apellido.isEmpty() || dni.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe completar los campos obligatorios: nombre, apellido y DNI.");
            return;
        }

        Paciente p = new Paciente(0, nombre, apellido, dni, telefono, email);
        if (pacienteController.crearPaciente(p)) {
            JOptionPane.showMessageDialog(this, "Paciente agregado correctamente.");
            listarPacientes();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al agregar paciente.");
        }
    }

    private void actualizarPaciente() {
        int fila = tablaPacientes.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un paciente para actualizar.");
            return;
        }

        int id = (int) modelo.getValueAt(fila, 0);
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String dni = txtDni.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String email = txtEmail.getText().trim();

        Paciente p = new Paciente(id, nombre, apellido, dni, telefono, email);
        if (pacienteController.actualizarPaciente(p)) {
            JOptionPane.showMessageDialog(this, "Paciente actualizado correctamente.");
            listarPacientes();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar paciente.");
        }
    }

    private void eliminarPaciente() {
        int fila = tablaPacientes.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un paciente para eliminar.");
            return;
        }

        int id = (int) modelo.getValueAt(fila, 0);
        int confirmar = JOptionPane.showConfirmDialog(this,
                "¿Desea eliminar este paciente?", "Confirmación", JOptionPane.YES_NO_OPTION);

        if (confirmar == JOptionPane.YES_OPTION) {
            if (pacienteController.eliminarPaciente(id)) {
                JOptionPane.showMessageDialog(this, "Paciente eliminado correctamente.");
                listarPacientes();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar paciente.");
            }
        }
    }

    private void cargarCamposDesdeTabla() {
        int fila = tablaPacientes.getSelectedRow();
        if (fila >= 0) {
            txtNombre.setText(modelo.getValueAt(fila, 1).toString());
            txtApellido.setText(modelo.getValueAt(fila, 2).toString());
            txtDni.setText(modelo.getValueAt(fila, 3).toString());
            txtTelefono.setText(modelo.getValueAt(fila, 4) != null ? modelo.getValueAt(fila, 4).toString() : "");
            txtEmail.setText(modelo.getValueAt(fila, 5) != null ? modelo.getValueAt(fila, 5).toString() : "");
        }
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtDni.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        tablaPacientes.clearSelection();
    }
}
