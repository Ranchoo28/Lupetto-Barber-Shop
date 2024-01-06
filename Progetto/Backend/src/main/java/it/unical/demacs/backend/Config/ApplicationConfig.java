package it.unical.demacs.backend.Config;

import it.unical.demacs.backend.Persistenza.DatabaseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    @Bean
    public UserDetailsService userDetailsService(){
        return username -> (UserDetails) DatabaseHandler.getInstance().getUtenteDao().findByUsername(username).join();
    }
}
