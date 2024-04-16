package kea.grocery.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Van {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;
    private String model;
    private int capacityInKg;

    @Getter
    @OneToMany
    private List<Delivery> deliveries;

    public int getCombinedWeightOfDeliveries(){
        int totalWeightOfDeliveries = 0;
        for (Delivery d : deliveries) {
            totalWeightOfDeliveries += d.getTotalWeightInKG();
        }
        return totalWeightOfDeliveries;
    }


    public boolean hasCapacityForDelivery(Delivery delivery){
        return getCombinedWeightOfDeliveries() + delivery.getTotalWeightInKG() <= capacityInKg;
    }
}
