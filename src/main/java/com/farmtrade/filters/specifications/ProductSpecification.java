package com.farmtrade.filters.specifications;

import com.farmtrade.entities.Product;
import com.farmtrade.filters.SearchCriteria;
import com.farmtrade.filters.abstracts.BaseSpecification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;

public class ProductSpecification extends BaseSpecification<Product> {
    public ProductSpecification(SearchCriteria criteria) {
        super(criteria);
    }
}
