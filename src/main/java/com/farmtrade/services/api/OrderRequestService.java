package com.farmtrade.services.api;

import com.farmtrade.dto.OrderRequestDto;
import com.farmtrade.entities.OrderRequest;
import com.farmtrade.services.interfaces.IBaseCrudService;

import java.math.BigDecimal;

public interface OrderRequestService extends IBaseCrudService<OrderRequest, Long, OrderRequestDto> {
    OrderRequest create(OrderRequestDto orderRequestDto);

    OrderRequest updatePrice(Long id, BigDecimal unitCostUpdate);
}
