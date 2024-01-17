package it.unical.demacs.backend.Persistenza.Model;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.scheduling.annotation.Async;

@Async
public class SmsSender {
    private static SmsSender instance;
    private SmsSender() {}
    public static SmsSender getInstance()
    {
        if(instance==null)
            instance=new SmsSender();
        return instance;
    }

    public static void SendSms(Booking booking) {
        Twilio.init("AC9615a241f3b49db73939c5135872d9d4", "2596be91b4119f84134826b51f8f7767");

        Message message = Message.creator(
                        new PhoneNumber(booking.getUser().getNumber()),
                        new PhoneNumber("+12054635379"),
                        "Ciao "+booking.getUser().getName()+" "+booking.getUser().getSurname()+"! La tua prenotazione è stata confermata per il giorno "+booking.getBookingDate().getDate()+" alle ore "+booking.getBookingDate().getTime()+" presso il nostro salone LUPETTO BARBER SHOP. Ti aspettiamo!")
                .create();

    }

}

