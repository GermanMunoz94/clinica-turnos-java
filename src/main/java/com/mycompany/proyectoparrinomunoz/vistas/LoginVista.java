package com.mycompany.proyectoparrinomunoz.vistas;

import com.mycompany.proyectoparrinomunoz.Controller.*;
import com.mycompany.proyectoparrinomunoz.Entity.Usuario;

import javax.swing.*;
import java.awt.*;

public class LoginVista extends JFrame {

    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnCerrar; // ✅ Agregado correctamente

    private final UsuarioController usuarioController;

    public LoginVista() {
        usuarioController = new UsuarioController();

        setTitle("Iniciar sesión");
        setSize(360, 240);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        panel.add(txtEmail);

        panel.add(new JLabel("Contraseña:"));
        txtPassword = new JPasswordField();
        panel.add(txtPassword);

        btnLogin = new JButton("Ingresar");
        btnCerrar = new JButton("Salir");

        btnLogin.addActionListener(e -> autenticar());
        btnCerrar.addActionListener(e -> System.exit(0)); // ✅ Sale del sistema

        JButton btnRegistro = new JButton("Registrarse");
        btnRegistro.addActionListener(e -> new RegistroUsuarioVista().setVisible(true));

        panel.add(btnRegistro);


        panel.add(btnLogin);
        panel.add(btnCerrar);

        add(panel);
    }

    private void autenticar() {
        String email = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor complete todos los campos.");
            return;
        }

        Usuario usuario = usuarioController.autenticar(email, password);
        if (usuario == null) {
            JOptionPane.showMessageDialog(this, "❌ Credenciales incorrectas.");
            return;
        }

        JOptionPane.showMessageDialog(this, "✅ Bienvenido " + usuario.getRol() + "!");

        switch (usuario.getRol().toUpperCase()) {
            case "ADMIN" -> new Principal().setVisible(true);
            case "MEDICO" -> new MedicoTurnoVista(
                    usuario.getIdRelacionado(),
                    new TurnoController(),
                    new PacienteController(),
                    new MedicoController()
            ).setVisible(true);
            case "PACIENTE" -> new PacienteVista(usuario.getIdRelacionado()).setVisible(true);
            default -> JOptionPane.showMessageDialog(this, "Rol desconocido: " + usuario.getRol());
        }

        dispose(); // Cierra el login luego de entrar
    }
}
