package com.farmtrade.filters.builders;

import com.farmtrade.entities.Product;
import com.farmtrade.filters.SearchCriteria;
import com.farmtrade.filters.FilterType;
import com.farmtrade.filters.abstracts.SpecificationsBuilder;
import com.farmtrade.filters.specifications.ProductSpecification;

import javax.activation.UnsupportedDataTypeException;
import java.math.BigDecimal;
import java.util.List;

public class ProductSpecificationsBuilder extends SpecificationsBuilder<Product, ProductSpecification> {

    public ProductSpecificationsBuilder(
            List<BigDecimal> quantity,
            List<BigDecimal> reservedQuantity,
            List<BigDecimal> size,
            Long productName,
            Long owner
    ) throws UnsupportedDataTypeException {
        super();
        this.with("quantity", FilterType.BETWEEN, quantity)
                .with("reservedQuantity", FilterType.BETWEEN, reservedQuantity)
                .with("size", FilterType.BETWEEN, size)
                .with("reservedQuantity", FilterType.BETWEEN, reservedQuantity)
                .with("productName", FilterType.EQUAL, productName)
                .with("owner", FilterType.EQUAL, owner);
    }

    @Override
    protected ProductSpecification specificationFactory(SearchCriteria criteria) {
        return new ProductSpecification(criteria);
    }


}
