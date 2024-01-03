package it.unical.demacs.backend.Persistenza.Model;

import java.sql.Time;
import java.time.LocalDate;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode

public class Booking {
    private Long idBooking;
    private Long idUser;
    private Long idService;
    private LocalDate date;
    private Time time;
}
