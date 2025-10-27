package com.mycompany.proyectoparrinomunoz.utils;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class EmailService {

    // ✅ Configurable por variables de entorno (seguro)
    private static final String CORREO_REMITENTE = System.getenv().getOrDefault("MAIL_USER", "tu_correo@gmail.com");
    private static final String CONTRASENIA = System.getenv().getOrDefault("MAIL_PASS", "clave_de_aplicacion");

    public void enviarCorreo(String destinatario, String asunto, String mensaje) {
        if (destinatario == null || destinatario.isBlank()) {
            System.err.println("❌ No se puede enviar correo: destinatario vacío.");
            return;
        }

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", System.getenv().getOrDefault("SMTP_HOST", "smtp.gmail.com"));
        props.put("mail.smtp.port", System.getenv().getOrDefault("SMTP_PORT", "587"));
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(CORREO_REMITENTE, CONTRASENIA);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(CORREO_REMITENTE));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject(asunto);
            message.setText(mensaje);

            Transport.send(message);
            System.out.println("📨 Correo enviado a: " + destinatario);

        } catch (MessagingException e) {
            System.err.println("❌ Error al enviar correo: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("⚠️ Error inesperado al enviar correo: " + e.getMessage());
        }
    }
}
