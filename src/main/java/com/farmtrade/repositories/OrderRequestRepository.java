package com.farmtrade.repositories;

import com.farmtrade.entities.OrderRequest;
import com.farmtrade.entities.PriceUpdateHistory;
import com.farmtrade.entities.ProductName;
import com.farmtrade.entities.User;
import com.farmtrade.entities.enums.OrderRequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface OrderRequestRepository extends BaseJpaAndSpecificationRepository<OrderRequest, Long> {
    List<OrderRequest> findAllByProductNameInAndStatus(List<ProductName> productName, OrderRequestStatus status);
    Page<OrderRequest> findAllByPriceUpdateHistoryIn(Pageable pageable, Set<PriceUpdateHistory> priceUpdateHistories);
    List<OrderRequest> findAllByStatusNotAndAuctionEndDateIsLessThanEqual(OrderRequestStatus status, LocalDateTime date);
    @Query(value = "UPDATE OrderRequest SET status = 'COMPLETED' WHERE id = :id")
    @Modifying
    void completeById(@Param("id") Long id);
}
