package it.unical.demacs.backend.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Specifica il percorso a cui applicare le politiche CORS (Cross-Origin Resource Sharing)
                .allowedOrigins("http://localhost:4200") // Specifica l'origine del frontend
                .allowedOrigins("127.0.0.1:8080")
                .allowedMethods("GET", "POST", "PUT", "DELETE","HEAD","OPTIONS") // Specifica i metodi HTTP consentiti
                .allowedHeaders("*") // Specifica gli header consentiti
                .allowCredentials(true); // Specifica se è consentito l'invio di credenziali (cookie)
    }
}
