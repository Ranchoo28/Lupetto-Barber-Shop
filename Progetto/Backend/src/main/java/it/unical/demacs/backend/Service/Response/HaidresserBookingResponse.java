package it.unical.demacs.backend.Service.Response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import java.sql.Date;
import java.sql.Time;

@Getter
@Setter

public class HaidresserBookingResponse {
    String user_name;
    String user_surname;
    String service_name;
    @JsonFormat(pattern="yyyy-MM-dd")
    Date date;
    Time time;
}
