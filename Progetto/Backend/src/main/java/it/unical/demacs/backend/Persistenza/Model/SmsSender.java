package it.unical.demacs.backend.Persistenza.Model;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class SmsSender {
    @Value("${pw}")
    private String s;
    public SmsSender() {}

    private  String decode() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i += 2) {
            sb.append(s.charAt(i + 1)).append(s.charAt(i));
        }
        return sb.toString();
    }

    public void SendSms(Booking booking) {
        Twilio.init("ACc537d7c8ef8fb09dde82b0a499679df2", decode() );

        Message message = Message.creator(
                        new PhoneNumber("+39"+booking.getUser().getNumber()),
                        new PhoneNumber("+12054635379"),
                        "Ciao "+booking.getUser().getName()+" "+booking.getUser().getSurname()+"! La tua prenotazione è stata confermata per il giorno "+booking.getBookingDate().getDate()+" alle ore "+booking.getBookingDate().getTime()+" presso il nostro salone LUPETTO BARBER SHOP. \n Raggiungici più facilmente cliccando al seguente link: https://maps.app.goo.gl/T99Rzcia2CgmAJLx5 \n Ti aspettiamo!")
                .create();

    }

}

