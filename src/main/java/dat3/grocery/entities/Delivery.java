package dat3.grocery.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate deliveryDate;
    private String fromWarehouse; //Replace with actual warehouse entity
    private String toDestination;

    @ManyToOne
    private Van van;

   @OneToMany
    private List<ProductOrder> productOrders;

    private double totalPrice;

    private double totalWeightInGrams = 0.0;

    public int getTotalWeightInKG() {
        return (int) Math.ceil((double)totalWeightInGrams / 1000);
    }


}
