package it.unical.demacs.backend.Controller.Servlet;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import it.unical.demacs.backend.Persistenza.DatabaseHandler;
import it.unical.demacs.backend.Persistenza.Model.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.CrossOrigin;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;


@CrossOrigin(origins = "http://localhost:4200")
@WebServlet("/api/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if(DatabaseHandler.getInstance().getUtenteDao().findByUsername(username).join().getUsername() == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Username not found");
        } else {
            if(BCrypt.checkpw(password, DatabaseHandler.getInstance().getUtenteDao().findByUsername(username).join().getPassword())) {
                SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("Login successful");
                response.addHeader("Authorization", "Bearer " +
                        Jwts.builder()
                                .setSubject(username)
                                .signWith(secretKey)
                                .compact()
                );
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Wrong password");
            }
        }
    }
}
