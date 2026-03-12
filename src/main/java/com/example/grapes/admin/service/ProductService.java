package com.example.grapes.admin.service;

import java.util.List;
import com.example.grapes.admin.model.Product;

public interface ProductService {

    List<Product> getAllProducts();

    Product saveProduct(Product product);

    Product getProductById(Long id);

    void deleteProduct(Long id);

}