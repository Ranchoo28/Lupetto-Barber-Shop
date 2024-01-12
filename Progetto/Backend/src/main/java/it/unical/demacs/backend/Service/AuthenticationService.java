package it.unical.demacs.backend.Service;

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
    @Autowired
    public AuthenticationService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public ResponseEntity<?> authenticate(AuthenticationRequest request) {
        try {
            DatabaseHandler.getInstance().openConnection();
            UserDao userDao = DatabaseHandler.getInstance().getUtenteDao();
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
            UserDao utenteDao = DatabaseHandler.getInstance().getUtenteDao();
            HairdresserDao hairdresserDao = DatabaseHandler.getInstance().getHairdresserDao();

            if (utenteDao.findByEmail(user.getEmail()).join().getEmail() != null ||
                    hairdresserDao.findByEmail(user.getEmail()).join().getEmail() != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("{\"message\": \"A person with this email already exists\", \"errorCode\": \"EMAIL_CONFLICT\"}");
            }
            utenteDao.insert(user);
            return ResponseEntity.ok().body("{\"message\": \"User registered successfully\"}");
        }
        finally {
            DatabaseHandler.getInstance().closeConnection();
        }
    }

}