package com.mycompany.proyectoparrinomunoz.vistas;

import com.mycompany.proyectoparrinomunoz.Controller.PacienteController;
import com.mycompany.proyectoparrinomunoz.Entity.Paciente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PacienteVista extends JFrame {
    private JTextField txtNombre, txtApellido, txtDni, txtEmail, txtTelefono;
    private JButton btnAgregar, btnActualizar, btnEliminar;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    private final PacienteController pacienteController;

    public PacienteVista(PacienteController pacienteController) {
        this.pacienteController = pacienteController;

        setTitle("Gestión de Pacientes");
        setSize(700, 500);
        setLocationRelativeTo(null);

        // === Formulario ===
        JPanel form = new JPanel(new GridLayout(5, 2, 10, 10));
        form.setBorder(BorderFactory.createTitledBorder("Datos del Paciente"));

        txtNombre = new JTextField();
        txtApellido = new JTextField();
        txtDni = new JTextField();
        txtEmail = new JTextField();
        txtTelefono = new JTextField();

        form.add(new JLabel("Nombre:")); form.add(txtNombre);
        form.add(new JLabel("Apellido:")); form.add(txtApellido);
        form.add(new JLabel("DNI:")); form.add(txtDni);
        form.add(new JLabel("Email:")); form.add(txtEmail);
        form.add(new JLabel("Teléfono:")); form.add(txtTelefono);

        // === Botones ===
        btnAgregar = new JButton("Agregar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");

        JPanel acciones = new JPanel(new FlowLayout());
        acciones.add(btnAgregar);
        acciones.add(btnActualizar);
        acciones.add(btnEliminar);

        // === Tabla ===
        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Nombre", "Apellido", "DNI", "Email", "Teléfono"}, 0);
        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Lista de Pacientes"));

        // === Layout ===
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(form, BorderLayout.CENTER);
        topPanel.add(acciones, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        // === Eventos ===
        btnAgregar.addActionListener(e -> agregarPaciente());
        btnActualizar.addActionListener(e -> actualizarPaciente());
        btnEliminar.addActionListener(e -> eliminarPaciente());

        // dentro de tu constructor, después de crear la tabla:
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() != -1) {
                int fila = tabla.getSelectedRow();
                txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
                txtApellido.setText(modeloTabla.getValueAt(fila, 2).toString());
                txtDni.setText(modeloTabla.getValueAt(fila, 3).toString());
                txtEmail.setText(modeloTabla.getValueAt(fila, 4).toString());
                txtTelefono.setText(modeloTabla.getValueAt(fila, 5).toString());
            }
        });

        cargarPacientes();
    }

    // ===== Métodos internos =====

    private void cargarPacientes() {
        modeloTabla.setRowCount(0);
        List<Paciente> lista = pacienteController.listarPacientes();
        for (Paciente p : lista) {
            modeloTabla.addRow(new Object[]{p.getIdPaciente(), p.getNombre(), p.getApellido(),
                    p.getDni(), p.getEmail(), p.getTelefono()});
        }
    }

    private void agregarPaciente() {
        Paciente p = new Paciente(0,
                txtNombre.getText(),
                txtApellido.getText(),
                txtDni.getText(),
                txtEmail.getText(),
                txtTelefono.getText());

        boolean ok = pacienteController.agregarPaciente(p);
        if (ok) {
            JOptionPane.showMessageDialog(this, "Paciente agregado correctamente");
            limpiarFormulario();
            cargarPacientes();
        } else {
            JOptionPane.showMessageDialog(this, "Error al agregar paciente");
        }
    }

    private void actualizarPaciente() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un paciente de la tabla");
            return;
        }

        int id = (int) modeloTabla.getValueAt(fila, 0);
        Paciente p = new Paciente(id,
                txtNombre.getText(),
                txtApellido.getText(),
                txtDni.getText(),
                txtEmail.getText(),
                txtTelefono.getText());

        boolean ok = pacienteController.actualizarPaciente(p);
        if (ok) {
            JOptionPane.showMessageDialog(this, "Paciente actualizado");
            limpiarFormulario();
            cargarPacientes();
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar");
        }


    }

    private void eliminarPaciente() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un paciente de la tabla");
            return;
        }

        int id = (int) modeloTabla.getValueAt(fila, 0);
        boolean ok = pacienteController.eliminarPaciente(id);
        if (ok) {
            JOptionPane.showMessageDialog(this, "Paciente eliminado");
            cargarPacientes();
        } else {
            JOptionPane.showMessageDialog(this, "Error al eliminar");
        }
    }

    private void limpiarFormulario() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtDni.setText("");
        txtEmail.setText("");
        txtTelefono.setText("");
    }
}
