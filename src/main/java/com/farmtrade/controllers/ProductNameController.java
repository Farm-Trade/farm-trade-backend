package com.farmtrade.controllers;

import com.farmtrade.dto.producnames.ApproveProductNameDto;
import com.farmtrade.dto.producnames.ProductNameCreateUpdateDto;
import com.farmtrade.entities.ProductName;
import com.farmtrade.entities.enums.Role;
import com.farmtrade.exceptions.ApiValidationException;
import com.farmtrade.services.api.ProductNameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.farmtrade.constants.SwaggerConstants.SECURITY_SCHEME_NAME;

@RestController
@RequestMapping("api/product-names")
@SecurityRequirement(name = SECURITY_SCHEME_NAME)
@Tag(name = "Product Name Controller", description = "The Product Name API")
public class ProductNameController {
    private final ProductNameService productNameService;

    public ProductNameController(ProductNameService productNameService) {
        this.productNameService = productNameService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    @Operation(summary = "Get product names page")
    public Page<ProductName> findAll(
            @ParameterObject @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return productNameService.findPage(pageable);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    @Operation(summary = "Get product name")
    public ProductName findOne(@PathVariable Long id) {
        return productNameService.findOne(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @Operation(summary = "Create product name")
    public ProductName create(@RequestBody ProductNameCreateUpdateDto productNameDto) {
        return productNameService.create(productNameDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    @Operation(summary = "Update product name")
    public ProductName update(@PathVariable Long id, @RequestBody ProductNameCreateUpdateDto productNameDto) {
        return productNameService.fullyUpdate(id, productNameDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product name")
    public void delete(@PathVariable Long id) {
        productNameService.delete(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}/approve")
    @Operation(summary = "Approve product name")
    public ApproveProductNameDto updateApprove(@PathVariable Long id, @RequestBody ApproveProductNameDto approveProductName) {
        return productNameService.updateApproveById(id, approveProductName);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}/update-request-permission/{role}")
    @Operation(summary = "Update product name permission")
    public ProductName updateRequestPermission(@PathVariable Long id, @PathVariable Role role) throws ApiValidationException {
        return productNameService.updateRequestPermission(id, role);
    }
}
