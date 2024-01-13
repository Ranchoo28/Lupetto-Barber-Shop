package it.unical.demacs.backend.Persistenza.DAO;


import it.unical.demacs.backend.Persistenza.Model.BookingDate;

import java.sql.Date;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public interface BookingDateDao {
    CompletableFuture<ArrayList<BookingDate>> findAll();
    CompletableFuture<BookingDate> findByPrimaryKey(Long id);
    CompletableFuture<ArrayList<BookingDate>> findByService(Long id);
    CompletableFuture<ArrayList<BookingDate>> findByDate(Date date, Long id);
    CompletableFuture<ArrayList<BookingDate>> findByDate(Date date);
    CompletableFuture<Boolean> insert(BookingDate booking);
    CompletableFuture<Boolean> delete(Long id);
    CompletableFuture<Boolean> update(BookingDate booking);
    CompletableFuture<Boolean> updateIsValid(Long idBookingDate, boolean b);
    CompletableFuture<Long> findByBookingId(Long id);

}
