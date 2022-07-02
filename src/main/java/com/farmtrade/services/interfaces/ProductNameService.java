package com.farmtrade.services.interfaces;

import com.farmtrade.entities.ProductName;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductNameService {
    Page<ProductName> findPageByApproved(Pageable pageable, boolean approved);

    List<ProductName> findAllByApproved(boolean approved);

    ProductName findOne(Long id);

    ProductName update(Long id, ProductName productName);

    ProductName create(ProductName productName);

    void deleteById(Long id);

    boolean updateApproveById(Long id, boolean approve);
}
