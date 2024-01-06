package it.unical.demacs.backend.Persistenza.Model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode

public class User implements UserDetails {
    private Long idUser;
    private String username;
    private String password;
    private String name;
    private String surname;
    private String email;
    private char role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return Objects.equals(new SimpleGrantedAuthority(String.valueOf(role)), new SimpleGrantedAuthority("u"));
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

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
