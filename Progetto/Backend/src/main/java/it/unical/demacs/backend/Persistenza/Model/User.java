package it.unical.demacs.backend.Persistenza.Model;

import it.unical.demacs.backend.Persistenza.DatabaseHandler;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode

public class User implements UserDetails {
    private Long idUser;
    private String email;
    private String password;
    private String name;
    private String surname;
    private String number;

    public User(Long idUser) throws SQLException {
        User u= DatabaseHandler.getInstance().getUtenteDao().findByPrimaryKey(idUser).join();
        this.idUser=u.getIdUser();
        this.email=u.getEmail();
        this.password=u.getPassword();
        this.name=u.getName();
        this.surname=u.getSurname();
        this.number=u.getNumber();
    }

    public String getRole(){
        return "USER";
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getUsername() { return this.email; }

    @Override
    public boolean isAccountNonExpired() { return true; }

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


}
