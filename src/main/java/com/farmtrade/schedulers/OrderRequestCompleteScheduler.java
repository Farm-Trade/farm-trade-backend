package com.farmtrade.schedulers;

import com.farmtrade.services.api.OrderRequestService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OrderRequestCompleteScheduler {
    private final OrderRequestService orderRequestService;
    private final boolean isOrderRequestCompletingDisable;

    public OrderRequestCompleteScheduler(
            OrderRequestService orderRequestService,
            @Value("${orderRequest.complete.disable}")
            boolean isOrderRequestCompletingDisable
    ) {
        this.orderRequestService = orderRequestService;
        this.isOrderRequestCompletingDisable = isOrderRequestCompletingDisable;
    }

    @Scheduled(fixedDelayString = "${orderRequest.complete.interval}")
    public void completeOrderRequest() {
        if (isOrderRequestCompletingDisable) {
            return;
        }
        orderRequestService.completeAllBasedOnEndAuctionDate();
    }
}
