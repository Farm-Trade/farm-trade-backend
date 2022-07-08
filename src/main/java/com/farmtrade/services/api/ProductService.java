package com.farmtrade.services.api;

import com.farmtrade.dto.product.CreateProductDto;
import com.farmtrade.dto.product.UpdateProductDto;
import com.farmtrade.entities.Product;
import com.farmtrade.entities.ProductName;
import com.farmtrade.repositories.ProductRepository;
import com.farmtrade.abstracts.BaseCrudService;
import com.farmtrade.services.interfaces.ProductNameService;
import org.springframework.stereotype.Service;

@Service
public class ProductService extends BaseCrudService<Product, Long, UpdateProductDto> {
    private final ProductNameService productNameService;
    public ProductService(ProductRepository productRepository, ProductNameService productNameService) {
        super(productRepository);
        this.productNameService = productNameService;
    }

    @Override
    public Class<Product> getClassInstance() {
        return Product.class;
    }

    public Product create(CreateProductDto entity) {
        ProductName productName = productNameService.findOne(entity.getProductName());
        Product product = Product.builder()
                // TODO Add after user system added .owner(null)
                .quantity(entity.getQuantity())
                .size(entity.getSize())
                .productName(productName)
                .build();
        return repository.save(product);
    }


}
