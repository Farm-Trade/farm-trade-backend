package com.farmtrade.repositories;

import com.farmtrade.entities.OrderRequest;
import com.farmtrade.entities.ProductName;
import com.farmtrade.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface OrderRequestRepository extends BaseJpaAndSpecificationRepository<OrderRequest, Long> {
    List<OrderRequest> findAllByProductNameIn(List<ProductName> productName);
}
