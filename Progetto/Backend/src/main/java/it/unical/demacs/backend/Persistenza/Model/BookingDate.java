package it.unical.demacs.backend.Persistenza.Model;

import lombok.*;

import java.sql.Date;
import java.sql.Time;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor

public class BookingDate {
    private Long idBookingDate;
    private Long idService;
    private Date date;
    private Time time;
    private Boolean isValid;

    public BookingDate(Long idService, Date date, Time time, Boolean isValid){
        this.idService = idService;
        this.date = date;
        this.time = time;
        this.isValid = isValid;
    }
}
