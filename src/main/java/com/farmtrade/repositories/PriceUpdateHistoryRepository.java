package com.farmtrade.repositories;

import com.farmtrade.entities.OrderRequest;
import com.farmtrade.entities.PriceUpdateHistory;
import com.farmtrade.entities.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PriceUpdateHistoryRepository extends JpaRepository<PriceUpdateHistory, Long> {
    List<PriceUpdateHistory> findAllByOrderRequestIdAndUpdaterId(Long orderRequestId, Long updaterId);

    Optional<PriceUpdateHistory> findFirstByOrderRequestIdAndUpdaterIdOrderByCreatedAtDesc(Long orderRequestId, Long updaterId);

    @Query(value = "SELECT DISTINCT ON(order_request) * FROM price_update_history WHERE updater = :userId ORDER BY order_request, created_at DESC", nativeQuery = true)
    Set<PriceUpdateHistory> findAllLastUpdatesByUserId(@Param("userId") Long userId);
}
