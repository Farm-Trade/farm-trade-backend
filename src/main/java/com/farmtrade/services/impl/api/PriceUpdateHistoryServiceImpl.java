package com.farmtrade.services.impl.api;

import com.farmtrade.entities.PriceUpdateHistory;
import com.farmtrade.entities.User;
import com.farmtrade.repositories.PriceUpdateHistoryRepository;
import com.farmtrade.services.api.PriceUpdateHistoryService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class PriceUpdateHistoryServiceImpl implements PriceUpdateHistoryService {
    private final PriceUpdateHistoryRepository priceUpdateHistoryRepository;

    public PriceUpdateHistoryServiceImpl(PriceUpdateHistoryRepository priceUpdateHistoryRepository) {
        this.priceUpdateHistoryRepository = priceUpdateHistoryRepository;
    }

    @Override
    public PriceUpdateHistory save(PriceUpdateHistory priceUpdateHistory) {
        return priceUpdateHistoryRepository.save(priceUpdateHistory);
    }

    @Override
    public Optional<PriceUpdateHistory> getLastUpdateByOrderRequestAndUserId(Long orderRequestId, Long userId) {
        List<PriceUpdateHistory> userUpdates = priceUpdateHistoryRepository.findAllByOrderRequestIdAndUpdaterId(
                orderRequestId,
                userId
        );
        return userUpdates.stream().max(Comparator.comparing(PriceUpdateHistory::getCreatedAt));
    }

    @Override
    public void delete(PriceUpdateHistory priceUpdateHistory) {
        priceUpdateHistoryRepository.delete(priceUpdateHistory);
    }
}