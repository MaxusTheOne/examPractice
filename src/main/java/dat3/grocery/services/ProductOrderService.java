package dat3.grocery.services;

import dat3.grocery.entities.ProductOrder;
import dat3.grocery.reposities.ProductRepository;
import dat3.grocery.entities.Product;
import dat3.grocery.reposities.ProductOrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ProductOrderService {
    private final ProductOrderRepository productOrderRepository;

    private final ProductService productService;


    public ProductOrderService(ProductOrderRepository productOrderRepository, ProductRepository productRepository, ProductService productService) {
        this.productOrderRepository = productOrderRepository;

        this.productService = productService;
    }

    public List<ProductOrder> getAllProductOrders(){
        List<ProductOrder> productOrders = productOrderRepository.findAll();
        return productOrders;
    }

    public Optional<ProductOrder> getProductOrderById(Long id) {
        return productOrderRepository.findById(id);

    }

    public List<ProductOrder> getProductOrdersByIds(List<Long> ids) {
        return productOrderRepository.findAllById(ids);
    }


    public ProductOrder createProductOrder(Long productId, int quantity) {
        Product product = productService.getProductById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }

        ProductOrder productOrder = new ProductOrder();
        productOrder.setProduct(product);
        productOrder.setQuantity(quantity);
        return productOrderRepository.save(productOrder);
    }

    public ProductOrder editProductOrder(ProductOrder request, Long id) {
        ProductOrder productOrderToEdit = productOrderRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Order not found"));
        updateProductOrder(productOrderToEdit, request);
        productOrderRepository.save(productOrderToEdit);
        return productOrderToEdit;
    }

    private void updateProductOrder(ProductOrder original, ProductOrder request){
        original.setQuantity(request.getQuantity());
    }

    public void deleteProductOrder(Long id) {
        ProductOrder productOrder = productOrderRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Order not found"));
        productOrderRepository.delete(productOrder);
    }






}
