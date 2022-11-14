package com.farmtrade.services.api;

import com.farmtrade.entities.PriceUpdateHistory;

import java.util.Optional;

public interface PriceUpdateHistoryService {
    PriceUpdateHistory save(PriceUpdateHistory priceUpdateHistory);
    Optional<PriceUpdateHistory> getLastUpdateByOrderRequestAndUserId(Long orderRequestId, Long userId);
    void delete(PriceUpdateHistory priceUpdateHistory);
}
