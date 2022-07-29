package com.farmtrade.services.api;

import com.farmtrade.dto.product.CreateProductDto;
import com.farmtrade.dto.product.UpdateProductDto;
import com.farmtrade.entities.Product;
import com.farmtrade.entities.User;
import com.farmtrade.services.interfaces.IBaseCrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService extends IBaseCrudService<Product, Long, UpdateProductDto> {
    Product create(CreateProductDto entity, User user);

    Product updateImage(Long id, MultipartFile img);

    Page<Product> findPage(Pageable pageable);

    Page<Product> findPage(Specification<Product> specification, Pageable pageable);

    Product findOne(Long id);

    Product simpleCreate(Product entity);

    Product fullyUpdate(Long id, UpdateProductDto updateDTO);

    void delete(Long id);
}
