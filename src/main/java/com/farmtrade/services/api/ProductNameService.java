package com.farmtrade.services.api;

import com.farmtrade.dto.producnames.ApproveProductNameDto;
import com.farmtrade.dto.producnames.ProductNameCreateUpdateDto;
import com.farmtrade.entities.ProductName;
import com.farmtrade.entities.enums.Role;
import com.farmtrade.exceptions.ApiValidationException;
import com.farmtrade.services.interfaces.IBaseCrudService;

public interface ProductNameService extends IBaseCrudService<ProductName, Long, ProductNameCreateUpdateDto> {
    ProductName create(ProductNameCreateUpdateDto productNameDto);
    ApproveProductNameDto updateApproveById(Long id, ApproveProductNameDto approveProductName);
    ProductName updateRequestPermission(Long id, Role role) throws ApiValidationException;
}
