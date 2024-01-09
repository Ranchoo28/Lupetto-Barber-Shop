package it.unical.demacs.backend.Persistenza.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor

public class BookingDate {
    private Long idBookingDate;
    private Long idService;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate date;
    private Time time;
    private Boolean isValid;

    public BookingDate(Long idService, Date date, Time time, Boolean isValid){
        this.idService = idService;
        this.date = date.toLocalDate();
        this.time = time;
        this.isValid = isValid;
    }
}
