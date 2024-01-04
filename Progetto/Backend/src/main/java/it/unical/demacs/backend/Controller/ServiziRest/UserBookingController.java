package it.unical.demacs.backend.Controller.ServiziRest;

import it.unical.demacs.backend.Persistenza.DatabaseHandler;
import it.unical.demacs.backend.Persistenza.Model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class UserBookingController {

    @GetMapping("/api/user/booking")
    public ResponseEntity<?> getBooking(@RequestBody User user) {
        if (DatabaseHandler.getInstance().getUtenteDao().findByUsername(user.getUsername()).join().getUsername() != null) {
            return ResponseEntity.ok(DatabaseHandler.getInstance().getUtenteDao().findByUsername(user.getUsername()).join().getPrenotazioni());
        } else {
            return ResponseEntity.badRequest().body("A person with this username doesn't exists");
        }
    }
}
