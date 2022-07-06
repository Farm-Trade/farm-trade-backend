package com.farmtrade.services.impl;

import com.farmtrade.services.interfaces.ProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductServiceImpl productService;

    public ProductServiceImpl(ProductServiceImpl productService) {
        this.productService = productService;
    }

}
