package it.unical.demacs.backend.Persistenza.Model;

import java.sql.Date;
import java.sql.Time;

import it.unical.demacs.backend.Persistenza.DatabaseHandler;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor

public class Booking {
    private Long idBooking;
    private User user;
    private BookingDate bookingDate;
    private String payment_intent;

    public Booking(User user, BookingDate bookingDate, String payment_intent)
    {
        this.user = user;
        this.bookingDate = bookingDate;
        this.payment_intent = payment_intent;
    }

    public Booking(Long idBooking)
    {
        Booking b = DatabaseHandler.getInstance().getBookingDao().findByPrimaryKey(idBooking).join();
        this.idBooking = b.getIdBooking();
        this.user = b.getUser();
        this.bookingDate = b.getBookingDate();
    }

    public Booking(User user, BookingDate bookingDate) {
        this.user = user;
        this.bookingDate = bookingDate;
        this.payment_intent = null;
    }
}
