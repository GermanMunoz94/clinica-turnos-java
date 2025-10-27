package com.mycompany.proyectoparrinomunoz.Controller;

import com.mycompany.proyectoparrinomunoz.Entity.Usuario;
import com.mycompany.proyectoparrinomunoz.vistas.Principal;

import javax.swing.*;

public class HomeController {

    private Usuario usuarioActual;

    public HomeController(Usuario usuario) {
        this.usuarioActual = usuario;
    }

    public void iniciarSesion() {
        if (usuarioActual == null) {
            JOptionPane.showMessageDialog(null,
                    "No hay usuario autenticado. Por favor, inicie sesión.",
                    "Acceso denegado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        SwingUtilities.invokeLater(() -> {
            Principal principal = new Principal(usuarioActual);
            principal.setVisible(true);
        });
    }

    public void cerrarSesion() {
        int confirmar = JOptionPane.showConfirmDialog(null,
                "¿Desea cerrar sesión?", "Confirmación", JOptionPane.YES_NO_OPTION);

        if (confirmar == JOptionPane.YES_OPTION) {
            usuarioActual = null;
            JOptionPane.showMessageDialog(null, "Sesión cerrada correctamente.");
            System.exit(0); // Cierra la aplicacion
        }
    }
}