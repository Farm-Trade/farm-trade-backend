package com.farmtrade.repositories;

import com.farmtrade.entities.OrderRequest;
import com.farmtrade.entities.PriceUpdateHistory;
import com.farmtrade.entities.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PriceUpdateHistoryRepository extends JpaRepository<PriceUpdateHistory, Long> {
    List<PriceUpdateHistory> findAllByOrderRequestIdAndUpdaterId(Long orderRequestId, Long updaterId);

    Optional<PriceUpdateHistory> findFirstByOrderRequestIdAndUpdaterIdOrderByCreatedAtDesc(Long orderRequestId, Long updaterId);

    @Query(value = "SELECT DISTINCT ON(order_request) * FROM price_update_history WHERE updater = :userId ORDER BY order_request, created_at DESC", nativeQuery = true)
    Set<PriceUpdateHistory> findAllLastUpdatesByUserId(@Param("userId") Long userId);

    @Query( value = "SELECT DISTINCT ON(updater) * FROM price_update_history WHERE order_request = :id ORDER BY updater, created_at DESC", nativeQuery = true)
    Set<PriceUpdateHistory> findTop3ByOrderRequestIdOrderByCreatedAt(@Param("id") Long id);

    @Query(value = "DELETE FROM price_update_history WHERE order_request = :id AND id NOT IN (SELECT DISTINCT ON(updater) id FROM price_update_history WHERE order_request = :id ORDER BY updater, created_at DESC FETCH FIRST 3 ROWS ONLY)", nativeQuery = true)
    @Modifying
    @Transactional
    void deleteAllByOrderRequestExceptTop3(@Param("id") Long Id);

    void deleteAllByOrderRequestIdAndIdNot(Long orderRequestId, Long exceptId);
}
