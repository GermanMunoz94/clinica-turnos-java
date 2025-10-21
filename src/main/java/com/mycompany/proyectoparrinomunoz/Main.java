
package com.mycompany.proyectoparrinomunoz;

import com.mycompany.proyectoparrinomunoz.vistas.LoginVista;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        try {
            // 🎨 Look & Feel moderno
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            // 🟢 Inicia directamente en el LOGIN
            new LoginVista().setVisible(true);
        });
    }
}
