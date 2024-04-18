package dat3.grocery.controllers;

import dat3.grocery.entities.Delivery;
import dat3.grocery.entities.Van;
import dat3.grocery.services.VanService;
import dat3.grocery.reposities.VanRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vans")
public class VanController {


    private final VanService vanService;

    public VanController(VanService vanService, VanService vanService1, VanRepository vanRepository) {
        this.vanService = vanService1;
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

    @GetMapping
    public ResponseEntity<List<Van>> getAllVans() {
        return ResponseEntity.ok(vanService.getAllVans());
    }

}
