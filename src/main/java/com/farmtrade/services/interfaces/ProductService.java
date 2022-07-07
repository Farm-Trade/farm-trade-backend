package com.farmtrade.services.interfaces;

import com.farmtrade.entities.Product;
import org.springframework.data.domain.Page;

public interface ProductService {
    Page<Product> findPage();
}
