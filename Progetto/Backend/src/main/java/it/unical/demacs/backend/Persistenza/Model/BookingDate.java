package it.unical.demacs.backend.Persistenza.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import it.unical.demacs.backend.Persistenza.DatabaseHandler;
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
    private Service service;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate date;
    private Time time;
    private Boolean isValid;

    public BookingDate(Service service, Date date, Time time, Boolean isValid){
        this.service = service;
        this.date = date.toLocalDate();
        this.time = time;
        this.isValid = isValid;
    }

    public BookingDate(Long idBookingDate){
        BookingDate b= DatabaseHandler.getInstance().getBookingDateDao().findByPrimaryKey(idBookingDate).join();
        this.service=b.getService();
        this.date=b.getDate();
        this.time=b.getTime();
        this.isValid=b.getIsValid();
    }


}
