package com.farmtrade.filters.builders;

import com.farmtrade.entities.Product;
import com.farmtrade.entities.ProductName;
import com.farmtrade.entities.enums.ProductType;
import com.farmtrade.entities.enums.Role;
import com.farmtrade.filters.FilterType;
import com.farmtrade.filters.SearchCriteria;
import com.farmtrade.filters.abstracts.SpecificationsBuilder;
import com.farmtrade.filters.specifications.ProductNameSpecification;
import com.farmtrade.filters.specifications.ProductSpecification;

import javax.activation.UnsupportedDataTypeException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public class ProductNameSpecificationsBuilder extends SpecificationsBuilder<ProductName, ProductNameSpecification> {

    public ProductNameSpecificationsBuilder(
            String name,
            ProductType type,
            boolean approved,
            Role createRequestPermission,
            List<Product> products
    ) throws UnsupportedDataTypeException {
        super();
        this.with("name", FilterType.CONTAIN, name)
                .with("type", FilterType.EQUAL, type)
                .with("approved", FilterType.EQUAL, approved)
                .with("createRequestPermission", FilterType.EQUAL, createRequestPermission)
                .with("products", FilterType.EQUAL, products);
    }

    @Override
    protected ProductNameSpecification specificationFactory(SearchCriteria criteria) {
        return new ProductNameSpecification(criteria);
    }


}
