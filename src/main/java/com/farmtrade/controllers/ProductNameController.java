package com.farmtrade.controllers;

import com.farmtrade.dto.ApproveProductNameDto;
import com.farmtrade.entities.ProductName;
import com.farmtrade.services.interfaces.ProductNameService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("productNames")
public class ProductNameController {
    private final ProductNameService productNameService;

    public ProductNameController(ProductNameService productNameService) {
        this.productNameService = productNameService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<ProductName> findAll(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam boolean approved
    ) {
        return productNameService.findPageByApproved(pageable, approved);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public ProductName findOne(@PathVariable Long id) {
        return productNameService.findOne(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ProductName create(@RequestBody ProductName productName) {
        return productNameService.create(productName);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public ProductName update(@PathVariable Long id, @RequestBody ProductName productName) {
        return productNameService.update(id, productName);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productNameService.deleteById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}/approve")
    public ApproveProductNameDto updateApprove(@PathVariable Long id, @RequestBody ApproveProductNameDto approveProductName) {
        return productNameService.updateApproveById(id, approveProductName);
    }
}
