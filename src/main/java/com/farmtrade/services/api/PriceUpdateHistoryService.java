package com.farmtrade.services.api;

import com.farmtrade.entities.OrderRequest;
import com.farmtrade.entities.PriceUpdateHistory;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PriceUpdateHistoryService {
    PriceUpdateHistory save(PriceUpdateHistory priceUpdateHistory);
    Optional<PriceUpdateHistory> getLastUpdateByOrderRequestAndUserId(Long orderRequestId, Long userId);
    void delete(PriceUpdateHistory priceUpdateHistory);
    Set<PriceUpdateHistory> findAllLastUpdatesByUserId(Long id);

    List<PriceUpdateHistory> getAllByUserIdAndOrderRequestId(Long userId, Long orderRequestId);

    void deleteAllExceptLastThreeRatesByOrderRequestId(OrderRequest id);

    void deleteAllExcept(Long orderRequestId, Long exceptId);
}
