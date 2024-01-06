package it.unical.demacs.backend.Controller.ServiziRest;

import it.unical.demacs.backend.Persistenza.Model.Service;
import it.unical.demacs.backend.Service.HandleServicesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor

public class ServiceController {

    private final HandleServicesService handleServicesService;

    @PostMapping("/api/service/insert")
    public ResponseEntity<?> insert(@RequestBody Service services, @RequestParam String username){
        return handleServicesService.insertService(services, username);
    }

    @GetMapping("/api/service/delete")
    public ResponseEntity<?> delete(@RequestParam Long idService, @RequestParam String username){
        return handleServicesService.deleteService(idService, username);
    }

    @PostMapping("/api/service/update")
    public ResponseEntity<?> update(@RequestBody Service services, @RequestParam String username){
        return handleServicesService.updateService(services, username);
    }

    @GetMapping("/api/service/get")
    public ResponseEntity<?> getService(){
        return handleServicesService.getService();
    }
}
