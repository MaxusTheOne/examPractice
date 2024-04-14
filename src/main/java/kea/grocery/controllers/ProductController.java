package kea.grocery.controllers;


import kea.grocery.entities.Product;
import kea.grocery.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Optional<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Product> getProductByName(@PathVariable String name) {
        return ResponseEntity.of(productService.getProductByName(name));
    }

    @PostMapping
    public Product addProduct(@RequestBody Product request) {
        return productService.addProduct(request);
    }

    @PutMapping("/{id}")
    public Product editProduct(@RequestBody Product request, @PathVariable Long id) {
        return productService.editProduct(request,id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }

}
