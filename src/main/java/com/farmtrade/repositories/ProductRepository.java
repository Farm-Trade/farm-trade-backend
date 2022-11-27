package com.farmtrade.repositories;

import com.farmtrade.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.awt.print.Pageable;
import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends BaseJpaAndSpecificationRepository<Product, Long> {
    List<Product> findAllByOwnerIdAndProductNameIdAndSizeGreaterThanEqual(Long userId, Long productNameId, BigDecimal size);
}
