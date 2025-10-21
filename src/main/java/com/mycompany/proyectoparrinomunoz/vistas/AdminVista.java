package com.mycompany.proyectoparrinomunoz.vistas;

import javax.swing.*;
import java.awt.*;

public class AdminVista extends JFrame {

    public AdminVista() {
        setTitle("Panel de Administración");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel lbl = new JLabel("Bienvenido Administrador", SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 16));
        add(lbl, BorderLayout.CENTER);
    }
}

