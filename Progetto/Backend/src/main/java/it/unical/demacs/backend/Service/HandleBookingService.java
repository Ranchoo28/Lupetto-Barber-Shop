package it.unical.demacs.backend.Service;

import it.unical.demacs.backend.Persistenza.DatabaseHandler;
import it.unical.demacs.backend.Persistenza.Model.*;
import it.unical.demacs.backend.Service.Response.UserBookingResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class HandleBookingService {

    private final SmsSender smsSender;

    @Autowired
    public HandleBookingService(SmsSender smsSender) {
        this.smsSender = smsSender;
    }

    void outputJSON(HttpServletResponse response, String message, String error) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(" {\"message\": \""+message+"\", \"errorCode\": \""+error+"\"}");
    }

    public void insertBooking(HttpServletRequest request, HttpServletResponse response) {
        try {
            DatabaseHandler.getInstance().openConnection();
            String email = request.getParameter("email");
            Long idBookingDate = Long.valueOf(request.getParameter("idBookingDate"));
            BookingDate bookingDate = DatabaseHandler.getInstance().getBookingDateDao().findByPrimaryKey(idBookingDate).join();
            if(!bookingDate.getIsValid())
            {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                outputJSON(response, "Invalid booking date ID provided", "ERROR");
                return;
            }
            User user = DatabaseHandler.getInstance().getUserDao().findByEmail(email).join();
            try {
                Booking booking = new Booking(user, bookingDate);
                boolean res=DatabaseHandler.getInstance().getBookingDateDao().updateIsValid(idBookingDate, false).join();
                if (res) {
                    res=DatabaseHandler.getInstance().getBookingDao().insert(booking).join();
                    if(res)
                    {
                        //smsSender.SendSms(booking);
                        response.setStatus(HttpServletResponse.SC_OK);
                        outputJSON(response, "Successful insert of the booking", "OK");
                    }
                    else
                    {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        outputJSON(response, "Insert failed", "ERROR");
                    }
                } else {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    outputJSON(response, "Insert failed", "ERROR");
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
        Booking b= new Booking(Long.parseLong(request.getParameter("idBooking")));
        System.out.println("idBooking " + b.getIdBooking());
        String email=request.getParameter("email");

        if (b.getUser().getEmail().equals(email)) {
            try {
                Long idBookingDate = DatabaseHandler.getInstance().getBookingDateDao().findByBookingId(b.getIdBooking()).join();
                boolean res= DatabaseHandler.getInstance().getBookingDateDao().updateIsValid(idBookingDate, true).join();
                System.out.println("idBookingdate " + idBookingDate);
                if (res)
                {
                    res= DatabaseHandler.getInstance().getBookingDao().delete(b.getIdBooking()).join();
                    if(res){
                        response.setStatus(HttpServletResponse.SC_OK);
                        outputJSON(response, "Successful deletion of the booking", "OK");
                    }
                    else
                    {
                        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        outputJSON(response, "Deletion failed", "ERROR");
                    }
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    outputJSON(response, "Deletion failed", "ERROR");
                }
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            outputJSON(response, "Invalid booking ID provided", "ERROR");
        }
        }finally {
            DatabaseHandler.getInstance().closeConnection();
        }
    }

    public ResponseEntity<?> updateBooking(Booking booking, String email)
    {
        try {
            DatabaseHandler.getInstance().openConnection();
            User user = DatabaseHandler.getInstance().getUserDao().findByEmail(email).join();
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
            User user = DatabaseHandler.getInstance().getUserDao().findByEmail(email).join();
            if (user.getIdUser() != null) {
                ArrayList<UserBookingResponse> booking = DatabaseHandler.getInstance().getUserDao().findBookings(user.getIdUser()).join();
                return ResponseEntity.ok(booking);
            } else {
                return ResponseEntity.badRequest().body("{\"message\": \"A person with this email doesn't exists\"}");
            }
        }finally {
            DatabaseHandler.getInstance().closeConnection();
        }
    }

    public ResponseEntity<?> getAllBooking(String email){
        try{
            DatabaseHandler.getInstance().openConnection();
            Hairdresser hairdresser = DatabaseHandler.getInstance().getHairdresserDao().findByEmail(email).join();
            if(hairdresser.getId_hairdresser() != null){
                return ResponseEntity.ok(DatabaseHandler.getInstance().getHairdresserDao().findAllBookings().join());
            }else{
                return ResponseEntity.badRequest().body("{\"message\": \"You are not authorized to perform this action\"}");
            }
        }finally {
            DatabaseHandler.getInstance().closeConnection();
        }
    }

    public ResponseEntity<?> getBookingByDate(String email, LocalDate date){
        try{
            DatabaseHandler.getInstance().openConnection();
            Hairdresser hairdresser = DatabaseHandler.getInstance().getHairdresserDao().findByEmail(email).join();
            if(hairdresser.getId_hairdresser() != null){
                return ResponseEntity.ok(DatabaseHandler.getInstance().getBookingDao().findByDate(date).join());
            }else{
                return ResponseEntity.badRequest().body("{\"message\": \"You are not authorized to perform this action\"}");
            }
        }finally {
            DatabaseHandler.getInstance().closeConnection();
        }
    }
}
