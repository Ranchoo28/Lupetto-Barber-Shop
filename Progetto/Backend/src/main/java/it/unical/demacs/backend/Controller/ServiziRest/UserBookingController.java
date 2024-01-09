package it.unical.demacs.backend.Controller.ServiziRest;

import it.unical.demacs.backend.Persistenza.DatabaseHandler;
import it.unical.demacs.backend.Persistenza.Model.Booking;
import it.unical.demacs.backend.Persistenza.Model.User;
import it.unical.demacs.backend.Service.HandleBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor

public class UserBookingController {

    private final HandleBookingService handleBookingService;

    @GetMapping("/api/user/booking")
    public ResponseEntity<?> getBooking(@RequestParam String email) {
        return handleBookingService.getBooking(email);
    }

    @PostMapping("/api/user/update-booking")
    public ResponseEntity<?> updateBooking(@RequestBody Booking booking, @RequestParam String email){
        return handleBookingService.updateBooking(booking, email);
    }
}
