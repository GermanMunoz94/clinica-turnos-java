package com.mycompany.proyectoparrinomunoz.vistas;

import com.mycompany.proyectoparrinomunoz.Controller.UsuarioController;

import javax.swing.*;
import java.awt.*;

public class RegistroUsuarioVista extends JFrame {
    private JTextField txtEmail;
    private final JTextField txtIdRelacionado;
    private JPasswordField txtPassword;
    private JComboBox<String> comboRol;
    private JButton btnRegistrar, btnCancelar;

    private final UsuarioController usuarioController;

    public RegistroUsuarioVista() {
        usuarioController = new UsuarioController();

        setTitle("Registro de Usuario");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        panel.add(txtEmail);

        panel.add(new JLabel("Contraseña:"));
        txtPassword = new JPasswordField();
        panel.add(txtPassword);

        panel.add(new JLabel("Rol:"));
        comboRol = new JComboBox<>(new String[]{"PACIENTE", "MEDICO", "ADMIN"});
        panel.add(comboRol);

        panel.add(new JLabel("ID Relacionado:"));
        txtIdRelacionado = new JTextField();
        panel.add(txtIdRelacionado);

        btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener(e -> registrarUsuario());
        panel.add(btnRegistrar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        panel.add(btnCancelar);

        add(panel);
    }

    private void registrarUsuario() {
        String email = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();
        String rol = comboRol.getSelectedItem().toString();
        String idTxt = txtIdRelacionado.getText().trim();

        if (email.isEmpty() || password.isEmpty() || idTxt.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos.");
            return;
        }

        try {
            int idRelacionado = Integer.parseInt(idTxt);
            boolean creado = usuarioController.crearUsuario(email, password, rol, idRelacionado);

            if (creado) {
                JOptionPane.showMessageDialog(this, "✅ Usuario registrado con éxito.");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Error al registrar usuario.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El ID relacionado debe ser numérico.");
        }
    }
}

