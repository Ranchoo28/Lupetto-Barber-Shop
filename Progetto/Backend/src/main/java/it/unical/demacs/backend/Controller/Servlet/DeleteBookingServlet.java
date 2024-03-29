package it.unical.demacs.backend.Controller.Servlet;

import it.unical.demacs.backend.Persistenza.DatabaseHandler;
import it.unical.demacs.backend.Persistenza.Model.User;
import it.unical.demacs.backend.Service.HandleBookingService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.IOException;


@WebServlet("/api/booking/delete")
@RequiredArgsConstructor

public class DeleteBookingServlet extends HttpServlet {

    private final HandleBookingService handleBookingService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        handleBookingService.deleteBooking(request, response);
    }
}
