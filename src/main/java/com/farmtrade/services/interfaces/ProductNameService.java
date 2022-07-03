package com.farmtrade.services.interfaces;

import com.farmtrade.dto.ApproveProductNameDto;
import com.farmtrade.dto.ProductNameCreateUpdateDto;
import com.farmtrade.entities.ProductName;
import com.farmtrade.entities.enums.Role;
import com.farmtrade.exceptions.ApiValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;

public interface ProductNameService {
    Page<ProductName> findPage(Pageable pageable);

    ProductName findOne(Long id);

    ProductName update(Long id, @RequestBody ProductNameCreateUpdateDto productNameDto);

    ProductName create(ProductNameCreateUpdateDto productNameDto);

    void deleteById(Long id);

    ApproveProductNameDto updateApproveById(Long id, ApproveProductNameDto approveProductName);

    ProductName updateRequestPermission(Long id, Role role) throws ApiValidationException;
}
