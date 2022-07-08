package com.farmtrade.controllers;

import com.farmtrade.abstracts.BaseCrudController;
import com.farmtrade.dto.product.CreateProductDto;
import com.farmtrade.dto.product.UpdateProductDto;
import com.farmtrade.entities.Product;
import com.farmtrade.services.api.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("products")
public class ProductController extends BaseCrudController<Product, Long, UpdateProductDto, ProductService> {
    public ProductController(ProductService productService) {
        super(productService);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Product create(@RequestBody CreateProductDto product) {
        return service.create(product);
    }

//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping("/{id}/updateImage")
//    public Product updateImage() {
//
//    }
 }
