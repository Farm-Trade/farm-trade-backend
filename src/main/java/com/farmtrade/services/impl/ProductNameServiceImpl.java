package com.farmtrade.services.impl;

import com.farmtrade.entities.ProductName;
import com.farmtrade.exceptions.EntityNotFoundException;
import com.farmtrade.repositories.ProductNameRepository;
import com.farmtrade.services.interfaces.ProductNameService;
import com.fasterxml.jackson.databind.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductNameServiceImpl implements ProductNameService {
    private final ProductNameRepository productNameRepository;

    public ProductNameServiceImpl(ProductNameRepository productNameRepository) {
        this.productNameRepository = productNameRepository;
    }

    @Override
    public Page<ProductName> findPageByApproved(Pageable pageable, boolean approved) {
        return productNameRepository.findAllByApproved(approved, pageable);
    }

    @Override
    public List<ProductName> findAllByApproved(boolean approved) {
        return productNameRepository.findAllByApproved(approved);
    }

    @Override
    public ProductName findOne(Long id) {
        return productNameRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ProductName.class, id.toString()));
    }

    @Override
    public ProductName update(Long id, ProductName productName) {
        ProductName productNameFromDb = findOne(id);
        BeanUtils.copyProperties(productName, productNameFromDb, "id", "approved");
        return productNameRepository.save(productNameFromDb);
    }

    @Override
    public ProductName create(ProductName productName) {
        productName.setApproved(false);
        return productNameRepository.save(productName);
    }

    @Override
    public void deleteById(Long id) {
        ProductName productName = findOne(id);
        productNameRepository.delete(productName);
    }

    @Override
    public boolean updateApproveById(Long id, boolean approve) {
        ProductName productName = findOne(id);
        productName.setApproved(approve);
        productNameRepository.save(productName);
        return approve;
    }
}
