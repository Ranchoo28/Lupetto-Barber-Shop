package it.unical.demacs.backend.Service;

import it.unical.demacs.backend.Persistenza.DatabaseHandler;
import it.unical.demacs.backend.Persistenza.Model.Service;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;


@org.springframework.stereotype.Service
public class HandleServicesService {
    public ResponseEntity<?> insertService(Service services, String username) {
        /* TODO -> DA FIXARE IN BASE ALLE NUOVE TABELLE
        String role = String.valueOf(DatabaseHandler.getInstance().getUtenteDao().findByUsername(username).join().getRole());
        if(role.equals(Role.ADMIN.toString()) || role.equals(Role.HAIRDRESSER.toString())) {
            boolean res= DatabaseHandler.getInstance().getServiceDao().insert(services).join();
            DatabaseHandler.getInstance().closeConnection();
            if (res) {
                return ResponseEntity.ok().body("{\"message\": \"Successful insert of the service\"}");

            } else {
                return ResponseEntity.badRequest().body("{\"message\": \"Insert failed. Probably the service already exists\"}");
            }
        } else {
            return ResponseEntity.badRequest().body("{\"message\": \"You are not authorized to perform this action\"}");
        }

         */
        return null;
    }

    public ResponseEntity<?> deleteService(Long idService, String username) {
        /* TODO -> DA FIXARE IN BASE ALLE NUOVE TABELLE
        String role = String.valueOf(DatabaseHandler.getInstance().getUtenteDao().findByUsername(username).join().getRole());
        if(role.equals("ADMIN") || role.equals("HAIRDRESSER")) {
            boolean res= DatabaseHandler.getInstance().getServiceDao().delete(idService).join();
            DatabaseHandler.getInstance().closeConnection();
            if (res) {
                return ResponseEntity.ok().body("{\"message\": \"Successful delete of the service\"}");
            } else {
                 return ResponseEntity.badRequest().body("{\"message\": \"Delete failed\"}");
            }
        } else {
            return ResponseEntity.badRequest().body("{\"message\": \"You are not authorized to perform this action\"}");
        }
         */
        return null;
    }
    public ResponseEntity<?> updateService(Service services, String username)  {
        /* TODO -> DA FIXARE IN BASE ALLE NUOVE TABELLE
        String role = String.valueOf(DatabaseHandler.getInstance().getUtenteDao().findByUsername(username).join().getRole());
        if(role.equals("ADMIN") || role.equals("HAIRDRESSER")) {
            boolean res= DatabaseHandler.getInstance().getServiceDao().update(services).join();
            DatabaseHandler.getInstance().closeConnection();
            if (res) {
                return ResponseEntity.ok().body("{\"message\": \"Successful update of the service\"}");
            } else {
                 return ResponseEntity.badRequest().body("{\"message\": \"Update failed.\"}");
            }
        } else {
             return ResponseEntity.badRequest().body("{\"message\": \"You are not authorized to perform this action\"}");
        }

         */
        return null;
    }
    public ResponseEntity<?> getService()  {
        ArrayList<Service> services= DatabaseHandler.getInstance().getServiceDao().findAll().join();
        DatabaseHandler.getInstance().closeConnection();
        if(services.isEmpty()){
            return ResponseEntity.badRequest().body("{\"message\": \"No services found\"}");
        } else {
            return ResponseEntity.ok(services);
        }
    }
}
