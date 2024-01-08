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
        String username= request.getParameter("username");
        Long idService=Long.valueOf(request.getParameter("idService"));
        Date date= Date.valueOf(request.getParameter("data"));
        Time time=Time.valueOf(request.getParameter("time"));
        User user= DatabaseHandler.getInstance().getUtenteDao().findByUsername(username).join();
        try {
            Booking booking=new Booking(user.getIdUser(), 1L);
            boolean res= DatabaseHandler.getInstance().getBookingDao().insert(booking).join();
            DatabaseHandler.getInstance().closeConnection();
            if (res) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("Successful insert of the booking");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("Insert failed");
            }
        } catch (NumberFormatException | IOException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    public void deleteBooking(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long bookingId= Long.valueOf(request.getParameter("idBooking"));
        String username=request.getParameter("username");
        User user=DatabaseHandler.getInstance().getUtenteDao().findByUsername(username).join();
        boolean isValid = DatabaseHandler.getInstance().getBookingDao().isValid(bookingId, user.getIdUser()).join();
        if (isValid) {
            try {
                boolean res= DatabaseHandler.getInstance().getBookingDao().delete(bookingId).join();
                DatabaseHandler.getInstance().closeConnection();
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
    }

    public ResponseEntity<?> updateBooking(Booking booking, String username)
    {
        User user=DatabaseHandler.getInstance().getUtenteDao().findByUsername(username).join();
        if (user!=null) {
            if(DatabaseHandler.getInstance().getBookingDao().isValid(booking.getIdBooking(), user.getIdUser()).join()) {
                boolean res= DatabaseHandler.getInstance().getBookingDao().update(booking).join();
                DatabaseHandler.getInstance().closeConnection();
                return ResponseEntity.ok(res);
            } else {
                return ResponseEntity.badRequest().body("Invalid booking ID provided");
            }
        } else {
            return ResponseEntity.badRequest().body("A person with this username doesn't exists");
        }
    }

    public ResponseEntity<?> getBooking(String username)
    {
        User user=DatabaseHandler.getInstance().getUtenteDao().findByUsername(username).join();
        if (user!=null) {
            ArrayList<BookingDate> booking = DatabaseHandler.getInstance().getUtenteDao().findBookings(user.getIdUser()).join();
            DatabaseHandler.getInstance().closeConnection();
            return ResponseEntity.ok(booking);
        } else {
            return ResponseEntity.badRequest().body("A person with this username doesn't exists");
        }
    }

}
