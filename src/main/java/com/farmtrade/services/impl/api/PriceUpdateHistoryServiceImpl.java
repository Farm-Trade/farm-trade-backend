package com.farmtrade.services.impl.api;

import com.farmtrade.entities.OrderRequest;
import com.farmtrade.entities.PriceUpdateHistory;
import com.farmtrade.entities.Product;
import com.farmtrade.repositories.PriceUpdateHistoryRepository;
import com.farmtrade.services.api.PriceUpdateHistoryService;
import com.farmtrade.services.api.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PriceUpdateHistoryServiceImpl implements PriceUpdateHistoryService {
    private final PriceUpdateHistoryRepository priceUpdateHistoryRepository;
    private final ProductService productService;

    public PriceUpdateHistoryServiceImpl(PriceUpdateHistoryRepository priceUpdateHistoryRepository, ProductService productService) {
        this.priceUpdateHistoryRepository = priceUpdateHistoryRepository;
        this.productService = productService;
    }

    @Override
    public PriceUpdateHistory save(PriceUpdateHistory priceUpdateHistory) {
        return priceUpdateHistoryRepository.save(priceUpdateHistory);
    }

    @Override
    public Optional<PriceUpdateHistory> getLastUpdateByOrderRequestAndUserId(Long orderRequestId, Long userId) {
        return priceUpdateHistoryRepository.findFirstByOrderRequestIdAndUpdaterIdOrderByCreatedAtDesc(orderRequestId, userId);
    }

    @Override
    public void delete(PriceUpdateHistory priceUpdateHistory) {
        priceUpdateHistoryRepository.delete(priceUpdateHistory);
    }

    @Override
    public Set<PriceUpdateHistory> findAllLastUpdatesByUserId(Long id) {
        return priceUpdateHistoryRepository.findAllLastUpdatesByUserId(id);
    }

    @Override
    public List<PriceUpdateHistory> getAllByUserIdAndOrderRequestId(Long userId, Long orderRequestId) {
        return priceUpdateHistoryRepository.findAllByOrderRequestIdAndUpdaterId(userId, orderRequestId);
    }

    @Override
    public void deleteAllExceptLastThreeRatesByOrderRequestId(OrderRequest orderRequest) {
        Long id = orderRequest.getId();
        Set<Long> top3ProductIds = priceUpdateHistoryRepository.findTop3ByOrderRequestIdOrderByCreatedAt(id).stream()
                .map(priceUpdateHistory -> priceUpdateHistory.getProduct().getId())
                .collect(Collectors.toSet());
        orderRequest.getPriceUpdateHistory().forEach(priceUpdateHistory -> {
            if (!top3ProductIds.contains(priceUpdateHistory.getProduct().getId())) {
                Product product = priceUpdateHistory.getProduct();
                product.setReservedQuantity(product.getReservedQuantity().subtract(orderRequest.getQuantity()));
                productService.save(product);
            }
        });
        priceUpdateHistoryRepository.deleteAllByOrderRequestExceptTop3(id);
    }
}
