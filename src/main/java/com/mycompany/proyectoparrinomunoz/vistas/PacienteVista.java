package com.mycompany.proyectoparrinomunoz.vistas;

import com.mycompany.proyectoparrinomunoz.Controller.MedicoController;
import com.mycompany.proyectoparrinomunoz.Controller.PacienteController;
import com.mycompany.proyectoparrinomunoz.Controller.TurnoController;
import com.mycompany.proyectoparrinomunoz.Entity.Medico;
import com.mycompany.proyectoparrinomunoz.Entity.Paciente;
import com.mycompany.proyectoparrinomunoz.Entity.Turno;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.lang.reflect.Constructor;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class PacienteVista extends JFrame {

    private final int idPaciente;
    private final PacienteController pacienteController;
    private final TurnoController turnoController;
    private final MedicoController medicoController;

    private DefaultListModel<Turno> modeloTurnos;
    private JList<Turno> listaTurnos;

    private JComboBox<Medico> comboMedicos;
    private JSpinner spinnerHora;
    private JComponent selectorFecha;

    private JButton btnNuevo;
    private JButton btnCancelar;
    private JButton btnCerrar;

    // ✅ Constructor simplificado (para Login)
    public PacienteVista(int idPaciente) {
        this(idPaciente, new TurnoController(), new MedicoController());
    }

    // ✅ Constructor principal
    public PacienteVista(int idPaciente, TurnoController turnoController, MedicoController medicoController) {
        this.idPaciente = idPaciente;
        this.pacienteController = new PacienteController();
        this.turnoController = turnoController;
        this.medicoController = medicoController;

        setTitle("Panel del Paciente");
        setSize(750, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initUI();
        cargarTurnos();
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
        Paciente p = pacienteController.buscarPacientePorId(idPaciente);
        JLabel lbl = new JLabel("🧍 Turnos de " + p.getNombre() + " " + p.getApellido());
        lbl.setFont(lbl.getFont().deriveFont(Font.BOLD, 18f));
        return lbl;
    }

    private JComponent crearCentro() {
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        split.setResizeWeight(0.5);

        // Panel izquierdo: lista de turnos
        modeloTurnos = new DefaultListModel<>();
        listaTurnos = new JList<>(modeloTurnos);
        listaTurnos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaTurnos.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {
            JLabel lab = new JLabel();
            if (value != null && value.getMedico() != null) {
                lab.setText(String.format("%s %s — Dr. %s %s (%s)",
                        value.getFecha(), value.getHora(),
                        value.getMedico().getNombre(), value.getMedico().getApellido(),
                        value.getMedico().getEspecialidad()));
            } else {
                lab.setText(value != null ? value.getFecha() + " " + value.getHora() : "");
            }
            lab.setOpaque(true);
            lab.setBackground(isSelected ? list.getSelectionBackground() : Color.WHITE);
            lab.setForeground(isSelected ? list.getSelectionForeground() : Color.DARK_GRAY);
            lab.setBorder(new EmptyBorder(6, 8, 6, 8));
            return lab;
        });

        JPanel panelLista = new JPanel(new BorderLayout());
        panelLista.setBorder(BorderFactory.createTitledBorder("Turnos reservados"));
        panelLista.add(new JScrollPane(listaTurnos), BorderLayout.CENTER);

        // Panel derecho: nuevo turno
        JPanel panelNuevo = new JPanel(new GridLayout(0, 1, 6, 6));
        panelNuevo.setBorder(BorderFactory.createTitledBorder("Reservar nuevo turno"));

        // Combo médicos
        comboMedicos = new JComboBox<>();
        List<Medico> medicos = medicoController.listarMedicos();
        medicos.forEach(comboMedicos::addItem);
        panelNuevo.add(new JLabel("👨‍⚕️ Médico:"));
        panelNuevo.add(comboMedicos);

        // Fecha
        selectorFecha = crearSelectorFecha();
        panelNuevo.add(new JLabel("📅 Fecha:"));
        panelNuevo.add(selectorFecha);

        // Hora
        spinnerHora = new JSpinner(new SpinnerListModel(new String[]{
                "08:00", "09:00", "10:00", "11:00", "12:00",
                "15:00", "16:00", "17:00", "18:00"
        }));
        panelNuevo.add(new JLabel("⏰ Hora:"));
        panelNuevo.add(spinnerHora);

        // Botón reservar
        JButton btnReservar = new JButton("Reservar turno");
        btnReservar.addActionListener(e -> reservarTurnoVisual());
        panelNuevo.add(btnReservar);

        split.setLeftComponent(panelLista);
        split.setRightComponent(panelNuevo);
        return split;
    }

    private JComponent crearFooter() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        btnCancelar = new JButton("Cancelar turno seleccionado");
        btnCerrar = new JButton("Cerrar sesión");

        btnCancelar.addActionListener(e -> cancelarTurno());
        btnCerrar.addActionListener(e -> dispose());

        panel.add(btnCancelar);
        panel.add(btnCerrar);
        return panel;
    }

    // ---------------- LÓGICA ----------------
    private void cargarTurnos() {
        modeloTurnos.clear();
        List<Turno> turnos = turnoController.listarTurnosPorPaciente(idPaciente);
        if (turnos.isEmpty()) {
            modeloTurnos.addElement(new Turno(0, null, null, "—", "No hay turnos reservados."));
        } else {
            turnos.forEach(modeloTurnos::addElement);
        }
    }

    private void reservarTurnoVisual() {
        Medico seleccionado = (Medico) comboMedicos.getSelectedItem();
        if (seleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un médico.");
            return;
        }

        LocalDate fecha = getFechaSeleccionada();
        String hora = (String) spinnerHora.getValue();

        Paciente p = pacienteController.buscarPacientePorId(idPaciente);
        Turno nuevo = new Turno(0, p, seleccionado, fecha.toString(), hora);

        if (!turnoController.crearTurno(nuevo)) {
            JOptionPane.showMessageDialog(this, "❌ No se pudo reservar el turno.");
        } else {
            JOptionPane.showMessageDialog(this, "✅ Turno reservado con éxito.");
            cargarTurnos();
        }
    }

    private void cancelarTurno() {
        Turno seleccionado = listaTurnos.getSelectedValue();
        if (seleccionado == null || seleccionado.getIdTurno() == 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un turno válido para cancelar.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Seguro que desea cancelar este turno?",
                "Confirmar cancelación",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (turnoController.eliminarTurno(seleccionado.getIdTurno())) {
                JOptionPane.showMessageDialog(this, "Turno cancelado correctamente.");
                cargarTurnos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al cancelar el turno.");
            }
        }
    }

    // ---------------- Helpers ----------------
    private JComponent crearSelectorFecha() {
        try {
            Class<?> calClass = Class.forName("com.toedter.calendar.JCalendar");
            Constructor<?> c = calClass.getConstructor();
            JComponent cal = (JComponent) c.newInstance();
            calClass.getMethod("setDate", Date.class).invoke(cal, new Date());
            return cal;
        } catch (Exception ignore) {
            JSpinner sp = new JSpinner(new SpinnerDateModel(new Date(), null, null, java.util.Calendar.DAY_OF_MONTH));
            JSpinner.DateEditor editor = new JSpinner.DateEditor(sp, "yyyy-MM-dd");
            sp.setEditor(editor);
            return sp;
        }
    }

    private LocalDate getFechaSeleccionada() {
        try {
            if (selectorFecha.getClass().getName().equals("com.toedter.calendar.JCalendar")) {
                Date d = (Date) selectorFecha.getClass().getMethod("getDate").invoke(selectorFecha);
                return d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            } else if (selectorFecha instanceof JSpinner sp) {
                Date d = (Date) sp.getValue();
                return d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            }
        } catch (Exception ignored) {}
        return LocalDate.now();
    }
}
