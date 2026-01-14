package com.example.backendPracticaDos.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.backendPracticaDos.model.Product;

@Service
public class ProductService {
	
	private final List<Product> products = new ArrayList<>();

    public ProductService() {
        // Productos de ejemplo
        products.add(new Product(1L, "Laptop", "Laptop gamer", 15000.0));
        products.add(new Product(2L, "Mouse", "Mouse inalámbrico", 350.0));
        products.add(new Product(3L, "Teclado", "Teclado mecánico", 1200.0));
    }

    public List<Product> getAllProducts() {
        return products;
    }

    public Product getProductById(Long id) {
        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

}
