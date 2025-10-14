package com.mycompany.proyectoparrinomunoz.vistas;

import com.mycompany.proyectoparrinomunoz.Controller.MedicoController;
import com.mycompany.proyectoparrinomunoz.Entity.Medico;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MedicoVista extends JFrame {
    private JTextField txtNombre, txtApellido, txtEspecialidad, txtMatricula;
    private JButton btnAgregar, btnActualizar, btnEliminar;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    private final MedicoController medicoController;

    public MedicoVista(MedicoController medicoController) {
        this.medicoController = medicoController;

        setTitle("Gestión de Médicos");
        setSize(700, 500);
        setLocationRelativeTo(null);

        // === Formulario ===
        JPanel form = new JPanel(new GridLayout(4, 2, 10, 10));
        form.setBorder(BorderFactory.createTitledBorder("Datos del Médico"));

        txtNombre = new JTextField();
        txtApellido = new JTextField();
        txtEspecialidad = new JTextField();
        txtMatricula = new JTextField();

        form.add(new JLabel("Nombre:")); form.add(txtNombre);
        form.add(new JLabel("Apellido:")); form.add(txtApellido);
        form.add(new JLabel("Especialidad:")); form.add(txtEspecialidad);
        form.add(new JLabel("Matrícula:")); form.add(txtMatricula);

        // === Botones ===
        btnAgregar = new JButton("Agregar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");

        JPanel acciones = new JPanel(new FlowLayout());
        acciones.add(btnAgregar);
        acciones.add(btnActualizar);
        acciones.add(btnEliminar);

        // === Tabla ===
        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Nombre", "Apellido", "Especialidad", "Matrícula"}, 0);
        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Lista de Médicos"));

        // === Layout ===
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(form, BorderLayout.CENTER);
        topPanel.add(acciones, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        // === Eventos ===
        btnAgregar.addActionListener(e -> agregarMedico());
        btnActualizar.addActionListener(e -> actualizarMedico());
        btnEliminar.addActionListener(e -> eliminarMedico());

        // Listener para cargar datos seleccionados
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() != -1) {
                int fila = tabla.getSelectedRow();
                txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
                txtApellido.setText(modeloTabla.getValueAt(fila, 2).toString());
                txtEspecialidad.setText(modeloTabla.getValueAt(fila, 3).toString());
                txtMatricula.setText(modeloTabla.getValueAt(fila, 4).toString());
            }
        });

        cargarMedicos();
    }

    // ===== Métodos internos =====

    private void cargarMedicos() {
        modeloTabla.setRowCount(0);
        List<Medico> lista = medicoController.listarMedicos();
        for (Medico m : lista) {
            modeloTabla.addRow(new Object[]{m.getIdMedico(), m.getNombre(), m.getApellido(),
                    m.getEspecialidad(), m.getMatricula()});
        }
    }

    private void agregarMedico() {
        Medico m = new Medico(0,
                txtNombre.getText(),
                txtApellido.getText(),
                txtEspecialidad.getText(),
                txtMatricula.getText());

        boolean ok = medicoController.agregarMedico(m);
        if (ok) {
            JOptionPane.showMessageDialog(this, "Médico agregado correctamente");
            limpiarFormulario();
            cargarMedicos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al agregar médico");
        }
    }

    private void actualizarMedico() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un médico de la tabla");
            return;
        }

        int id = (int) modeloTabla.getValueAt(fila, 0);
        Medico m = new Medico(id,
                txtNombre.getText(),
                txtApellido.getText(),
                txtEspecialidad.getText(),
                txtMatricula.getText());

        boolean ok = medicoController.actualizarMedico(m);
        if (ok) {
            JOptionPane.showMessageDialog(this, "Médico actualizado");
            limpiarFormulario();
            cargarMedicos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar médico");
        }
    }

    private void eliminarMedico() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un médico de la tabla");
            return;
        }

        int id = (int) modeloTabla.getValueAt(fila, 0);
        boolean ok = medicoController.eliminarMedico(id);
        if (ok) {
            JOptionPane.showMessageDialog(this, "Médico eliminado");
            cargarMedicos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al eliminar médico");
        }
    }

    private void limpiarFormulario() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtEspecialidad.setText("");
        txtMatricula.setText("");
    }
}
