package it.unical.demacs.backend.EmailSender;

import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.util.Properties;

public class EmailHandler {
    private EmailHandler(){}
    private static EmailHandler instance;
    public static EmailHandler getInstance(){
        if (instance == null) return new EmailHandler();
        else return instance;
    }

    public void sendEmail(String toEmail, String subject, String body) {
        try {
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            Properties properties = new Properties();

            // Configurazione del server SMTP
            mailSender.setHost("smtp.gmail.com");
            mailSender.setUsername("lupettobarbershop@gmail.com");
            mailSender.setPassword("jvkzsjaprbpdbpzh");
            mailSender.setPort(587);
            properties.setProperty("mail.smtp.auth", "true");
            properties.setProperty("mail.smtp.starttls.enable", "true");
            mailSender.setJavaMailProperties(properties);

            // Creazione e invio del messaggio MIME (per HTML)
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("lupettobarbershop@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body, true); // true per indicare che il corpo è HTML

            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}