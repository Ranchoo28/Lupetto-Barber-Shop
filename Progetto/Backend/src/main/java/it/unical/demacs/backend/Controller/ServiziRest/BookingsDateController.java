package it.unical.demacs.backend.Controller.ServiziRest;

import it.unical.demacs.backend.Persistenza.Model.BookingDate;
import it.unical.demacs.backend.Persistenza.Model.Service;
import it.unical.demacs.backend.Service.HandleBookingDateService;
import it.unical.demacs.backend.Service.Request.BookingDateInsertRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@RestController
@RequiredArgsConstructor
public class BookingsDateController {
    private final HandleBookingDateService handleBookingDateService;

    @PostMapping("/api/bookingdate/insert")
    public ResponseEntity<?> insert(@RequestBody BookingDateInsertRequest request){
        return handleBookingDateService.insertBookingDate(request);
    }

    @GetMapping("/api/bookingdate/delete")
    public ResponseEntity<?> delete(@RequestParam Long idBookingDate, @RequestParam String username){
        return handleBookingDateService.deleteBookingDate(idBookingDate, username);
    }

    @PostMapping("/api/bookingdate/update")
    public ResponseEntity<?> update(@RequestBody BookingDate bookingDate, @RequestParam String username){
        return handleBookingDateService.updateBookingDate(bookingDate, username);
    }

    @GetMapping("/api/bookingdate/get")
    public ResponseEntity<?> get(){
        return handleBookingDateService.getBookingDate();
    }

    @GetMapping("/api/bookingdate/getDatebyService")
    public ResponseEntity<?> getDatebyService(@RequestParam Long idService){
        return handleBookingDateService.getBookingDateByService(idService);
    }

    @GetMapping("/api/bookingdate/getTimebyDate")
    public ResponseEntity<?> getTimebyDate(@RequestParam Date date, @RequestParam Long idService){
        return handleBookingDateService.getBookingDateTime(date, idService);
    }
}
