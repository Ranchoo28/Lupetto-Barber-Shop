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
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE","HEAD","OPTIONS") // Specifica i metodi HTTP consentiti
                .allowedHeaders("*");
    }
}
