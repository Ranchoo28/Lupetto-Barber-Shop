package it.unical.demacs.backend.Controller.ServiziRest;

import it.unical.demacs.backend.Service.AuthenticationService;
import it.unical.demacs.backend.Service.Request.AuthenticationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class AuthenticationController {

    public final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

   @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request){
        return authenticationService.authenticate(request);
    }
}
