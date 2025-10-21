package com.mycompany.proyectoparrinomunoz.utils;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class EmailService {

    private final String remitente;
    private final String password;

    public EmailService() {
        // Leer credenciales de variables de entorno (más seguro)
        remitente = System.getenv("MAIL_USER");
        password = System.getenv("MAIL_PASS");

        if (remitente == null || password == null) {
            throw new RuntimeException("❌ Configuración de correo no encontrada. Defina MAIL_USER y MAIL_PASS.");
        }
    }

    public boolean enviarCorreo(String destinatario, String asunto, String contenidoHTML) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session sesion = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(remitente, password);
                }
            });

            Message mensaje = new MimeMessage(sesion);
            mensaje.setFrom(new InternetAddress(remitente));
            mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            mensaje.setSubject(asunto);
            mensaje.setContent(contenidoHTML, "text/html; charset=utf-8");

            Transport.send(mensaje);
            System.out.println("✅ Correo enviado a " + destinatario);
            return true;

        } catch (MessagingException e) {
            System.out.println("❌ Error al enviar correo: " + e.getMessage());
            return false;
        }
    }
}

