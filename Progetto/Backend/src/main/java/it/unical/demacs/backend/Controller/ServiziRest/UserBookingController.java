package it.unical.demacs.backend.Controller.ServiziRest;

import it.unical.demacs.backend.Persistenza.DatabaseHandler;
import it.unical.demacs.backend.Persistenza.Model.Booking;
import it.unical.demacs.backend.Persistenza.Model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class UserBookingController {

    @GetMapping("/api/user/booking")
    public ResponseEntity<?> getBooking(@RequestParam String username) {
        User user=DatabaseHandler.getInstance().getUtenteDao().findByUsername(username).join();
        if (user!=null) {
            return ResponseEntity.ok(DatabaseHandler.getInstance().getUtenteDao().findBookings(user.getIdUser()).join());
        } else {
            return ResponseEntity.badRequest().body("A person with this username doesn't exists");
        }
    }

    @GetMapping("/api/user/update-booking")
    public ResponseEntity<?> updateBooking(@RequestParam String username) {
        User user=DatabaseHandler.getInstance().getUtenteDao().findByUsername(username).join();
        if (user.getIdUser() != null)
        {
            return ResponseEntity.ok().body("bravo minchiù");
        }
        else
        {
            return ResponseEntity.badRequest().body("A person with this username doesn't exists");
        }
    }
}
