package com.farmtrade.controllers;

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
import org.springframework.web.multipart.MultipartFile;

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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Product create(@RequestBody CreateProductDto product) {
        return productService.create(product);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody UpdateProductDto entity) {
        return productService.fullyUpdate(id, entity);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{id}/updateImage")
    public Product updateImage(@PathVariable Long id, @RequestParam("img") MultipartFile img) {
        return productService.updateImage(id, img);
    }
}
