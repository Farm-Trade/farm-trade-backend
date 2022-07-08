package com.farmtrade.controllers;

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
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<Product> findPage(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return productService.findPage(pageable);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Product findOne(@PathVariable Long id) {
        return productService.findOne(id);
    }

//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping
//    public Product create(@RequestBody Product product) {
//
//    }
//
//    @ResponseStatus(HttpStatus.OK)
//    @PatchMapping("/{id}")
//    public Product update(@PathVariable Long id, @RequestBody Product product) {
//
//    }
//
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @DeleteMapping("/{id}")
//    public void delete(@PathVariable Long id) {
//
//    }
//
//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping("/{id}/updateImage")
//    public Product updateImage() {
//
//    }
 }
