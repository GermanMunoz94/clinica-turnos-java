package com.mycompany.proyectoparrinomunoz.vistas;

import com.mycompany.proyectoparrinomunoz.Controller.DiagnosticoController;
import com.mycompany.proyectoparrinomunoz.Controller.MedicoController;
import com.mycompany.proyectoparrinomunoz.Controller.PacienteController;
import com.mycompany.proyectoparrinomunoz.Controller.TurnoController;
import com.mycompany.proyectoparrinomunoz.Entity.Medico;
import com.mycompany.proyectoparrinomunoz.Entity.Paciente;
import com.mycompany.proyectoparrinomunoz.Entity.Turno;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class MedicoTurnoVista extends JFrame {

    private final int idMedico;
    private final TurnoController turnoController;
    private final PacienteController pacienteController;
    private final MedicoController medicoController;

    private JComboBox<Paciente> cbPacientes;
    private DefaultListModel<Turno> modeloTurnos;
    private JList<Turno> listaTurnos;

    private JLabel lblPaciente, lblDni, lblMail, lblTel, lblFecha, lblHora;
    private JTextArea txtDiagnosticoPrevio;
    private JButton btnAgregarDx, btnAtender, btnCancelar;

    public MedicoTurnoVista(int idMedico,
                            TurnoController turnoController,
                            PacienteController pacienteController,
                            MedicoController medicoController) {
        this.idMedico = idMedico;
        this.turnoController = turnoController;
        this.pacienteController = pacienteController;
        this.medicoController = medicoController;

        setTitle("Gestión de Turnos Médicos");
        setSize(900, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initUI();
    }

    // ---------------- UI ----------------

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        ((JComponent) getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));

        add(crearHeader(), BorderLayout.NORTH);
        add(crearCentro(), BorderLayout.CENTER);
        add(crearFooter(), BorderLayout.SOUTH);
    }

    private JComponent crearHeader() {
        JPanel header = new JPanel(new BorderLayout(10, 10));

        Medico med = medicoController.buscarMedicoPorId(idMedico);
        String titulo = (med != null)
                ? "Agenda de " + med.getNombre() + " " + med.getApellido() + " — " + med.getEspecialidad()
                : "Agenda del Médico (ID " + idMedico + ")";
        JLabel lbl = new JLabel(titulo);
        lbl.setFont(lbl.getFont().deriveFont(Font.BOLD, 16f));
        header.add(lbl, BorderLayout.WEST);

        JLabel leyenda = new JLabel("Seleccione un paciente para ver sus turnos y diagnósticos.");
        leyenda.setForeground(Color.DARK_GRAY);
        header.add(leyenda, BorderLayout.EAST);

        return header;
    }

    private JComponent crearCentro() {
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        split.setResizeWeight(0.45);

        JPanel izquierda = new JPanel(new BorderLayout(10, 10));
        izquierda.add(crearPanelPacientes(), BorderLayout.NORTH);
        izquierda.add(crearPanelLista(), BorderLayout.CENTER);

        JPanel derecha = crearPanelDetalle();

        split.setLeftComponent(izquierda);
        split.setRightComponent(derecha);
        return split;
    }

    private JComponent crearPanelPacientes() {
        JPanel p = new JPanel(new BorderLayout(6, 6));
        p.setBorder(BorderFactory.createTitledBorder("Seleccionar Paciente"));

        cbPacientes = new JComboBox<>();
        List<Paciente> pacientes = pacienteController.listarPacientes();
        for (Paciente pac : pacientes) {
            cbPacientes.addItem(pac);
        }

        cbPacientes.addActionListener(e -> {
            Paciente seleccionado = (Paciente) cbPacientes.getSelectedItem();
            if (seleccionado != null) {
                cargarTurnosPorPaciente(seleccionado.getIdPaciente());
            }
        });

        p.add(cbPacientes, BorderLayout.CENTER);
        return p;
    }

    private JComponent crearPanelLista() {
        JPanel p = new JPanel(new BorderLayout(6, 6));
        p.setBorder(BorderFactory.createTitledBorder("Turnos del paciente"));

        modeloTurnos = new DefaultListModel<>();
        listaTurnos = new JList<>(modeloTurnos);
        listaTurnos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaTurnos.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {
            JLabel lab = new JLabel();
            if (value != null) {
                lab.setText(String.format("📅 %s %s — %s (%s)",
                        value.getFecha(), value.getHora(),
                        value.getMedico().getNombre(), value.getMedico().getEspecialidad()));
            }
            lab.setOpaque(true);
            lab.setBorder(new EmptyBorder(6, 8, 6, 8));
            lab.setBackground(isSelected ? list.getSelectionBackground() : Color.WHITE);
            lab.setForeground(isSelected ? list.getSelectionForeground() : Color.DARK_GRAY);
            return lab;
        });

        listaTurnos.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                actualizarDetalle(listaTurnos.getSelectedValue());
            }
        });

        listaTurnos.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && listaTurnos.getSelectedValue() != null) {
                    atenderTurno(listaTurnos.getSelectedValue());
                }
            }
        });

        p.add(new JScrollPane(listaTurnos), BorderLayout.CENTER);
        return p;
    }

    private JPanel crearPanelDetalle() {
        JPanel p = new JPanel(new BorderLayout(8, 8));
        p.setBorder(BorderFactory.createTitledBorder("Detalle del turno"));

        JPanel grid = new JPanel(new GridLayout(0, 2, 8, 8));

        lblPaciente = new JLabel("-");
        lblDni = new JLabel("-");
        lblMail = new JLabel("-");
        lblTel = new JLabel("-");
        lblFecha = new JLabel("-");
        lblHora = new JLabel("-");

        grid.add(negrita("Paciente:")); grid.add(lblPaciente);
        grid.add(negrita("DNI:")); grid.add(lblDni);
        grid.add(negrita("Email:")); grid.add(lblMail);
        grid.add(negrita("Teléfono:")); grid.add(lblTel);
        grid.add(negrita("Fecha:")); grid.add(lblFecha);
        grid.add(negrita("Hora:")); grid.add(lblHora);

        p.add(grid, BorderLayout.NORTH);

        txtDiagnosticoPrevio = new JTextArea(8, 30);
        txtDiagnosticoPrevio.setEditable(false);
        txtDiagnosticoPrevio.setLineWrap(true);
        txtDiagnosticoPrevio.setWrapStyleWord(true);

        JPanel dxPanel = new JPanel(new BorderLayout());
        dxPanel.setBorder(BorderFactory.createTitledBorder("Historial de diagnósticos"));
        dxPanel.add(new JScrollPane(txtDiagnosticoPrevio), BorderLayout.CENTER);

        p.add(dxPanel, BorderLayout.CENTER);
        return p;
    }

    private JComponent crearFooter() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        btnAgregarDx = new JButton("Agregar Diagnóstico");
        btnAtender = new JButton("Atender");
        btnCancelar = new JButton("Cerrar");

        btnAgregarDx.addActionListener(e -> abrirDiagnostico());
        btnAtender.addActionListener(e -> {
            Turno t = listaTurnos.getSelectedValue();
            if (t == null) {
                JOptionPane.showMessageDialog(this, "Seleccioná un turno primero.");
                return;
            }
            atenderTurno(t);
        });
        btnCancelar.addActionListener(e -> dispose());

        footer.add(btnAgregarDx);
        footer.add(btnAtender);
        footer.add(btnCancelar);

        return footer;
    }

    // ---------------- LÓGICA ----------------

    private void cargarTurnosPorPaciente(int idPaciente) {
        modeloTurnos.clear();
        List<Turno> turnosPaciente = turnoController.listarTurnosPorPaciente(idPaciente);
        turnosPaciente.forEach(modeloTurnos::addElement);
        actualizarDetalle(null);
    }

    private void actualizarDetalle(Turno t) {
        if (t == null) {
            lblPaciente.setText("-");
            lblDni.setText("-");
            lblMail.setText("-");
            lblTel.setText("-");
            lblFecha.setText("-");
            lblHora.setText("-");
            txtDiagnosticoPrevio.setText("");
            return;
        }

        Paciente p = t.getPaciente();

        lblPaciente.setText(p.getNombre() + " " + p.getApellido());
        lblDni.setText(p.getDni());
        lblMail.setText(p.getEmail());
        lblTel.setText(p.getTelefono());
        lblFecha.setText(t.getFecha());
        lblHora.setText(t.getHora());

        DiagnosticoController dxController = new DiagnosticoController();
        List<com.mycompany.proyectoparrinomunoz.Entity.Diagnostico> historial =
                dxController.obtenerDiagnosticosPorPaciente(p.getIdPaciente());

        if (historial.isEmpty()) {
            txtDiagnosticoPrevio.setText("No hay diagnósticos previos registrados para este paciente.");
        } else {
            StringBuilder sb = new StringBuilder("📋 HISTORIAL DE DIAGNÓSTICOS:\n\n");
            for (com.mycompany.proyectoparrinomunoz.Entity.Diagnostico dx : historial) {
                sb.append("📅 Fecha: ").append(dx.getFecha()).append("\n");
                sb.append("🩺 Descripción: ").append(dx.getDescripcion()).append("\n");
                sb.append("💊 Receta: ").append(dx.getReceta()).append("\n");
                sb.append("───────────────────────────────\n");
            }
            txtDiagnosticoPrevio.setText(sb.toString());
        }
    }

    private void atenderTurno(Turno t) {
        String msg = "Atendiendo a: " + t.getPaciente().getNombre() + " " + t.getPaciente().getApellido()
                + " — " + t.getFecha() + " " + t.getHora();
        JOptionPane.showMessageDialog(this, msg);
    }

    private void abrirDiagnostico() {
        Turno t = listaTurnos.getSelectedValue();
        if (t == null) {
            JOptionPane.showMessageDialog(this, "Seleccioná un turno primero.");
            return;
        }
        DiagnosticoController dxController = new DiagnosticoController();
        new DiagnosticoVista(t, dxController).setVisible(true);
    }

    // ---------------- Helpers ----------------

    private JLabel negrita(String s) {
        JLabel l = new JLabel(s);
        l.setFont(l.getFont().deriveFont(Font.BOLD));
        return l;
    }
}
