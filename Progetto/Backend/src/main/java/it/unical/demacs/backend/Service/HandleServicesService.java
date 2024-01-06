package it.unical.demacs.backend.Service;

import it.unical.demacs.backend.Persistenza.DatabaseHandler;
import it.unical.demacs.backend.Persistenza.Model.Service;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;


@org.springframework.stereotype.Service
public class HandleServicesService {
    public ResponseEntity<?> insertService(Service services, String username) {
        String role = String.valueOf(DatabaseHandler.getInstance().getUtenteDao().findByUsername(username).join().getRole());
        if(role.equals("ADMIN") || role.equals("HAIRDRESSER")) {
            if (DatabaseHandler.getInstance().getServiceDao().insert(services).join()) {
                return ResponseEntity.ok("Successful insert of the service");
            } else {
                return ResponseEntity.badRequest().body("Insert failed. Probably the service already exists");
            }
        } else {
            return ResponseEntity.badRequest().body("You are not authorized to perform this action");
        }
    }

    public ResponseEntity<?> deleteService(Long idService, String username) {
        String role = String.valueOf(DatabaseHandler.getInstance().getUtenteDao().findByUsername(username).join().getRole());
        if(role.equals("ADMIN") || role.equals("HAIRDRESSER")) {
            if (DatabaseHandler.getInstance().getServiceDao().delete(idService).join()) {
                return ResponseEntity.ok("Successful delete of the service");
            } else {
                return ResponseEntity.badRequest().body("Delete failed.");
            }
        } else {
            return ResponseEntity.badRequest().body("You are not authorized to perform this action");
        }
    }
    public ResponseEntity<?> updateService(Service services, String username) {
        String role = String.valueOf(DatabaseHandler.getInstance().getUtenteDao().findByUsername(username).join().getRole());
        if(role.equals("ADMIN") || role.equals("HAIRDRESSER")) {
            if (DatabaseHandler.getInstance().getServiceDao().update(services).join()) {
                return ResponseEntity.ok("Successful update of the service");
            } else {
                return ResponseEntity.badRequest().body("Update failed.");
            }
        } else {
            return ResponseEntity.badRequest().body("You are not authorized to perform this action");
        }
    }
    public ResponseEntity<?> getService() {
        ArrayList<Service> services= DatabaseHandler.getInstance().getServiceDao().findAll().join();
        if(services.isEmpty()){
            return ResponseEntity.badRequest().body("No services found");
        } else {
            return ResponseEntity.ok(services);
        }
    }
}
