package com.farmtrade.filters.abstracts;

import com.farmtrade.filters.SearchCriteria;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

@AllArgsConstructor
public abstract class BaseSpecification<T> implements Specification<T> {
    protected SearchCriteria criteria;

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (criteria.getValue().equals("")) {
            return null;
        }

        switch (criteria.getType()) {
            case EQUAL -> {
                return buildEqual(root, builder);
            }
            case CONTAIN -> {
                return builder.like(root.get(criteria.getKey()), "%" + criteria.getValue() + "%");
            }
            case BETWEEN -> {
                String[] range = ((String) criteria.getValue()).split(",");
                String from = range.length == 1 && !range[0].equals("null") ? range[0] : null;
                String to = range.length == 2 && !range[1].equals("null") ? range[1] : null;

                if (from != null && to != null) {
                    return buildBetween(root, builder, from, to);
                } else if (from != null) {
                    return buildGreaterThanOrEqualTo(root, builder, from);
                } else if (to != null) {
                    return buildLessThanOrEqualTo(root, builder, to);
                }
                return null;
            }
            default -> {
                return null;
            }
        }
    }

    protected Predicate buildEqual(Root<T> root, CriteriaBuilder builder) {
        return builder.equal(root.get(criteria.getKey()),  criteria.getValue());
    }

    protected Predicate buildBetween(Root<T> root, CriteriaBuilder builder, String from, String to) {
        return builder.between(root.get(criteria.getKey()),  from, to);
    }

    protected Predicate buildGreaterThanOrEqualTo(Root<T> root, CriteriaBuilder builder, String from) {
        return builder.greaterThanOrEqualTo(root.get(criteria.getKey()),  from);
    }

    protected Predicate buildLessThanOrEqualTo(Root<T> root, CriteriaBuilder builder, String to) {
        return builder.lessThanOrEqualTo(root.get(criteria.getKey()),  to);
    }
}
