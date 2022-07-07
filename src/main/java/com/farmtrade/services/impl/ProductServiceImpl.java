package com.farmtrade.services.impl;

import com.farmtrade.entities.Product;
import com.farmtrade.services.interfaces.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductServiceImpl productService;

    public ProductServiceImpl(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @Override
    public Page<Product> findPage() {
        return productService.findPage();
    }
}
