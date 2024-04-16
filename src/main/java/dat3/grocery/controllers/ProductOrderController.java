package dat3.grocery.controllers;


import dat3.grocery.dto.OrderRequest;
import dat3.grocery.entities.ProductOrder;
import dat3.grocery.services.ProductOrderService;
import dat3.grocery.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/productorder")
public class ProductOrderController {
    private final ProductOrderService productOrderService;
    private final ProductService productService;

    public ProductOrderController(ProductOrderService productOrderService, ProductService productService) {
        this.productOrderService = productOrderService;
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductOrder>> getAllOrders() {
        List<ProductOrder> orders = productOrderService.getAllProductOrders();
        return ResponseEntity.ok(orders);
    }


    @PostMapping
    public ResponseEntity<ProductOrder> createOrder(@RequestBody OrderRequest orderRequest) {
        try {
            ProductOrder productOrder = productOrderService.createProductOrder(orderRequest.getProductId(), orderRequest.getQuantity());
            return ResponseEntity.ok(productOrder);
        } catch (IllegalArgumentException | ResponseStatusException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductOrder> updateOrder(@RequestBody ProductOrder request, @PathVariable Long id) {
        try {
            ProductOrder productOrder = productOrderService.editProductOrder(request, id);
            return ResponseEntity.ok(productOrder);
        } catch (IllegalArgumentException | ResponseStatusException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteOrder(@PathVariable Long id) {
        try {
            productOrderService.deleteProductOrder(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException | ResponseStatusException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
