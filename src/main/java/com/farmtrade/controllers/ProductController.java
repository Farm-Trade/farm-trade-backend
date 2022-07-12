package com.farmtrade.controllers;

import com.farmtrade.dto.product.CreateProductDto;
import com.farmtrade.dto.product.UpdateProductDto;
import com.farmtrade.entities.Product;
import com.farmtrade.entities.User;
import com.farmtrade.filters.builders.ProductSpecificationsBuilder;
import com.farmtrade.services.api.ProductService;
import com.farmtrade.services.interfaces.AuthService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.UnsupportedDataTypeException;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {
    private final ProductService productService;
    private final AuthService authService;

    public ProductController(ProductService productService, AuthService authService) {
        this.productService = productService;
        this.authService = authService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<Product> findPage(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(value = "quantity", required = false) List<BigDecimal> quantity,
            @RequestParam(value = "reservedQuantity", required = false) List<BigDecimal> reservedQuantity,
            @RequestParam(value = "size", required = false) List<BigDecimal> size,
            @RequestParam(value = "productName", required = false) Long productName,
            @RequestParam(value = "owner", required = false) Long owner
    ) throws UnsupportedDataTypeException {
        Specification<Product> specification = new ProductSpecificationsBuilder(
                quantity,
                reservedQuantity,
                size,
                productName,
                owner
        ).build();
        return productService.findPage(specification, pageable);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Product findOne(@PathVariable Long id) {
        return productService.findOne(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Product create(@RequestBody CreateProductDto product) {
        User user = authService.getUserFromContext();
        return productService.create(product, user);
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
