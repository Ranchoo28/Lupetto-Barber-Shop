package it.unical.demacs.backend.Service;

import it.unical.demacs.backend.Persistenza.DatabaseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

@Service
public class ScheduledService {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ScheduledService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //@Scheduled(cron = "0 01 00 * * *")
    //@Scheduled(fixedRate = 10000)
    @Async
    public void isValidCheck() {
        System.out.println("Eseguo isValidCheck()");
        String query = "SELECT public.isvalidcheck();";
        jdbcTemplate.execute(query);
    }


}
