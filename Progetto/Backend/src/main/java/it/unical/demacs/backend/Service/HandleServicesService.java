package it.unical.demacs.backend.Service;

import it.unical.demacs.backend.Persistenza.DatabaseHandler;
import it.unical.demacs.backend.Persistenza.Model.Hairdresser;
import it.unical.demacs.backend.Persistenza.Model.Service;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;


@org.springframework.stereotype.Service
public class HandleServicesService {
    public ResponseEntity<?> insertService(Service services, String username) {
        try {
            DatabaseHandler.getInstance().openConnection();
            Hairdresser hairdresser = DatabaseHandler.getInstance().getHairdresserDao().findByEmail(username).join();
            if (hairdresser.getId_hairdresser() != null) {
                boolean res = DatabaseHandler.getInstance().getServiceDao().insert(services).join();
                if (res) {
                    return ResponseEntity.ok().body("{\"message\": \"Successful insert of the service\"}");

                } else {
                    return ResponseEntity.badRequest().body("{\"message\": \"Insert failed. Probably the service already exists\"}");
                }
            } else {
                return ResponseEntity.badRequest().body("{\"message\": \"You are not authorized to perform this action\"}");
            }
        }
        finally {
            DatabaseHandler.getInstance().closeConnection();
        }
    }

    public ResponseEntity<?> deleteService(Long idService, String email) {
        try {
            DatabaseHandler.getInstance().openConnection();
            Hairdresser hairdresser = DatabaseHandler.getInstance().getHairdresserDao().findByEmail(email).join();
            if (hairdresser.getId_hairdresser() != null) {
                boolean res = DatabaseHandler.getInstance().getServiceDao().delete(idService).join();
                if (res) {
                    return ResponseEntity.ok().body("{\"message\": \"Successful delete of the service\"}");
                } else {
                    return ResponseEntity.badRequest().body("{\"message\": \"Delete failed\"}");
                }
            } else {
                return ResponseEntity.badRequest().body("{\"message\": \"You are not authorized to perform this action\"}");
            }
        }
        finally {
            DatabaseHandler.getInstance().closeConnection();
        }
    }
    public ResponseEntity<?> updateService(Service services, String email)  {
        try {
            DatabaseHandler.getInstance().openConnection();
            Hairdresser hairdresser = DatabaseHandler.getInstance().getHairdresserDao().findByEmail(email).join();
            if (hairdresser.getId_hairdresser() != null) {
                boolean res = DatabaseHandler.getInstance().getServiceDao().update(services).join();
                if (res) {
                    return ResponseEntity.ok().body("{\"message\": \"Successful update of the service\"}");
                } else {
                    return ResponseEntity.badRequest().body("{\"message\": \"Update failed.\"}");
                }
            } else {
                return ResponseEntity.badRequest().body("{\"message\": \"You are not authorized to perform this action\"}");
            }
        }
        finally {
            DatabaseHandler.getInstance().closeConnection();
        }
    }
    public ResponseEntity<?> getService()  {
        try {
            DatabaseHandler.getInstance().openConnection();
            ArrayList<Service> services = DatabaseHandler.getInstance().getServiceDao().findAll().join();
            if (services.isEmpty()) {
                return ResponseEntity.badRequest().body("{\"message\": \"No services found\"}");
            } else {
                return ResponseEntity.ok(services);
            }
        }
        finally {
            DatabaseHandler.getInstance().closeConnection();
        }
    }

    public ResponseEntity<?> getService(String sex)
    {
        try {
            DatabaseHandler.getInstance().openConnection();
            ArrayList<Service> services = DatabaseHandler.getInstance().getServiceDao().findBySex(sex).join();
            if (services.isEmpty()) {
                return ResponseEntity.badRequest().body("{\"message\": \"No services found\"}");
            } else {
                return ResponseEntity.ok(services);
            }
        }
        finally {
            DatabaseHandler.getInstance().closeConnection();
        }
    }
}
