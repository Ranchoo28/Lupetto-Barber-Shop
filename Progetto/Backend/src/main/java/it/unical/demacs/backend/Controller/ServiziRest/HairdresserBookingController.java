package it.unical.demacs.backend.Controller.ServiziRest;

import it.unical.demacs.backend.Service.HandleBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class HairdresserBookingController {

    private final HandleBookingService handleBookingService;

    @GetMapping("/api/hairdresser/bookings")
    public ResponseEntity<?> getAllBookings(@RequestParam String email){
        return handleBookingService.getAllBooking(email);
    }

    @PostMapping("/api/hairdresser/bookings/getByDate")
    public ResponseEntity<?> getBookingsByDate(@RequestBody String email, @RequestBody LocalDate date){
        return handleBookingService.getBookingByDate(email, date);
    }
}
