package com.mycompany.proyectoparrinomunoz.vistas;

import com.mycompany.proyectoparrinomunoz.Controller.DiagnosticoController;
import com.mycompany.proyectoparrinomunoz.Controller.MedicoController;
import com.mycompany.proyectoparrinomunoz.Controller.PacienteController;
import com.mycompany.proyectoparrinomunoz.Controller.TurnoController;
import com.mycompany.proyectoparrinomunoz.Entity.Medico;
import com.mycompany.proyectoparrinomunoz.Entity.Paciente;
import com.mycompany.proyectoparrinomunoz.Entity.Turno;
import com.mycompany.proyectoparrinomunoz.Entity.Diagnostico;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Constructor;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Vista para que el MÉDICO vea sus turnos del día/fecha seleccionada,
 * consulte datos del paciente y (en el siguiente paso) cargue diagnóstico.
 *
 * Si tienes JCalendar (com.toedter.calendar.JCalendar) en el classpath, se muestra un calendario real.
 * Si no, se usa un selector de fecha (JSpinner) como fallback.
 */
public class MedicoTurnoVista extends JFrame {

    private final int idMedico;
    private final TurnoController turnoController;
    private final PacienteController pacienteController;
    private final MedicoController medicoController;

    // UI
    private DefaultListModel<Turno> modeloTurnos;
    private JList<Turno> listaTurnos;
    private JPanel panelDetalle;
    private JLabel lblPaciente;
    private JLabel lblDni;
    private JLabel lblMail;
    private JLabel lblTel;
    private JLabel lblFecha;
    private JLabel lblHora;
    private JTextArea txtDiagnosticoPrevio;
    private JButton btnAtender;
    private JButton btnAgregarDx;
    private JButton btnCancelar;

    // “Calendario” (dinámico: JCalendar si existe, sino JSpinner)
    private JComponent selectorFecha;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public MedicoTurnoVista(int idMedico,
                            TurnoController turnoController,
                            PacienteController pacienteController,
                            MedicoController medicoController) {
        this.idMedico = idMedico;
        this.turnoController = turnoController;
        this.pacienteController = pacienteController;
        this.medicoController = medicoController;

        setTitle("Agenda del Médico");
        setSize(900, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initUI();
        cargarTurnosParaFecha(getFechaSeleccionada());
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

        // Info del médico
        Medico med = medicoController.buscarMedicoPorId(idMedico);
        String titulo = (med != null)
                ? "Agenda de " + med.getNombre() + " " + med.getApellido() + " — " + med.getEspecialidad()
                : "Agenda del Médico (ID " + idMedico + ")";
        JLabel lbl = new JLabel(titulo);
        lbl.setFont(lbl.getFont().deriveFont(Font.BOLD, 16f));
        header.add(lbl, BorderLayout.WEST);

        // Leyenda
        JLabel leyenda = new JLabel("Seleccione una fecha y un turno para ver detalles.");
        leyenda.setForeground(Color.DARK_GRAY);
        header.add(leyenda, BorderLayout.EAST);

        return header;
    }

    private JComponent crearCentro() {
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        split.setResizeWeight(0.45);

        // Izquierda: Calendario + Lista
        JPanel izquierda = new JPanel(new BorderLayout(10, 10));
        izquierda.add(crearPanelFecha(), BorderLayout.NORTH);
        izquierda.add(crearPanelLista(), BorderLayout.CENTER);

        // Derecha: Detalle
        panelDetalle = crearPanelDetalle();

        split.setLeftComponent(izquierda);
        split.setRightComponent(panelDetalle);
        return split;
    }

    private JComponent crearPanelFecha() {
        JPanel p = new JPanel(new BorderLayout(6, 6));
        p.setBorder(BorderFactory.createTitledBorder("Fecha"));

        selectorFecha = crearSelectorFecha(); // JCalendar si existe, sino JSpinner

        // Listener de cambios de fecha
        if (selectorFecha.getClass().getName().equals("com.toedter.calendar.JCalendar")) {
            // JCalendar -> addPropertyChangeListener("calendar")
            selectorFecha.addPropertyChangeListener(evt -> {
                if ("calendar".equals(evt.getPropertyName())) {
                    cargarTurnosParaFecha(getFechaSeleccionada());
                }
            });
        } else {
            // JSpinner fallback
            if (selectorFecha instanceof JSpinner sp) {
                sp.addChangeListener(e -> cargarTurnosParaFecha(getFechaSeleccionada()));
            }
        }

        p.add(selectorFecha, BorderLayout.CENTER);
        return p;
    }

    private JComponent crearPanelLista() {
        JPanel p = new JPanel(new BorderLayout(6, 6));
        p.setBorder(BorderFactory.createTitledBorder("Turnos del día"));

        modeloTurnos = new DefaultListModel<>();
        listaTurnos = new JList<>(modeloTurnos);
        listaTurnos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaTurnos.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {
            JLabel lab = new JLabel();
            String linea = (value != null)
                    ? String.format("%s %s — %s %s",
                    value.getFecha(), value.getHora(),
                    value.getPaciente().getNombre(), value.getPaciente().getApellido())
                    : "";
            lab.setText(linea);
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

        // Doble click = “Atender”
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
        dxPanel.setBorder(BorderFactory.createTitledBorder("Diagnóstico previo"));
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

    private void cargarTurnosParaFecha(LocalDate fecha) {
        modeloTurnos.clear();
        List<Turno> todos = turnoController.listarTurnosPorMedico(idMedico);
        List<Turno> delDia = todos.stream()
                .filter(t -> Objects.equals(t.getFecha(), fecha.toString()))
                .collect(Collectors.toList());
        delDia.forEach(modeloTurnos::addElement);

        // reset detalle
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

        // 🔍 Mostrar historial de diagnósticos del paciente
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

    private LocalDate getFechaSeleccionada() {
        if (selectorFecha.getClass().getName().equals("com.toedter.calendar.JCalendar")) {
            try {
                Date d = (Date) selectorFecha.getClass().getMethod("getDate").invoke(selectorFecha);
                return d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            } catch (Exception ignored) { }
        }
        // Fallback: JSpinner
        if (selectorFecha instanceof JSpinner sp) {
            Date d = (Date) sp.getValue();
            return d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        return LocalDate.now();
    }

    private JComponent crearSelectorFecha() {
        // Intentar JCalendar (reflexión para no romper si no está)
        try {
            Class<?> calClass = Class.forName("com.toedter.calendar.JCalendar");
            Constructor<?> c = calClass.getConstructor();
            JComponent cal = (JComponent) c.newInstance();
            // setear fecha hoy
            calClass.getMethod("setDate", Date.class).invoke(cal, new Date());
            return cal;
        } catch (Exception ignore) {
            // Fallback: JSpinner con fecha
            JSpinner sp = new JSpinner(new SpinnerDateModel(new Date(), null, null, java.util.Calendar.DAY_OF_MONTH));
            JSpinner.DateEditor editor = new JSpinner.DateEditor(sp, "yyyy-MM-dd");
            sp.setEditor(editor);
            return sp;
        }
    }
}

