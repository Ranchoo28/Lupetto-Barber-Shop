package it.unical.demacs.backend.Controller.ServiziRest;

import it.unical.demacs.backend.Service.HandleBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController

@RequiredArgsConstructor
public class HairdresserBookingController {

    private final HandleBookingService handleBookingService;

    @GetMapping("/api/hairdresser/bookings")
    public ResponseEntity<?> getAllBookings(@RequestParam String email){
        return handleBookingService.getAllBooking(email);
    }

    @GetMapping("/api/hairdresser/bookings/getByDate")
    public ResponseEntity<?> getBookingsByDate(@RequestParam String email, @RequestParam String date){
        return handleBookingService.getBookingByDate(email, LocalDate.parse(date));
    }
}
