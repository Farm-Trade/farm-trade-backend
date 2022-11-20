package com.farmtrade.services.api;

import com.farmtrade.dto.orderrequests.OrderRequestUpdateCreateDto;
import com.farmtrade.entities.OrderRequest;
import com.farmtrade.services.interfaces.IBaseCrudService;

public interface OrderRequestService extends IBaseCrudService<OrderRequest, Long, OrderRequestUpdateCreateDto> {
    OrderRequest create(OrderRequestUpdateCreateDto orderRequestCreateDto);

    OrderRequest updatePrice(Long id);

    OrderRequest rejectUpdateUnitPrice(Long id);

    OrderRequest applyToUltimatePrice(Long id);

    void complete(Long id);
}
