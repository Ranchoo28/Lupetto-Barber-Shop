package it.unical.demacs.backend.Service.Response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;

@Getter
@Setter

public class HaidresserBookingResponse {
    Long booking_id;
    String user_name;
    String user_surname;
    String service_name;
    @JsonFormat(pattern="yyyy-MM-dd")
    LocalDate date;
    Time time;
}
