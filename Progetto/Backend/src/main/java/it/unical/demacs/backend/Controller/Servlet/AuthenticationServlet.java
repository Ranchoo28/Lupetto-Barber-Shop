package it.unical.demacs.backend.Controller.Servlet;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import it.unical.demacs.backend.Persistenza.DatabaseHandler;
import it.unical.demacs.backend.Service.AuthenticationService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.CrossOrigin;
import javax.crypto.SecretKey;
import java.io.IOException;


@CrossOrigin(origins = "http://localhost:4200")
@WebServlet("/api/login")
public class AuthenticationServlet extends HttpServlet {

    public final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationServlet(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        authenticationService.authenticate(request, response);
    }
}
