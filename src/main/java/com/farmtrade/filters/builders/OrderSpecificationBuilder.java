package com.farmtrade.filters.builders;

import com.farmtrade.entities.OrderRequest;
import com.farmtrade.filters.FilterType;
import com.farmtrade.filters.SearchCriteria;
import com.farmtrade.filters.abstracts.SpecificationsBuilder;
import com.farmtrade.filters.specifications.OrderSpecification;

import javax.activation.UnsupportedDataTypeException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class OrderSpecificationBuilder extends SpecificationsBuilder<OrderRequest, OrderSpecification> {
    public OrderSpecificationBuilder(
            List<BigDecimal> quantity,
            List<BigDecimal> unitPrice,
            List<BigDecimal> size,
            Long productName,
            Timestamp loadingDate,
            Timestamp auctionEndDate,
            Long owner,
            Long batchNumber,
            boolean completed
    ) throws UnsupportedDataTypeException {
        super();
        this.with("quantity", FilterType.BETWEEN, quantity)
                .with("unitPrice", FilterType.BETWEEN, unitPrice)
                .with("size", FilterType.BETWEEN, size)
                .with("productName", FilterType.EQUAL, productName)
                .with("loadingDate", FilterType.EQUAL, loadingDate)
                .with("auctionEndDate", FilterType.EQUAL, auctionEndDate)
                .with("owner", FilterType.EQUAL, owner)
                .with("batchNumber", FilterType.EQUAL, batchNumber)
                .with("completed", FilterType.EQUAL, completed);

    }

    @Override
    protected OrderSpecification specificationFactory(SearchCriteria criteria) {
        return new OrderSpecification(criteria);
    }
}
