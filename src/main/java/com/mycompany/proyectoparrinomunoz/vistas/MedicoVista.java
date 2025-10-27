package com.mycompany.proyectoparrinomunoz.vistas;

import com.formdev.flatlaf.FlatLightLaf;
import com.mycompany.proyectoparrinomunoz.Controller.MedicoController;
import com.mycompany.proyectoparrinomunoz.Entity.Medico;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MedicoVista extends JFrame {

    private final MedicoController medicoController;
    private JTable tablaMedicos;
    private DefaultTableModel modelo;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtEspecialidad;
    private JTextField txtTelefono;

    public MedicoVista() {
        FlatLightLaf.setup();
        medicoController = new MedicoController();
        initUI();
        listarMedicos();
    }

    private void initUI() {
        setTitle("Gestión de Médicos");
        setSize(750, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelFormulario = new JPanel(new GridLayout(5, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        txtNombre = new JTextField();
        txtApellido = new JTextField();
        txtEspecialidad = new JTextField();
        txtTelefono = new JTextField();

        JButton btnAgregar = new JButton("Agregar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");

        btnAgregar.addActionListener(e -> agregarMedico());
        btnActualizar.addActionListener(e -> actualizarMedico());
        btnEliminar.addActionListener(e -> eliminarMedico());

        panelFormulario.add(new JLabel("Nombre:"));
        panelFormulario.add(txtNombre);
        panelFormulario.add(new JLabel("Apellido:"));
        panelFormulario.add(txtApellido);
        panelFormulario.add(new JLabel("Especialidad:"));
        panelFormulario.add(txtEspecialidad);
        panelFormulario.add(new JLabel("Teléfono:"));
        panelFormulario.add(txtTelefono);
        panelFormulario.add(btnAgregar);
        panelFormulario.add(btnActualizar);

        add(panelFormulario, BorderLayout.NORTH);

        modelo = new DefaultTableModel(new String[]{"ID", "Nombre", "Apellido", "Especialidad", "Teléfono"}, 0);
        tablaMedicos = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tablaMedicos);

        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.add(scroll, BorderLayout.CENTER);
        panelInferior.add(btnEliminar, BorderLayout.SOUTH);

        add(panelInferior, BorderLayout.CENTER);

        // === Habilitar selección y carga de campos ===
        tablaMedicos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaMedicos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int fila = tablaMedicos.getSelectedRow();
                if (fila != -1) {
                    txtNombre.setText(modelo.getValueAt(fila, 1).toString());
                    txtApellido.setText(modelo.getValueAt(fila, 2).toString());
                    txtEspecialidad.setText(modelo.getValueAt(fila, 3).toString());
                    txtTelefono.setText(modelo.getValueAt(fila, 4).toString());
                }
            }
        });

    }

    private void listarMedicos() {
        modelo.setRowCount(0);
        List<Medico> medicos = medicoController.listarMedicos();
        for (Medico m : medicos) {
            modelo.addRow(new Object[]{
                    m.getIdMedico(),
                    m.getNombre(),
                    m.getApellido(),
                    m.getEspecialidad(),
                    m.getTelefono()
            });
        }
    }

    private void agregarMedico() {
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String especialidad = txtEspecialidad.getText().trim();
        String telefono = txtTelefono.getText().trim();

        if (nombre.isEmpty() || apellido.isEmpty() || especialidad.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe completar todos los campos obligatorios.");
            return;
        }

        Medico m = new Medico(0, nombre, apellido, especialidad, telefono);
        if (medicoController.crearMedico(m)) {
            JOptionPane.showMessageDialog(this, "Médico agregado correctamente.");
            listarMedicos();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al agregar médico.");
        }
    }

    private void actualizarMedico() {
        int fila = tablaMedicos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un médico para actualizar.");
            return;
        }

        int id = (int) modelo.getValueAt(fila, 0);
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String especialidad = txtEspecialidad.getText().trim();
        String telefono = txtTelefono.getText().trim();

        Medico m = new Medico(id, nombre, apellido, especialidad, telefono);
        if (medicoController.actualizarMedico(m)) {
            JOptionPane.showMessageDialog(this, "Médico actualizado correctamente.");
            listarMedicos();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar médico.");
        }
    }

    private void eliminarMedico() {
        int fila = tablaMedicos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un médico para eliminar.");
            return;
        }

        int id = (int) modelo.getValueAt(fila, 0);
        int confirmar = JOptionPane.showConfirmDialog(this,
                "¿Desea eliminar este médico?", "Confirmación", JOptionPane.YES_NO_OPTION);

        if (confirmar == JOptionPane.YES_OPTION) {
            if (medicoController.eliminarMedico(id)) {
                JOptionPane.showMessageDialog(this, "Médico eliminado correctamente.");
                listarMedicos();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar médico.");
            }
        }
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtEspecialidad.setText("");
        txtTelefono.setText("");
    }
}
