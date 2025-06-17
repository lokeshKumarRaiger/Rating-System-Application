package com.rating_system.rating_system.controller;


import com.rating_system.rating_system.model.Product;
import com.rating_system.rating_system.model.Review;
import com.rating_system.rating_system.repository.ProductRepository;
import com.rating_system.rating_system.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductRepository productRepository;

    @PostMapping
    public String addReview(@RequestBody Review review) {
        Long productId = review.getProduct().getId();
        String username = review.getUsername();

        if (!productRepository.existsById(productId)) {
            return "Product does not exist.";
        }

        if (reviewRepository.existsByUsernameAndProductId(username, productId)) {
            return "User has already reviewed this product.";
        }

        Product product = productRepository.findById(productId).get();
        review.setProduct(product);

        reviewRepository.save(review);
        return "Review submitted successfully!";
    }
}
