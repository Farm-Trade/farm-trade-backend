package com.farmtrade.schedulers;

import com.farmtrade.services.api.OrderRequestService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OrderRequestCompleteScheduler {
    private final OrderRequestService orderRequestService;

    public OrderRequestCompleteScheduler(OrderRequestService orderRequestService) {
        this.orderRequestService = orderRequestService;
    }

    @Scheduled(fixedDelayString = "${orderRequest.complete.interval}")
    public void completeOrderRequest() {
        orderRequestService.completeAllBasedOnEndAuctionDate();
    }
}
