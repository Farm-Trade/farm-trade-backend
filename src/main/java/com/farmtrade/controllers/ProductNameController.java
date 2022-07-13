package com.farmtrade.controllers;

import com.farmtrade.dto.producnames.ApproveProductNameDto;
import com.farmtrade.dto.producnames.ProductNameCreateUpdateDto;
import com.farmtrade.entities.ProductName;
import com.farmtrade.entities.enums.Role;
import com.farmtrade.exceptions.ApiValidationException;
import com.farmtrade.services.api.ProductNameService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/product-names")
public class ProductNameController {
    private final ProductNameService productNameService;

    public ProductNameController(ProductNameService productNameService) {
        this.productNameService = productNameService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<ProductName> findAll(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return productNameService.findPage(pageable);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public ProductName findOne(@PathVariable Long id) {
        return productNameService.findOne(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ProductName create(@RequestBody ProductNameCreateUpdateDto productNameDto) {
        return productNameService.create(productNameDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public ProductName update(@PathVariable Long id, @RequestBody ProductNameCreateUpdateDto productNameDto) {
        return productNameService.fullyUpdate(id, productNameDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productNameService.delete(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}/approve")
    public ApproveProductNameDto updateApprove(@PathVariable Long id, @RequestBody ApproveProductNameDto approveProductName) {
        return productNameService.updateApproveById(id, approveProductName);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}/update-request-permission/{role}")
    public ProductName updateRequestPermission(@PathVariable Long id, @PathVariable Role role) throws ApiValidationException {
        return productNameService.updateRequestPermission(id, role);
    }
}
