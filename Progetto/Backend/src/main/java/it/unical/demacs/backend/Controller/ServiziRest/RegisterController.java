package it.unical.demacs.backend.Controller.ServiziRest;

import it.unical.demacs.backend.Persistenza.DatabaseHandler;
import it.unical.demacs.backend.Persistenza.Model.User;
import it.unical.demacs.backend.Service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.ref.PhantomReference;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
public class RegisterController {
    private final AuthenticationService authenticationService;

    @PostMapping("/api/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        return authenticationService.registerUser(user);
    }
}
