package com.farmtrade.filters.specifications;

import com.farmtrade.entities.ProductName;
import com.farmtrade.filters.SearchCriteria;
import com.farmtrade.filters.abstracts.BaseSpecification;

public class ProductNameSpecification extends BaseSpecification<ProductName> {
    public ProductNameSpecification(SearchCriteria criteria) {
        super(criteria);
    }
}
