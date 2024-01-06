package it.unical.demacs.backend.Persistenza.Model;

import lombok.*;
import java.util.ArrayList;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode

public class User {
    private Long idUser;
    private String username;
    private String password;
    private String name;
    private String surname;
    private String email;
    private char role;

    //private ArrayList<Booking> bookings=new ArrayList<>();

    /*public void addBooking(Booking booking) {
        bookings.add(booking);
    }
    public void deleteBooking(Long idBooking) {
        boolean deleted=false;
        for(int k = 0; k<=bookings.size()-1 || !deleted; k++)
        {
            if(bookings.get(k).getIdBooking().equals(idBooking))
            {
                bookings.remove(k);
                deleted=true;
            }
        }
    }*/


}
