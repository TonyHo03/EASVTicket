package dk.easv.easvticket.BLL.util;

import dk.easv.easvticket.BE.Ticket;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

import java.util.Properties;

public class EmailSender {

    private static final String FROM = "noreply.ticketsystemtest@gmail.com"; // Lavede en gmail bare som test
    private static final String PASSWORD = "sull kdyd sjag nrgl"; // App password til gmail

    public static void sendTicket(Ticket ticket) throws Exception {

        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465"); // Port til at sende mails med. åbentbart standarden.
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.starttls.enable", "false");
        properties.put("mail.debug", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM, PASSWORD);
            }
        });

        Multipart multipart = new MimeMultipart();

        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setText("""
                
                Hi, %s!
                
                Thank you for your purchase! Your ticket ID is: %s
                
                Please find your ticket attached.
                
                This is an automated email, please do not reply.
                
                SEA Ticket System
                """.formatted(ticket.getCustomerName(), ticket.getTicketId()));

        MimeBodyPart attachment = new MimeBodyPart();
        attachment.attachFile(TicketPDFWriter.generatePDF(ticket));

        multipart.addBodyPart(textPart);
        multipart.addBodyPart(attachment);

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(FROM));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(ticket.getEmail()));
        message.setSubject("Ticket Test");

        message.setContent(multipart);

        Transport.send(message);

    }

}
