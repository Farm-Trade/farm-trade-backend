package com.farmtrade.services.impl.api;

import com.farmtrade.dto.producnames.ApproveProductNameDto;
import com.farmtrade.dto.producnames.ProductNameCreateUpdateDto;
import com.farmtrade.entities.ProductName;
import com.farmtrade.entities.enums.Role;
import com.farmtrade.exceptions.ApiValidationException;
import com.farmtrade.repositories.ProductNameRepository;
import com.farmtrade.services.abstracts.BaseCrudService;
import com.farmtrade.services.api.ProductNameService;
import org.springframework.stereotype.Service;

@Service
public class ProductNameServiceImpl extends BaseCrudService<ProductName, Long, ProductNameCreateUpdateDto> implements ProductNameService {
    public ProductNameServiceImpl(ProductNameRepository productNameRepository) {
        super(productNameRepository);
    }

    @Override
    public Class<ProductName> getClassInstance() {
        return ProductName.class;
    }

    @Override
    public ProductName fullyUpdate(Long id, ProductNameCreateUpdateDto productNameDto) {
        ProductName product = super.fullyUpdate(id, productNameDto);
        // TODO After user system added if admit adding approve should be true
        product.setApproved(false);
        return repository.save(product);
    }

    @Override
    public ProductName create(ProductNameCreateUpdateDto productNameDto) {
        final ProductName productName = ProductName.builder()
                .name(productNameDto.getName())
                .type(productNameDto.getType())
                .approved(false)
                .createRequestPermission(Role.RESELLER)
                .build();
        return repository.save(productName);
    }

    @Override
    public ApproveProductNameDto updateApproveById(Long id, ApproveProductNameDto approveProductName) {
        ProductName productName = findOne(id);
        productName.setApproved(approveProductName.isApproved());
        repository.save(productName);
        return ApproveProductNameDto.builder().approved(productName.isApproved()).build();
    }

    @Override
    public ProductName updateRequestPermission(Long id, Role role) throws ApiValidationException {
        ProductName productName = findOne(id);
        if (!productName.isApproved()) {
            throw new ApiValidationException("Назва продукту не підтверджена");
        }

        Role.isCommercialRole(role, true);
        if (role.equals(productName.getCreateRequestPermission())) {
            return productName;
        }
        productName.setCreateRequestPermission(role);
        return repository.save(productName);
    }

}
