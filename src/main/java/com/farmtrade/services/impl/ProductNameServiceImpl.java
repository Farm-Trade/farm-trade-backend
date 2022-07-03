package com.farmtrade.services.impl;

import com.farmtrade.dto.ApproveProductNameDto;
import com.farmtrade.dto.ProductNameCreateUpdateDto;
import com.farmtrade.entities.ProductName;
import com.farmtrade.entities.enums.Role;
import com.farmtrade.exceptions.ApiValidationException;
import com.farmtrade.exceptions.EntityNotFoundException;
import com.farmtrade.repositories.ProductNameRepository;
import com.farmtrade.services.interfaces.ProductNameService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class ProductNameServiceImpl implements ProductNameService {
    private final ProductNameRepository productNameRepository;

    public ProductNameServiceImpl(ProductNameRepository productNameRepository) {
        this.productNameRepository = productNameRepository;
    }

    @Override
    public Page<ProductName> findPage(Pageable pageable) {
        return productNameRepository.findAll(pageable);
    }

    @Override
    public ProductName findOne(Long id) {
        return productNameRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ProductName.class, id.toString()));
    }

    @Override
    public ProductName update(Long id, @RequestBody ProductNameCreateUpdateDto productNameDto) {
        ProductName productNameFromDb = findOne(id);
        BeanUtils.copyProperties(productNameDto, productNameFromDb);
        // TODO After user system added if admit adding approve should be true
        productNameFromDb.setApproved(false);
        return productNameRepository.save(productNameFromDb);
    }

    @Override
    public ProductName create(ProductNameCreateUpdateDto productNameDto) {
        final ProductName productName = ProductName.builder()
                .name(productNameDto.getName())
                .type(productNameDto.getType())
                .approved(false)
                .createRequestPermission(Role.RESELLER)
                .build();
        return productNameRepository.save(productName);
    }

    @Override
    public void deleteById(Long id) {
        ProductName productName = findOne(id);
        productNameRepository.delete(productName);
    }

    @Override
    public ApproveProductNameDto updateApproveById(Long id, ApproveProductNameDto approveProductName) {
        ProductName productName = findOne(id);
        productName.setApproved(approveProductName.isApproved());
        productNameRepository.save(productName);
        return ApproveProductNameDto.builder().approved(productName.isApproved()).build();
    }

    @Override
    public ProductName updateRequestPermission(Long id, Role role) throws ApiValidationException {
        ProductName productName = findOne(id);
        if (!productName.isApproved()) {
            throw new ApiValidationException("ProductName is not approved, createRequestPermission cannot be updated");
        }

        Role.isCommercialRole(role, true);
        if (role.equals(productName.getCreateRequestPermission())) {
            return productName;
        }
        productName.setCreateRequestPermission(role);
        return productNameRepository.save(productName);
    }

}
