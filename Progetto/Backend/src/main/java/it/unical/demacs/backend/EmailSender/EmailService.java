package it.unical.demacs.backend.EmailSender;

import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class EmailService {
    private final EmailHandler emailHandler = EmailHandler.getInstance();

    public void sendEmail(String toEmail, String subject, String body) {
        try{
            ExecutorService emailExecutor = Executors.newSingleThreadExecutor();
            emailExecutor.execute(() -> emailHandler.sendEmail(toEmail, subject, body));
            emailExecutor.shutdown();
        }catch (Exception e){
            e.fillInStackTrace();
        }
    }
}
