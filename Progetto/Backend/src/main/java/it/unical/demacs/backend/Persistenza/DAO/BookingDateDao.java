package it.unical.demacs.backend.Persistenza.DAO;


import it.unical.demacs.backend.Persistenza.Model.BookingDate;

import java.sql.Date;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public interface BookingDateDao {
    CompletableFuture<ArrayList<BookingDate>> findAll();
    CompletableFuture<BookingDate> findByPrimaryKey(Long id);
    CompletableFuture<BookingDate> findByDate(Date date);
    CompletableFuture<Boolean> insert(BookingDate booking);
    CompletableFuture<Boolean> delete(BookingDate booking);
}
