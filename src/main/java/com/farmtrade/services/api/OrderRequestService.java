package com.farmtrade.services.api;

import com.farmtrade.dto.orderrequests.OrderRequestCreateDto;
import com.farmtrade.dto.orderrequests.OrderRequestUpdateDto;
import com.farmtrade.entities.OrderRequest;
import com.farmtrade.entities.User;
import com.farmtrade.services.interfaces.IBaseCrudService;

public interface OrderRequestService extends IBaseCrudService<OrderRequest, Long, OrderRequestUpdateDto> {
    OrderRequest create(OrderRequestCreateDto orderRequestCreateDto, User user);
    OrderRequest updatePrice(Long id, User user);
    OrderRequest rejectUpdateUnitPrice(Long id, User user);
    void complete(Long id, User user);
}
