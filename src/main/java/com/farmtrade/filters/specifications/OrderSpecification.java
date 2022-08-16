package com.farmtrade.filters.specifications;

import com.farmtrade.entities.OrderRequest;
import com.farmtrade.filters.SearchCriteria;
import com.farmtrade.filters.abstracts.BaseSpecification;

public class OrderSpecification extends BaseSpecification<OrderRequest> {
    public OrderSpecification(SearchCriteria criteria) {
        super(criteria);
    }
}
