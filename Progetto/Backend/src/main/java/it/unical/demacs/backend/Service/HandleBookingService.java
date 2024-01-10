package it.unical.demacs.backend.Service;

import it.unical.demacs.backend.Persistenza.DatabaseHandler;
import it.unical.demacs.backend.Persistenza.Model.Booking;
import it.unical.demacs.backend.Persistenza.Model.BookingDate;
import it.unical.demacs.backend.Persistenza.Model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

@Service
public class HandleBookingService {

    public void insertBooking(HttpServletRequest request, HttpServletResponse response) {
        try {
            DatabaseHandler.getInstance().openConnection();
            String email = request.getParameter("email");
            Long idBookingDate = Long.valueOf(request.getParameter("idBookingDate"));
            BookingDate bookingDate =DatabaseHandler.getInstance().getBookingDateDao().findByPrimaryKey(idBookingDate).join();
            if(!bookingDate.getIsValid())
            {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Invalid booking date ID provided");
                return;
            }
            User user = DatabaseHandler.getInstance().getUtenteDao().findByEmail(email).join();
            try {
                Booking booking = new Booking(user.getIdUser(), idBookingDate);
                boolean res = DatabaseHandler.getInstance().getBookingDao().insert(booking).join();
                if (res) {
                    res=DatabaseHandler.getInstance().getBookingDateDao().updateIsValid(idBookingDate, false).join();
                    if(res)
                    {
                        response.setStatus(HttpServletResponse.SC_OK);
                        response.getWriter().write("Successful insert of the booking");
                    }
                    else
                    {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        response.getWriter().write("Insert failed");
                    }
                } else {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    response.getWriter().write("Insert failed");
                }
            } catch (NumberFormatException | IOException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            DatabaseHandler.getInstance().closeConnection();
        }
    }

    public void deleteBooking(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{
            DatabaseHandler.getInstance().openConnection();
        Long bookingId= Long.valueOf(request.getParameter("idBooking"));
        String email=request.getParameter("email");
        User user=DatabaseHandler.getInstance().getUtenteDao().findByEmail(email).join();
        boolean isValid =DatabaseHandler.getInstance().getBookingDao().isValid(bookingId, user.getIdUser()).join();
        if (isValid) {
            try {
                boolean res= DatabaseHandler.getInstance().getBookingDao().delete(bookingId).join();
                if (res) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getWriter().write("Successful cancellation of the reservation");
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("Deletion failed");
                }
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid booking ID provided");
        }
        }finally {
            DatabaseHandler.getInstance().closeConnection();
        }
    }

    public ResponseEntity<?> updateBooking(Booking booking, String email)
    {
        try {
            DatabaseHandler.getInstance().openConnection();
            User user = DatabaseHandler.getInstance().getUtenteDao().findByEmail(email).join();
            if (user != null) {
                if (DatabaseHandler.getInstance().getBookingDao().isValid(booking.getIdBooking(), user.getIdUser()).join()) {
                    boolean res = DatabaseHandler.getInstance().getBookingDao().update(booking).join();
                    return ResponseEntity.ok(res);
                } else {
                    return ResponseEntity.badRequest().body("{\"message\": \"Invalid booking ID provided\"}");
                }
            } else {
                return ResponseEntity.badRequest().body("{\"message\": \"A person with this username doesn't exists\"}");
            }
        }finally {
            DatabaseHandler.getInstance().closeConnection();
        }
    }

    public ResponseEntity<?> getBooking(String email)
    {
        try {
            DatabaseHandler.getInstance().openConnection();
            User user = DatabaseHandler.getInstance().getUtenteDao().findByEmail(email).join();
            if (user != null) {
                ArrayList<BookingDate> booking = DatabaseHandler.getInstance().getUtenteDao().findBookings(user.getIdUser()).join();
                return ResponseEntity.ok(booking);
            } else {
                return ResponseEntity.badRequest().body("{\"message\": \"A person with this username doesn't exists\"}");
            }
        }finally {
            DatabaseHandler.getInstance().closeConnection();
        }
    }

}
