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
    private Long idUser;
    private Long idBookingDate;

    public Booking(Long idUser, Long idBookingDate)
    {
        this.idUser = idUser;
        this.idBookingDate = idBookingDate;
    }
}
