package com.farmtrade.filters.specifications;

import com.farmtrade.entities.OrderRequest;
import com.farmtrade.filters.SearchCriteria;
import com.farmtrade.filters.abstracts.BaseSpecification;

public class OrderRequestSpecification extends BaseSpecification<OrderRequest> {
    public OrderRequestSpecification(SearchCriteria criteria) {
        super(criteria);
    }
}
