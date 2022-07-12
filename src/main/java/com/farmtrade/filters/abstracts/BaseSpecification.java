package com.farmtrade.filters.abstracts;

import com.farmtrade.filters.SearchCriteria;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
public abstract class BaseSpecification<T> implements Specification<T> {
    protected SearchCriteria criteria;

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        Object value = criteria.getValue();
        if (value == null || (value.getClass().equals(String.class)  && value.equals(""))) {
            return null;
        }

        Path<String> key = root.get(criteria.getKey());
        switch (criteria.getType()) {
            case EQUAL -> {
                return builder.equal(key,  criteria.getValue());
            }
            case CONTAIN -> {
                return builder.like(key, "%" + criteria.getValue() + "%");
            }
            case BETWEEN -> {
                return buildBetween(root, builder);
            }
            default -> {
                return null;
            }
        }
    }

    protected Predicate buildBetween(Root<T> root, CriteriaBuilder builder) {
        return buildBigDecimalBetween(root, builder);
    };

    protected Predicate buildBigDecimalBetween(Root<T> root, CriteriaBuilder builder) {
        List<BigDecimal> range = (List<BigDecimal>) criteria.getValue();
        BigDecimal from = range.size() == 1 ? range.get(0) : null;
        BigDecimal to = range.size() == 2 ? range.get(1) : null;

        if (from != null && to != null) {
            return builder.between(root.get(criteria.getKey()), from, to);
        } else if (from != null) {
            return builder.greaterThanOrEqualTo(root.get(criteria.getKey()), from);
        } else if (to != null) {
            return builder.lessThanOrEqualTo(root.get(criteria.getKey()), to);
        }
        return null;
    }
}
