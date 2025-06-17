package com.rating_system.rating_system.controller;

import com.rating_system.rating_system.ProductSummaryDTO;
import com.rating_system.rating_system.model.Product;
import com.rating_system.rating_system.model.Review;
import com.rating_system.rating_system.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    // GET: List all products
    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // POST: Add a new product
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<?> getProductDetails(@PathVariable Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }

        Product product = optionalProduct.get();

        // Calculate average rating
        List<Review> reviews = product.getReviews();
        double averageRating = 0;
        if (!reviews.isEmpty()) {
            averageRating = reviews.stream()
                    .mapToInt(Review::getRating)
                    .average()
                    .orElse(0.0);
        }

        // Prepare response
        Map<String, Object> response = new HashMap<>();
        response.put("id", product.getId());
        response.put("name", product.getName());
        response.put("description", product.getDescription());
        response.put("averageRating", averageRating);
        response.put("totalReviews", reviews.size());
        response.put("reviews", reviews);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/summary")
    public List<ProductSummaryDTO> getAllProductSummaries() {
        List<Product> products = productRepository.findAll();

        return products.stream().map(product -> {
            List<Review> reviews = product.getReviews();
            double avgRating = reviews.isEmpty() ? 0.0 :
                    reviews.stream().mapToInt(Review::getRating).average().orElse(0.0);
            return new ProductSummaryDTO(
                    product.getId(),
                    product.getName(),
                    product.getDescription(),
                    avgRating
            );
        }).collect(Collectors.toList());
    }

}