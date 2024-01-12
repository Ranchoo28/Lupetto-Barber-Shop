package it.unical.demacs.backend.Service;

import it.unical.demacs.backend.Persistenza.DatabaseHandler;
import it.unical.demacs.backend.Persistenza.Model.BookingDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;

@Component
@Service
public class ScheduledService {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ScheduledService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    //@Scheduled(cron = "0 01 00 * * *")
   /* @Async
    public void isValidCheck() {
        System.out.println("Eseguo isValidCheck()");
        String query = "SELECT public.isvalidcheck();";
        jdbcTemplate.execute(query);
    }*/

    @Scheduled(fixedRate = 1209600000)
    public void addBookingDate(){
        executeScript();
    }

    private void executeScript(){
        if(!DatabaseHandler.getInstance().getBookingDateDao().findByDate(Date.valueOf(LocalDate.now())).join().isEmpty())
            return;
        LocalDate currentDate = LocalDate.now();
        ArrayList<LocalDate> dateList = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            if(currentDate.plusDays(i).getDayOfWeek().getValue() != 6 &&currentDate.plusDays(i).getDayOfWeek().getValue() != 7)
                dateList.add(currentDate.plusDays(i));
        }
        DatabaseHandler.getInstance().openConnection();
        ArrayList<it.unical.demacs.backend.Persistenza.Model.Service> services = DatabaseHandler.getInstance().getServiceDao().findAll().join();
        for(int k=0; k<dateList.size(); k++)
        {
            Time time = new Time(9, 0, 0);
            int duration=0;
            for(int k1=0; k1<services.size(); k1++)
            {
                duration = services.get(k1).getDuration();
                if(time.getHours() == 12 && time.getMinutes()+duration >= 59)
                    time = new Time(14, 30, 0);
                if(time.getHours() == 18 && time.getMinutes()+duration >= 59)
                    break;
                BookingDate bookingDate = new BookingDate(services.get(k1), Date.valueOf(dateList.get(k)), time, true);
                DatabaseHandler.getInstance().getBookingDateDao().insert(bookingDate);
                time=Time.valueOf(time.toLocalTime().plusMinutes(duration));
            }
        }
        DatabaseHandler.getInstance().closeConnection();
    }
}
