package com.triana.salesianos.dam.lagrada.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.triana.salesianos.dam.lagrada.model.Evento;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import org.springframework.mail.javamail.MimeMessageHelper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import com.triana.salesianos.dam.lagrada.model.User;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendVerificationEmail(String toEmail, String verificationCode) {
        // Implementación del envío de email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Código de verificación");
        message.setText("Este es el código de verificación: " + verificationCode);

        mailSender.send(message);
    }

    public void sendTicketPurchaseEmail(String toEmail, Evento evento) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Confirmación de compra de entrada");
        message.setText("Has comprado una entrada para el evento: " + evento.getNombre() +
                "\nDescripción: " + evento.getDescripcion() +
                "\nFecha y hora: " + evento.getFechaYHora() +
                "\nEquipos: " + (evento.getEquipo1() != null ? evento.getEquipo1().getNombre() : "") + " vs " + (evento.getEquipo2() != null ? evento.getEquipo2().getNombre() : "") +
                "\nPrecio: " + evento.getPrecio() + "€\n\n¡Gracias por tu compra!");
        mailSender.send(message);
    }

    public String generateQRCodeBase64(String text, int width, int height) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
            byte[] pngData = pngOutputStream.toByteArray();
            return Base64.getEncoder().encodeToString(pngData);
        } catch (WriterException | java.io.IOException e) {
            throw new RuntimeException("Error generando el código QR", e);
        }
    }

    public void sendTicketPurchaseEmailWithQR(User user, Evento evento) {
        String qrContent = evento.getId() + ":" + user.getId();
        String qrBase64 = generateQRCodeBase64(qrContent, 250, 250);
        String htmlMsg = "<h2>Has comprado una entrada para el evento: " + evento.getNombre() + "</h2>" +
                "<p>Descripción: " + evento.getDescripcion() + "<br>" +
                "Fecha y hora: " + evento.getFechaYHora() + "<br>" +
                "Equipos: " + (evento.getEquipo1() != null ? evento.getEquipo1().getNombre() : "") + " vs " + (evento.getEquipo2() != null ? evento.getEquipo2().getNombre() : "") + "<br>" +
                "Precio: " + evento.getPrecio() + "€<br><br>" +
                "<b>Tu código QR de acceso está adjunto a este correo.</b><br><br>" +
                "¡Gracias por tu compra!</p>";
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(user.getCorreo());
            helper.setSubject("Confirmación de compra de entrada con QR");
            helper.setText(htmlMsg, true);
            // Adjuntar el QR como archivo PNG
            byte[] qrBytes = Base64.getDecoder().decode(qrBase64);
            helper.addAttachment("qr-entrada.png", new org.springframework.core.io.ByteArrayResource(qrBytes));
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Error enviando el email con QR", e);
        }
    }

    public void sendLoginNotificationEmail(String toEmail, String ipAddress) {
        String htmlMsg = "<h2>Nuevo inicio de sesión detectado</h2>" +
                "<p>Se ha detectado un nuevo inicio de sesión en tu cuenta desde la IP: " + ipAddress + "</p>" +
                "<p>Si no has sido tú, te recomendamos cambiar tu contraseña inmediatamente.</p>" +
                "<p>Puedes cambiar tu contraseña haciendo clic en el siguiente enlace:</p>" +
                "<p><a href='http://localhost:4200/perfil'>Cambiar contraseña</a></p>";

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(toEmail);
            helper.setSubject("Notificación de inicio de sesión");
            helper.setText(htmlMsg, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Error enviando el email de notificación de inicio de sesión", e);
        }
    }

}

