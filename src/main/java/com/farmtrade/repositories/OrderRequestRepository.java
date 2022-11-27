package com.farmtrade.repositories;

import com.farmtrade.entities.OrderRequest;
import com.farmtrade.entities.ProductName;
import com.farmtrade.entities.User;
import com.farmtrade.entities.enums.OrderRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface OrderRequestRepository extends BaseJpaAndSpecificationRepository<OrderRequest, Long> {
    List<OrderRequest> findAllByProductNameInAndStatus(List<ProductName> productName, OrderRequestStatus status);
}
