package com.farmtrade.services.impl;

import com.farmtrade.dto.OrderRequestDto;
import com.farmtrade.entities.OrderRequest;
import com.farmtrade.repositories.BaseJpaAndSpecificationRepository;
import com.farmtrade.services.abstracts.BaseCrudService;
import com.farmtrade.services.interfaces.OrderRequestService;
import com.farmtrade.services.api.ProductNameService;
import com.farmtrade.services.interfaces.UserService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Service
public class OrderRequestServiceImpl extends BaseCrudService<OrderRequest, Long, OrderRequestDto> implements OrderRequestService{
    private final UserService userService;
    private final ProductNameService productNameService;

    public OrderRequestServiceImpl(BaseJpaAndSpecificationRepository<OrderRequest, Long> repository, UserService userService, ProductNameService productNameService) {
        super(repository);
        this.userService = userService;
        this.productNameService = productNameService;
    }

    @Override
    public Class<OrderRequest> getClassInstance() {
        return OrderRequest.class;
    }

    public OrderRequest create(OrderRequestDto orderRequestDto) {
        OrderRequest order = OrderRequest.builder()
                .productName(productNameService.findOne(orderRequestDto.getProductName()))
                .auctionEndDate(Timestamp.valueOf(orderRequestDto.getAuctionEndDate()))
                .unitPrice(orderRequestDto.getUnitPrice())
                .loadingDate(Timestamp.valueOf(orderRequestDto.getLoadingDate()))
                .size(orderRequestDto.getSize())
                .quantity(orderRequestDto.getQuantity())
                .notes(orderRequestDto.getNotes())
                .ultimatePrice(orderRequestDto.getUltimatePrice())
                .unitPriceUpdate(orderRequestDto.getUnitPriceUpdate())
        .build();

        return  repository.save(order);
    }

    public OrderRequest updatePrice(Long id, BigDecimal unitCostUpdate) {
        OrderRequest orderRequest = repository.getReferenceById(id);
        orderRequest.setUnitPriceUpdate(unitCostUpdate);

        return repository.save(orderRequest);
    }
}
