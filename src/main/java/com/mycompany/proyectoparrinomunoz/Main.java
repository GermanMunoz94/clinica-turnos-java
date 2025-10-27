
package com.mycompany.proyectoparrinomunoz;

import com.formdev.flatlaf.FlatLightLaf;
import com.mycompany.proyectoparrinomunoz.vistas.LoginVista;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        // Configurar apariencia moderna
        FlatLightLaf.setup();

        // Probar conexión antes de iniciar interfaz
        Conexion conexion = new Conexion();
        if (Conexion.getConexion() == null) {
            JOptionPane.showMessageDialog(null,
                    "No se pudo conectar a la base de datos.\nVerifique credenciales y puerto (3306).",
                    "Error de conexión", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Iniciar interfaz en el hilo gráfico seguro
        SwingUtilities.invokeLater(() -> {
            try {
                new LoginVista().setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "Error al iniciar la aplicación: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
