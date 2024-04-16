package dat3.grocery.services;

import dat3.grocery.entities.Delivery;
import dat3.grocery.entities.Van;
import dat3.grocery.reposities.VanRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class VanService {
    private VanRepository vanRepository;

    public VanService(VanRepository vanRepository) {
        this.vanRepository = vanRepository;
    }

    public List<Van> getAll() {
        return vanRepository.findAll();
    }

    public Optional<Van> findVanById(Long id) {
            return vanRepository.findById(id);
        }

    public boolean addDeliveryToVan(Delivery delivery, Van van) {
        // if combined weight < capacity
        // add delivery to van
        if (van.hasCapacityForDelivery(delivery)) {
            van.getDeliveries().add(delivery);
            vanRepository.save(van);
            return true;
        } else {
            return false;
        }


    }
    public boolean addDeliveryToVan(Delivery delivery, Long vanId) {
        Van van = vanRepository.findById(vanId).orElseThrow();
        return addDeliveryToVan(delivery, van);
    }

}

