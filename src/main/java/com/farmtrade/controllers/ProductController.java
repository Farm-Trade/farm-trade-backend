package com.farmtrade.controllers;

import com.farmtrade.dto.ImageDto;
import com.farmtrade.dto.product.CreateProductDto;
import com.farmtrade.dto.product.UpdateProductDto;
import com.farmtrade.entities.Product;
import com.farmtrade.entities.User;
import com.farmtrade.filters.builders.ProductSpecificationsBuilder;
import com.farmtrade.services.api.ProductService;
import com.farmtrade.services.interfaces.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.ClientRequest;

import javax.activation.UnsupportedDataTypeException;
import java.math.BigDecimal;
import java.util.List;

import static com.farmtrade.constants.SwaggerConstants.SECURITY_SCHEME_NAME;

@RestController
@RequestMapping("api/products")
@SecurityRequirement(name = SECURITY_SCHEME_NAME)
@Tag(name = "Product Controller", description = "The Product API")
public class ProductController {
    private final ProductService productService;
    private final AuthService authService;

    public ProductController(ProductService productService, AuthService authService) {
        this.productService = productService;
        this.authService = authService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    @Operation(summary = "Get products page")
    public Page<Product> findPage(
            @ParameterObject @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false) List<BigDecimal> quantity,
            @RequestParam(required = false) List<BigDecimal> reservedQuantity,
            @RequestParam(required = false) List<BigDecimal> size,
            @RequestParam(required = false) Long productName,
            @RequestParam(required = false) Long owner
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
    @Operation(summary = "Get product")
    public Product findOne(@PathVariable Long id) {
        return productService.findOne(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @Operation(summary = "Create product")
    public Product create(@RequestBody CreateProductDto product) {
        User user = authService.getUserFromContext();
        return productService.create(product, user);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    @Operation(summary = "Update product")
    public Product update(@PathVariable Long id, @RequestBody UpdateProductDto entity) {
        return productService.fullyUpdate(id, entity);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/{id}/update-image", method = RequestMethod.POST, produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Update product's image",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "ImageDto body.",
                    content = @Content(
                            mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            schema = @Schema(implementation = ImageDto.class)
                    ),

                    required = true
            )
    )
    public Product updateImage(@PathVariable Long id, @RequestParam("img") MultipartFile img) {
        return productService.updateImage(id, img);
    }
}
