package com.mycompany.proyectoparrinomunoz.vistas;

import com.formdev.flatlaf.FlatLightLaf;
import com.mycompany.proyectoparrinomunoz.Controller.UsuarioController;
import com.mycompany.proyectoparrinomunoz.Entity.Usuario;
import com.mycompany.proyectoparrinomunoz.Conexion;

import javax.swing.*;
import java.awt.*;

public class LoginVista extends JFrame {

    private final JTextField txtUsuario;
    private final JPasswordField txtPassword;
    private final JButton btnLogin;
    private final UsuarioController usuarioController;
    private final Conexion conexion;

    public LoginVista() {
        conexion = new Conexion();
        usuarioController = new UsuarioController();

        // Configurar apariencia moderna
        FlatLightLaf.setup();
        setTitle("Inicio de Sesión - Gestor de Turnos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel principal
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JLabel lblUsuario = new JLabel("Usuario:");
        txtUsuario = new JTextField();

        JLabel lblPassword = new JLabel("Contraseña:");
        txtPassword = new JPasswordField();

        btnLogin = new JButton("Ingresar");
        btnLogin.addActionListener(e -> autenticar());

        panel.add(lblUsuario);
        panel.add(txtUsuario);
        panel.add(lblPassword);
        panel.add(txtPassword);
        panel.add(new JLabel());
        panel.add(btnLogin);

        add(panel, BorderLayout.CENTER);
    }

    private void autenticar() {
        String usuario = txtUsuario.getText().trim();
        String contrasenia = new String(txtPassword.getPassword()).trim();

        // Validaciones básicas
        if (usuario.isEmpty() || contrasenia.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe completar usuario y contraseña.");
            return;
        }

        // Probar conexión
        if (Conexion.getConexion() == null) {
            JOptionPane.showMessageDialog(this, "No hay conexión con la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Usuario u = usuarioController.autenticar(usuario, contrasenia);

            if (u != null) {
                JOptionPane.showMessageDialog(this, "Bienvenido " + u.getUsuario());

                // Abrir vista según rol
                switch (u.getRol().toLowerCase()) {
                    case "administrador" -> new AdminVista().setVisible(true);
                    case "medico" -> new MedicoVista().setVisible(true);
                    case "paciente" -> new PacienteVista().setVisible(true);
                    default -> JOptionPane.showMessageDialog(this, "Rol no reconocido: " + u.getRol());
                }
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Credenciales incorrectas.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al autenticar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginVista().setVisible(true));
    }
}
