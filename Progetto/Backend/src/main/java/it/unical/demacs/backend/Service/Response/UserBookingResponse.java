package it.unical.demacs.backend.Service.Response;

import com.fasterxml.jackson.annotation.JsonFormat;
import it.unical.demacs.backend.Persistenza.Model.Booking;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;
import java.sql.Time;

@Getter
@Setter
@ToString
public class UserBookingResponse {
    String service_name;
    @JsonFormat(pattern="yyyy-MM-dd")
    Date date;
    Time time;
}
