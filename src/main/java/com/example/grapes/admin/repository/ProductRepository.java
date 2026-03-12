package com.example.grapes.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.grapes.admin.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}