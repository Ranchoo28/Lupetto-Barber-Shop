package it.unical.demacs.backend.Persistenza.Model;

import it.unical.demacs.backend.Persistenza.DatabaseHandler;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode


public class Hairdresser implements UserDetails {
    private Long id_hairdresser;
    private String email;
    private String password;


    public Hairdresser(Long id_hairdresser, String email, String password) {
        this.id_hairdresser = id_hairdresser;
        this.email = email;
        this.password = password;
    }

    public Hairdresser(Long id_hairdresser) {
        Hairdresser h=DatabaseHandler.getInstance().getHairdresserDao().findByPrimaryKey(id_hairdresser).join();
        this.id_hairdresser = h.getId_hairdresser();
        this.email = h.getEmail();
        this.password = h.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("HAIRDRESSER"));
    }

    @Override
    public String getUsername() { return null; }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
