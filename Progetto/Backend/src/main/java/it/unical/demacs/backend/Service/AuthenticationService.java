package it.unical.demacs.backend.Service;

import it.unical.demacs.backend.Persistenza.DatabaseHandler;
import it.unical.demacs.backend.Persistenza.Model.Role;
import it.unical.demacs.backend.Persistenza.Model.User;
import it.unical.demacs.backend.Service.Request.AuthenticationRequest;
import it.unical.demacs.backend.Service.Request.JwtAuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {

    private final JwtService jwtService;
    @Autowired
    public AuthenticationService(JwtService jwtService) {
        this.jwtService = jwtService;
    }


    public ResponseEntity<?> authenticate(AuthenticationRequest request) {
        if (DatabaseHandler.getInstance().getUtenteDao().findByUsername(request.getUsername()).join().getUsername() == null) {
          return ResponseEntity.badRequest().body("Username not found");
        }else {
            User user = DatabaseHandler.getInstance().getUtenteDao().findByUsername(request.getUsername()).join();
            if (BCrypt.checkpw(request.getPassword(), user.getPassword())) {
                Role role = DatabaseHandler.getInstance().getUtenteDao().findByUsername(request.getUsername()).join().getRole();
                Map<String, Object> claims = new HashMap<>();
                claims.put("Role", role.toString());
                return ResponseEntity.ok(new JwtAuthResponse(
                        jwtService.generateToken(claims,user)));
            } else {
                return ResponseEntity.badRequest().body("Wrong password");
            }
        }
    }

    public ResponseEntity<?> registerUser(User user) {
        if (DatabaseHandler.getInstance().getUtenteDao().findByUsername(user.getUsername()).join().getUsername() != null) {
            return ResponseEntity.badRequest().body("Username already exists");
        }
        if (DatabaseHandler.getInstance().getUtenteDao().findByEmail(user.getEmail()).join().getEmail() != null) {
            return ResponseEntity.badRequest().body("Email already exists");
        }
        DatabaseHandler.getInstance().getUtenteDao().insert(user);
        return ResponseEntity.ok("User registered successfully");
    }
}