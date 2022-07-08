package com.farmtrade.services.api;

import com.farmtrade.entities.Product;
import com.farmtrade.repositories.ProductRepository;
import com.farmtrade.services.abstracts.BaseCrudService;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class ProductService extends BaseCrudService<Product, Long, Product> {
    public ProductService(ProductRepository productRepository) {
        super(productRepository, new HashSet<>());
    }

    @Override
    public Class<Product> getClassInstance() {
        return Product.class;
    }
}
