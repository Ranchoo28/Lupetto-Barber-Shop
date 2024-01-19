package it.unical.demacs.backend.Service;

import it.unical.demacs.backend.EmailSender.EmailService;
import it.unical.demacs.backend.Persistenza.DAO.HairdresserDao;
import it.unical.demacs.backend.Persistenza.DAO.UserDao;
import it.unical.demacs.backend.Persistenza.DatabaseHandler;
import it.unical.demacs.backend.Persistenza.Model.Hairdresser;
import it.unical.demacs.backend.Persistenza.Model.User;
import it.unical.demacs.backend.Service.Request.AuthenticationRequest;
import it.unical.demacs.backend.Service.Request.JwtAuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service

public class AuthenticationService {

    private final JwtService jwtService;
    private final EmailService emailService;

    @Autowired
    public AuthenticationService(JwtService jwtService, EmailService emailService) {
        this.jwtService = jwtService;
        this.emailService = emailService;
    }


    public ResponseEntity<?> authenticate(AuthenticationRequest request) {
        try {
            DatabaseHandler.getInstance().openConnection();
            UserDao userDao = DatabaseHandler.getInstance().getUserDao();
            HairdresserDao hairdresserDao = DatabaseHandler.getInstance().getHairdresserDao();

            User user = userDao.findByEmail(request.getEmail()).join();
            Hairdresser hairdresser = hairdresserDao.findByEmail(request.getEmail()).join();

            if (user.getEmail() == null && hairdresser.getEmail() == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"Wrong password\", \"errorCode\": \"INVALID_CREDENTIALS\"}");
            }

            boolean isHairdresser = (user.getEmail() == null);

            String passwordToCheck = isHairdresser ? hairdresser.getPassword() : user.getPassword();

            if (BCrypt.checkpw(request.getPassword(), passwordToCheck)) {
                UserDetails entity = isHairdresser ? hairdresser : user;
                return ResponseEntity.ok(new JwtAuthResponse(jwtService.generateToken(entity)));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"Wrong password\", \"errorCode\": \"INVALID_CREDENTIALS\"}");
            }
        }
        finally {
            DatabaseHandler.getInstance().closeConnection();
        }
    }

    public ResponseEntity<?> registerUser(User user) {
        try {
            DatabaseHandler.getInstance().openConnection();
            UserDao utenteDao = DatabaseHandler.getInstance().getUserDao();
            HairdresserDao hairdresserDao = DatabaseHandler.getInstance().getHairdresserDao();

            if (utenteDao.findByEmail(user.getEmail()).join().getEmail() != null ||
                    hairdresserDao.findByEmail(user.getEmail()).join().getEmail() != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("{\"message\": \"A person with this email already exists\", \"errorCode\": \"EMAIL_CONFLICT\"}");
            }
            String htmlBody = "<html><head>"
                    + "<style>"
                    + ".bodyStyle {font-family: Arial, sans-serif; background-color: #f4f4f4; color: #333333; margin: 0; padding: 0;}"
                    + ".container {width: 100%; max-width: 600px; margin: 20px auto; background-color: #ffffff; padding: 20px; box-shadow: 0 4px 8px rgba(0,0,0,0.1);}"
                    + ".header {background-color: #007bff; color: #ffffff; padding: 20px; text-align: center; border-radius: 5px 5px 0 0;}"
                    + ".textCenter {padding: 15px; text-align: center; font-size: 16px;}"
                    + ".footer {text-align: center; padding: 20px; background-color: #f0f0f0; color: #333; font-size: 14px; border-radius: 0 0 5px 5px;}"
                    + "</style>"
                    + "</head><body class=\"bodyStyle\">"
                    + "<div class=\"container\">"
                    + "<div class=\"header\">"
                    + "<h1 style=\"margin: 0; font-size: 24px;\">Lupetto Barber Shop <br> Ciao "+user.getName()+", finalmente fai parte del nostro branco!</h1>"
                    + "</div>"
                    + "<div class=\"textCenter\">Adesso puoi iniziare a usufruire di tutti i vantaggi da noi offerti!</div>"
                    + "<div class=\"textCenter\">Puoi prenotare un appuntamento, acquistare i tuoi prodotti preferiti e tanto altro ancora!</div>"
                    + "<div class=\"textCenter\">Ti aspettiamo in negozio (e online)!!!</div>"
                    + "<div class=\"footer\">©2024 Lupetto-Barber-Shop. Designed by team Lupetto</div>"
                    + "</div></body></html>";

            utenteDao.insert(user);
            emailService.sendEmail(
                    user.getEmail(),
                    "Benvenuto in Lupetto Barber Shop" ,
                    htmlBody
            );
            return ResponseEntity.ok().body("{\"message\": \"User registered successfully\"}");
        }
        finally {
            DatabaseHandler.getInstance().closeConnection();
        }
    }

}