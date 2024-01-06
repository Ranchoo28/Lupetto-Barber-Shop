package it.unical.demacs.backend.Controller.ServiziRest;

import it.unical.demacs.backend.Persistenza.DatabaseHandler;
import it.unical.demacs.backend.Persistenza.Model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class RegisterController {

    @PostMapping("/api/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
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
