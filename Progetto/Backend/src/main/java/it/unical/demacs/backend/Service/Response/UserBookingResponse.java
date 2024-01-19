package it.unical.demacs.backend.Service.Response;

import com.fasterxml.jackson.annotation.JsonFormat;
import it.unical.demacs.backend.Persistenza.Model.Booking;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.cglib.core.Local;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;

@Getter
@Setter
@ToString
public class UserBookingResponse {
    String service_name;
    @JsonFormat(pattern="yyyy-MM-dd")
    LocalDate date;
    Time time;
    Long id_booking;
    String intent;
}
