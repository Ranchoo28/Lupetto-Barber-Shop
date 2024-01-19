package it.unical.demacs.backend.Controller.Servlet;


import it.unical.demacs.backend.Service.HandleBookingService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@WebServlet("/api/booking/insertWithIntent")
@RequiredArgsConstructor

public class InsertWithIntentBookingServlet extends HttpServlet {

    private final HandleBookingService handleBookingService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        handleBookingService.insertBookingWithIntent(request, response);
    }
}