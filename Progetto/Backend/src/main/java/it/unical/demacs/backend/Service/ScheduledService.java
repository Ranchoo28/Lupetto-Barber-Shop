package it.unical.demacs.backend.Service;

import it.unical.demacs.backend.Persistenza.DatabaseHandler;
import it.unical.demacs.backend.Persistenza.Model.BookingDate;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;

@Component
@AllArgsConstructor
@Service
public class ScheduledService {
    private final JdbcTemplate jdbcTemplate;

    @Scheduled(cron = "0 01 00 * * *")
    public void isValidCheck() {
        String query = "SELECT public.isvalidcheck();";
        jdbcTemplate.execute(query);
    }

    @Scheduled(fixedRate = 1209600000)
    public void addBookingDate() {
        executeScript();
    }

    private void executeScript() {
        if (!DatabaseHandler.getInstance().getBookingDateDao().findByDate(Date.valueOf(LocalDate.now())).join().isEmpty())
            return;
        LocalDate currentDate = LocalDate.now();
        ArrayList<LocalDate> dateList = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            if (currentDate.plusDays(i).getDayOfWeek().getValue() != 1 && currentDate.plusDays(i).getDayOfWeek().getValue() != 7)
                dateList.add(currentDate.plusDays(i));
        }
        DatabaseHandler.getInstance().openConnection();
        ArrayList<it.unical.demacs.backend.Persistenza.Model.Service> services = DatabaseHandler.getInstance().getServiceDao().findAll().join();
        for (int k = 0; k < dateList.size(); k++) {
            int duration = 0;
            for (int k1 = 0; k1 < services.size(); k1++) {
                Time time = new Time(9, 0, 0);
                boolean flag = true;
                boolean morning = true;
                while (flag) {
                    duration = services.get(k1).getDuration();
                    Time tempTime = Time.valueOf(time.toLocalTime().plusMinutes(duration));
                        if (tempTime.getHours() >= 13 && morning) {
                            time = new Time(14, 30, 0);
                            morning = false;
                        }
                        if (tempTime.getHours() >= 18) {
                            time = new Time(9, 0, 0);
                            flag = false;
                            morning=true;
                            break;
                        }
                        BookingDate bookingDate = new BookingDate(services.get(k1), Date.valueOf(dateList.get(k)), time, true);
                        DatabaseHandler.getInstance().getBookingDateDao().insert(bookingDate);
                        time = Time.valueOf(time.toLocalTime().plusMinutes(duration));
                }
            }
        }
        DatabaseHandler.getInstance().closeConnection();
    }
}
