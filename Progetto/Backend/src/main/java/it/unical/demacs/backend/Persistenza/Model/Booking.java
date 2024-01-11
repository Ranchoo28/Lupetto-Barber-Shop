package it.unical.demacs.backend.Persistenza.Model;

import java.sql.Date;
import java.sql.Time;
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

    public Booking(User user, BookingDate bookingDate)
    {
        this.user = user;
        this.bookingDate = bookingDate;
    }
}
