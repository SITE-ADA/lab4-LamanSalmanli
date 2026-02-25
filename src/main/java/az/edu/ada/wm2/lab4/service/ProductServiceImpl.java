package az.edu.ada.wm2.lab4.service;

import az.edu.ada.wm2.lab4.model.Product;
import az.edu.ada.wm2.lab4.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(Product product) {
        if(product.getId() == null){
            product.setId(UUID.randomUUID());
        }
        return  productRepository.save(product);
    }

    public Product getProductByID(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RunTimeException("Product not found with id: " + id ));
    }

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    public Product updateProduct(Product product) {
        if (!productRepository.existById(product.getId())) {
            throw RunTimeException("Product not found with id: " + id);
        }
        return productRepository.save(product);
    }

    public void deleteProduct(UUID id) {
        if (!productRepository.existById(id)) {
            throw RunTimeException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> getProductsExpiringBefore(LocalDate date) {
        List<Product> result = new ArrayList<>();
        List<Product> allProducts = productRepository.findAll();

        for (Product product : allProducts) {
            if (product.getExpirationDate().isBefore(date)) {
                result.add(product);
            }
        }

        return result;
    }

    @Override
    public List<Product> getProductsByPriceRange(BigDecimal min, BigDecimal max) {
        List<Product> result = new ArrayList<>();
        List<Product> allProducts = productRepository.findAll();

        for (Product product : allProducts) {
            BigDecimal price = product.getPrice();

            if (price.compareTo(min) >= 0 && price.compareTo(max) <= 0) {
                result.add(product);
            }
        }
        return result;
    }

}