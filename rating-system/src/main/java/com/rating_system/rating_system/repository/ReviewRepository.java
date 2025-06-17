package com.rating_system.rating_system.repository;

import com.rating_system.rating_system.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    boolean existsByUsernameAndProductId(String username, Long productId);
}