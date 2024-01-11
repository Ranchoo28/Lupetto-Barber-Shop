package it.unical.demacs.backend.Service.Request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;

@Setter
@Getter
public class BookingDateInsertRequest {
    String email;
    Long id_service;
    @JsonFormat(pattern = "yyyy-MM-dd")
    Date date;
    Time time;
}
