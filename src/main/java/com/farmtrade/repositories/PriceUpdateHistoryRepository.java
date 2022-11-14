package com.farmtrade.repositories;

import com.farmtrade.entities.PriceUpdateHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PriceUpdateHistoryRepository extends JpaRepository<PriceUpdateHistory, Long> {
    List<PriceUpdateHistory> findAllByOrderRequestIdAndUpdaterId(Long orderRequestId, Long updaterId);
    List<PriceUpdateHistory> findAllByOrderRequestId(Long orderRequestId);
}
