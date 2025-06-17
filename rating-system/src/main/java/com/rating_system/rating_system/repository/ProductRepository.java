package com.rating_system.rating_system.repository;

import com.rating_system.rating_system.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // No extra methods needed for now
}