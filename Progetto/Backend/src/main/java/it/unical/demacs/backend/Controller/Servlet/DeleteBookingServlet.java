package it.unical.demacs.backend.Controller.Servlet;

import it.unical.demacs.backend.Persistenza.DatabaseHandler;
import it.unical.demacs.backend.Persistenza.Model.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.IOException;

@CrossOrigin(origins = "http://localhost:4200")
@WebServlet("/api/booking/delete")
public class DeleteBookingServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long bookingId= Long.valueOf(request.getParameter("idBooking"));
        String username=request.getParameter("username");
        User user=DatabaseHandler.getInstance().getUtenteDao().findByUsername(username).join();
        boolean isValid = DatabaseHandler.getInstance().getBookingDao().isValid(bookingId, user.getIdUser()).join();
        System.out.println("isValid: "+isValid);
        if (isValid) {
            try {
                if (DatabaseHandler.getInstance().getBookingDao().delete(bookingId).join()) {
                    //user.deleteBooking(bookingId);
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
}
