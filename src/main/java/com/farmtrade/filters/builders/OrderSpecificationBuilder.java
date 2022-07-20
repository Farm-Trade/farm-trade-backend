package com.farmtrade.filters.builders;

import com.farmtrade.entities.OrderRequest;
import com.farmtrade.filters.FilterType;
import com.farmtrade.filters.SearchCriteria;
import com.farmtrade.filters.abstracts.SpecificationsBuilder;
import com.farmtrade.filters.specifications.OrderSpecification;

import javax.activation.UnsupportedDataTypeException;
import java.math.BigDecimal;
import java.util.List;

public class OrderSpecificationBuilder extends SpecificationsBuilder<OrderRequest, OrderSpecification> {
    public OrderSpecificationBuilder(
            List<BigDecimal> quantity,
            List<BigDecimal> unitPrice,
            List<BigDecimal> size,
            Long productName
    ) throws UnsupportedDataTypeException {
        super();
        this.with("quantity", FilterType.BETWEEN, quantity)
                .with("unitPrice", FilterType.BETWEEN, unitPrice)
                .with("size", FilterType.BETWEEN, size)
                .with("productName", FilterType.EQUAL, productName);

    }

    @Override
    protected OrderSpecification specificationFactory(SearchCriteria criteria) {
        return null;
    }
}
