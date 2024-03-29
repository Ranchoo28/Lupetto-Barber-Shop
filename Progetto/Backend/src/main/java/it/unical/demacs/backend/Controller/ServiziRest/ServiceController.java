package it.unical.demacs.backend.Controller.ServiziRest;

import it.unical.demacs.backend.Persistenza.Model.Service;
import it.unical.demacs.backend.Service.HandleServicesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;


@RestController
@RequiredArgsConstructor

public class ServiceController {

    private final HandleServicesService handleServicesService;

    @PostMapping("/api/service/insert")
    public ResponseEntity<?> insert(@RequestBody Service services, @RequestParam String email){
        return handleServicesService.insertService(services, email);
    }

    @GetMapping("/api/service/delete")
    public ResponseEntity<?> delete(@RequestParam Long idService, @RequestParam String email){
        return handleServicesService.deleteService(idService, email);
    }

    @PostMapping("/api/service/update")
    public ResponseEntity<?> update(@RequestBody Service services, @RequestParam String email){
        return handleServicesService.updateService(services, email);
    }

    @GetMapping("/api/service/get")
    public ResponseEntity<?> getService() {
        return handleServicesService.getService();
    }

    @GetMapping("/api/service/getBySex")
    public ResponseEntity<?> getService(@RequestParam String sex) {
        return handleServicesService.getService(sex);
    }
}
