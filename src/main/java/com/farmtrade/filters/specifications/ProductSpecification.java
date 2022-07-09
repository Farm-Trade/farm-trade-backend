package com.farmtrade.filters.specifications;

import com.farmtrade.entities.Product;
import com.farmtrade.filters.SearchCriteria;
import com.farmtrade.filters.abstracts.BaseSpecification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;

public class ProductSpecification extends BaseSpecification<Product> {
    public ProductSpecification(SearchCriteria criteria) {
        super(criteria);
    }

    @Override
    protected Predicate buildEqual(Root<Product> root, CriteriaBuilder builder) {
        Long id = Long.valueOf((String) criteria.getValue());
        return builder.equal(root.get(criteria.getKey()),  id);
    }

    @Override
    protected Predicate buildBetween(Root<Product> root, CriteriaBuilder builder, String from, String to) {
        return builder.between(
                root.get(criteria.getKey()),
                BigDecimal.valueOf(Long.parseLong(from)),
                BigDecimal.valueOf(Long.parseLong(to))
        );
    }

    @Override
    protected Predicate buildGreaterThanOrEqualTo(Root<Product> root, CriteriaBuilder builder, String from) {
        return builder.greaterThanOrEqualTo(root.get(criteria.getKey()),  BigDecimal.valueOf(Long.parseLong(from)));
    }

    @Override
    protected Predicate buildLessThanOrEqualTo(Root<Product> root, CriteriaBuilder builder, String to) {
        return builder.lessThanOrEqualTo(root.get(criteria.getKey()),  BigDecimal.valueOf(Long.parseLong(to)));
    }
}
