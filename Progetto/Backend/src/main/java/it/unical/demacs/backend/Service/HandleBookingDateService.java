package it.unical.demacs.backend.Service;

import it.unical.demacs.backend.Persistenza.DatabaseHandler;
import it.unical.demacs.backend.Persistenza.Model.BookingDate;
import it.unical.demacs.backend.Persistenza.Model.Hairdresser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;

@Service
public class HandleBookingDateService {
    public ResponseEntity<?> insertBookingDate(BookingDate bookingDate, String username) {
        try {
            DatabaseHandler.getInstance().openConnection();
            Hairdresser hairdresser = DatabaseHandler.getInstance().getHairdresserDao().findByUsername(username).join();
            if (hairdresser.getId_hairdresser() != null) {
                boolean res = DatabaseHandler.getInstance().getBookingDateDao().insert(bookingDate).join();
                if (res) {
                    return ResponseEntity.ok().body("{\"message\": \"Successful insert of the booking date\"}");
                } else {
                    return ResponseEntity.badRequest().body("{\"message\": \"Insert failed. Probably the booking date already exists\"}");
                }
            }else {
                return ResponseEntity.badRequest().body("{\"message\": \"You are not authorized to perform this action\"}");
            }
        }
        finally {
            DatabaseHandler.getInstance().closeConnection();
        }
    }

    public ResponseEntity<?> deleteBookingDate(Long id, String username)
    {
        try {
            DatabaseHandler.getInstance().openConnection();
            Hairdresser hairdresser = DatabaseHandler.getInstance().getHairdresserDao().findByUsername(username).join();
            if (hairdresser.getId_hairdresser() != null) {
                boolean res = DatabaseHandler.getInstance().getBookingDateDao().delete(id).join();
                if (res) {
                    return ResponseEntity.ok().body("{\"message\": \"Successful delete of the booking date\"}");
                } else {
                    return ResponseEntity.badRequest().body("{\"message\": \"Delete failed\"}");
                }
            }else {
                return ResponseEntity.badRequest().body("{\"message\": \"You are not authorized to perform this action\"}");
            }
        }
        finally {
            DatabaseHandler.getInstance().closeConnection();
        }
    }

    public ResponseEntity<?> updateBookingDate(BookingDate bookingDate, String username)
    {
        try {
            DatabaseHandler.getInstance().openConnection();
            Hairdresser hairdresser = DatabaseHandler.getInstance().getHairdresserDao().findByUsername(username).join();
            if (hairdresser.getId_hairdresser() != null) {
                boolean res = DatabaseHandler.getInstance().getBookingDateDao().update(bookingDate).join();
                if (res) {
                    return ResponseEntity.ok().body("{\"message\": \"Successful update of the booking date\"}");
                } else {
                    return ResponseEntity.badRequest().body("{\"message\": \"Update failed\"}");
                }
            }else {
                return ResponseEntity.badRequest().body("{\"message\": \"You are not authorized to perform this action\"}");
            }
        }
        finally {
            DatabaseHandler.getInstance().closeConnection();
        }
    }

    public ResponseEntity<?> getBookingDate()
    {
        try {
            DatabaseHandler.getInstance().openConnection();
            ArrayList<BookingDate> bookingsDate = DatabaseHandler.getInstance().getBookingDateDao().findAll().join();
            if(!bookingsDate.isEmpty())
            {
                System.out.println(bookingsDate);
                return ResponseEntity.ok().body(bookingsDate);
            }
            else
            {
                return ResponseEntity.badRequest().body("{\"message\": \"No booking date found\"}");
            }
        }
        finally {
            DatabaseHandler.getInstance().closeConnection();
        }
    }

    public ResponseEntity<?> getBookingDateTime(Date date, Long idService)
    {
        try{
            DatabaseHandler.getInstance().openConnection();
            ArrayList<BookingDate> bookingsDate = DatabaseHandler.getInstance().getBookingDateDao().findByDate(date, idService).join();
            if(!bookingsDate.isEmpty())
            {
                return ResponseEntity.ok().body(bookingsDate);
            }
            else
            {
                return ResponseEntity.badRequest().body("{\"message\": \"No booking date found\"}");
            }
        }
        finally {
            DatabaseHandler.getInstance().closeConnection();
        }
    }

    public ResponseEntity<?> getBookingDateByService(Long idService) {
        try{
            DatabaseHandler.getInstance().openConnection();
            ArrayList<BookingDate> bookingsDate = DatabaseHandler.getInstance().getBookingDateDao().findByService(idService).join();
            if(!bookingsDate.isEmpty())
            {
                return ResponseEntity.ok().body(bookingsDate);
            }
            else
            {
                return ResponseEntity.badRequest().body("{\"message\": \"No booking date found\"}");
            }
        }
        finally {
            DatabaseHandler.getInstance().closeConnection();
        }
    }
}
