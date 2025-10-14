package com.mycompany.proyectoparrinomunoz.vistas;

import com.mycompany.proyectoparrinomunoz.Controller.DiagnosticoController;
import com.mycompany.proyectoparrinomunoz.Entity.Diagnostico;
import com.mycompany.proyectoparrinomunoz.Entity.Turno;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DiagnosticoVista extends JFrame {

    private final DiagnosticoController diagnosticoController;
    private final Turno turno;

    private JTextField txtFecha;
    private JTextArea txtDescripcion;
    private JTextArea txtReceta;
    private JButton btnGuardar;
    private JButton btnCancelar;

    public DiagnosticoVista(Turno turno, DiagnosticoController diagnosticoController) {
        this.turno = turno;
        this.diagnosticoController = diagnosticoController;

        setTitle("Registrar Diagnóstico");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));

        // ---- ENCABEZADO ----
        JLabel lblTitulo = new JLabel("Diagnóstico de " +
                turno.getPaciente().getNombre() + " " + turno.getPaciente().getApellido(),
                SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(lblTitulo, BorderLayout.NORTH);

        // ---- FORMULARIO ----
        JPanel panelForm = new JPanel(new GridLayout(3, 1, 10, 10));
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Fecha (solo lectura)
        txtFecha = new JTextField(LocalDate.now().toString());
        txtFecha.setEditable(false);
        JPanel panelFecha = new JPanel(new BorderLayout());
        panelFecha.add(new JLabel("Fecha del Diagnóstico:"), BorderLayout.NORTH);
        panelFecha.add(txtFecha, BorderLayout.CENTER);
        panelForm.add(panelFecha);

        // Descripción
        txtDescripcion = new JTextArea(5, 30);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        JScrollPane scrollDesc = new JScrollPane(txtDescripcion);
        JPanel panelDesc = new JPanel(new BorderLayout());
        panelDesc.add(new JLabel("Descripción del Diagnóstico:"), BorderLayout.NORTH);
        panelDesc.add(scrollDesc, BorderLayout.CENTER);
        panelForm.add(panelDesc);

        // Receta
        txtReceta = new JTextArea(4, 30);
        txtReceta.setLineWrap(true);
        txtReceta.setWrapStyleWord(true);
        JScrollPane scrollReceta = new JScrollPane(txtReceta);
        JPanel panelReceta = new JPanel(new BorderLayout());
        panelReceta.add(new JLabel("Receta / Tratamiento:"), BorderLayout.NORTH);
        panelReceta.add(scrollReceta, BorderLayout.CENTER);
        panelForm.add(panelReceta);

        add(panelForm, BorderLayout.CENTER);

        // ---- BOTONES ----
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        add(panelBotones, BorderLayout.SOUTH);

        // ---- ACCIONES ----
        btnGuardar.addActionListener(e -> guardarDiagnostico());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void guardarDiagnostico() {
        String descripcion = txtDescripcion.getText().trim();
        String receta = txtReceta.getText().trim();

        if (descripcion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar la descripción del diagnóstico.");
            return;
        }

        /*Diagnostico diag = new Diagnostico(0, turno, txtFecha.getText(), descripcion, receta);*/
        Diagnostico diag = new Diagnostico(0, turno, LocalDateTime.now(), descripcion, receta);


        boolean ok = diagnosticoController.crearDiagnostico(diag);
        if (ok) {
            JOptionPane.showMessageDialog(this, "Diagnóstico guardado correctamente.");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar el diagnóstico.");
        }
    }
}

