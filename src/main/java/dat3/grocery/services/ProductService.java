package dat3.grocery.services;

import dat3.grocery.reposities.ProductRepository;
import dat3.grocery.entities.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final dat3.grocery.reposities.ProductRepository ProductRepository;
    private final ProductRepository productRepository;

    public ProductService(ProductRepository ProductRepository, ProductRepository productRepository){
        this.ProductRepository = ProductRepository;
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts(){
        List<Product> products = ProductRepository.findAll();
        return products;
    }

    public Optional<Product> getProductByName(String name){
        return ProductRepository.findByName(name);
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }


    public Product addProduct(Product request){
        if(request.getId()!= null){
            throw new IllegalArgumentException("You cannot provide the id for a new product");
        }

        Product newProduct = new Product();
        updateProduct(newProduct, request);
        ProductRepository.save(newProduct);
        return newProduct;

    }

    public Product editProduct(Product request, Long id) {
        Product productToEdit = productRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        updateProduct(productToEdit, request);
        productRepository.save(productToEdit);
        return productToEdit;
    }

    private void updateProduct(Product original, Product request){
        original.setName(request.getName());
        original.setPrice(request.getPrice());
        original.setWeightInGrams(request.getWeightInGrams());
    }

    public ResponseEntity deleteProduct(Long id){
        Product productToDelete = ProductRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Product not found"));
        ProductRepository.delete(productToDelete);
        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }

}
