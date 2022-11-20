package com.farmtrade.filters.builders;

import com.farmtrade.entities.OrderRequest;
import com.farmtrade.filters.FilterType;
import com.farmtrade.filters.SearchCriteria;
import com.farmtrade.filters.abstracts.SpecificationsBuilder;
import com.farmtrade.filters.specifications.OrderRequestSpecification;
import com.farmtrade.filters.specifications.ProductSpecification;

import javax.activation.UnsupportedDataTypeException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class OrderRequestSpecificationsBuilder extends SpecificationsBuilder<OrderRequest, OrderRequestSpecification> {

    public OrderRequestSpecificationsBuilder(
            List<BigDecimal> quantity,
            List<BigDecimal> unitPrice,
            LocalDateTime loadingDate,
            LocalDateTime auctionEndDate,
            Long productName,
            Long owner,
            boolean completed
    ) throws UnsupportedDataTypeException {
        super();
        this.with("quantity", FilterType.BETWEEN, quantity)
                .with("unitPrice", FilterType.BETWEEN, unitPrice)
                .with("loadingDate", FilterType.EQUAL, loadingDate)
                .with("auctionEndDate", FilterType.EQUAL, auctionEndDate)
                .with("productName", FilterType.EQUAL, productName)
                .with("owner", FilterType.EQUAL, owner)
                .with("completed", FilterType.EQUAL, completed);
    }

    @Override
    protected OrderRequestSpecification specificationFactory(SearchCriteria criteria) {
        return new OrderRequestSpecification(criteria);
    }


}
