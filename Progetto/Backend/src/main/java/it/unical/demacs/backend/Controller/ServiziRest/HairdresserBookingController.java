package it.unical.demacs.backend.Controller.ServiziRest;

import it.unical.demacs.backend.Service.HandleBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class HairdresserBookingController {

    private final HandleBookingService handleBookingService;

    @GetMapping("/api/hairdresser/bookings")
    public ResponseEntity<?> getAllBookings(@RequestParam String email){
        return handleBookingService.getAllBooking(email);
    }
}
