package it.unical.demacs.backend.Persistenza.Model;

import java.sql.Date;
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
    private Date date;
    private Time time;

    public Booking(Long idUser, Long idService, Date date, Time time)
    {
        this.idUser=idUser;
        this.idService=idService;
        this.date=date;
        this.time=time;
    }
}
