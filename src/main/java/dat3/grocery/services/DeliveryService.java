package dat3.grocery.services;

import dat3.grocery.entities.Delivery;
import dat3.grocery.entities.ProductOrder;
import dat3.grocery.entities.Van;
import dat3.grocery.dto.DeliveryDTO;
import dat3.grocery.reposities.DeliveryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final ProductOrderService productOrderService;
    private final VanService vanService;

    public DeliveryService(DeliveryRepository deliveryRepository, ProductOrderService productOrderService, VanService vanService) {
        this.deliveryRepository = deliveryRepository;
        this.productOrderService = productOrderService;
        this.vanService = vanService;
    }



    public Delivery createDelivery(DeliveryDTO deliveryDTO) {
        Delivery delivery = new Delivery();
        delivery.setDeliveryDate(deliveryDTO.getDeliveryDate());
        delivery.setFromWarehouse(deliveryDTO.getFromWarehouse());
        delivery.setToDestination(deliveryDTO.getToDestination());

        if (deliveryDTO.getVanId() != null) {
            Van van = vanService.findVanById(deliveryDTO.getVanId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Van not found"));
            delivery.setVan(van);
        }

        delivery.setTotalWeightInGrams(0);
        delivery.setTotalPrice(0);

        return deliveryRepository.save(delivery);
    }




    public List<Delivery> getAllDeliveries() {
        return deliveryRepository.findAll();
    }

    public Delivery getDeliveryById(Long id) {
        return deliveryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Delivery not found"));
    }

//    public Delivery addProductOrderToDelivery(Long deliveryId, Long productOrderId) {
//        Delivery delivery = getDeliveryById(deliveryId);
//        ProductOrder productOrder = productOrderService.getProductOrderById(productOrderId)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Order not found"));
//
//        delivery.getProductOrders().add(productOrder);
//        updateTotals(delivery);
//        return deliveryRepository.save(delivery);
//    }

    public Delivery addProductOrdersToDelivery(Long deliveryId, List<Long> productOrderIds) {
        Delivery delivery = getDeliveryById(deliveryId);
        List<ProductOrder> productOrdersToAdd = productOrderService.getProductOrdersByIds(productOrderIds);

        for (ProductOrder productOrder : productOrdersToAdd) {
            if (!delivery.getProductOrders().contains(productOrder)) {
                delivery.getProductOrders().add(productOrder);
            } else {
                throw new IllegalStateException("Product order already added to this delivery");
            }
        }
        updateTotals(delivery);
        return deliveryRepository.save(delivery);
    }



    private void updateTotals(Delivery delivery) {
        double totalWeightInGrams = 0;
        double totalPrice = 0;

        for (ProductOrder order : delivery.getProductOrders()) {
            totalWeightInGrams += order.getQuantity() * order.getProduct().getWeightInGrams();
            totalPrice += order.getQuantity() * order.getProduct().getPrice();
        }

        delivery.setTotalWeightInGrams(totalWeightInGrams);
        delivery.setTotalPrice(totalPrice);
    }

    public Delivery updateDelivery(Delivery delivery) {
        updateTotals(delivery);
        return deliveryRepository.save(delivery);
    }

    public void deleteDelivery(Long id) {
        deliveryRepository.deleteById(id);
    }

}

