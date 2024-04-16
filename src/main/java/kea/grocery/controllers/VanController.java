package kea.grocery.controllers;

import kea.grocery.entities.Delivery;
import kea.grocery.entities.Van;
import kea.grocery.reposities.VanRepository;
import kea.grocery.services.VanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VanController {


    private final VanService vanService;
    private final VanRepository vanRepository;

    public VanController(kea.grocery.services.VanService vanService, VanService vanService1, VanRepository vanRepository) {
        this.vanService = vanService1;
        this.vanRepository = vanRepository;
    }


    @PostMapping("/{id}/deliveries")
    public ResponseEntity<Van> addDeliveryToVan(@PathVariable Long id, @RequestBody Delivery delivery) {
        // add delivery to van
        Van van = vanService.findVanById(id).orElseThrow();
        if (vanService.addDeliveryToVan(delivery, van)){
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.badRequest().build();
        }
    }
}
