package com.farmtrade.services.api;

import com.farmtrade.dto.OrderRequestDto;
import com.farmtrade.entities.OrderRequest;
import com.farmtrade.repositories.BaseJpaAndSpecificationRepository;
import com.farmtrade.repositories.OrderRequestRepository;
import com.farmtrade.services.abstracts.BaseCrudService;
import com.farmtrade.services.impl.UserServiceImpl;
import com.farmtrade.services.interfaces.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Service
public class OrderRequestService extends BaseCrudService<OrderRequest, Long, OrderRequestDto>{
    private final UserService userService;
    private final ProductNameService productNameService;

    public OrderRequestService(BaseJpaAndSpecificationRepository<OrderRequest, Long> repository, UserService userService, ProductNameService productNameService) {
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
                .owner(userService.getUser(orderRequestDto.getOwner()))
                .productName(productNameService.findOne(orderRequestDto.getProductName()))
                .auctionEndDate(Timestamp.valueOf(orderRequestDto.getAuctionEndDate()))
                .batchNumber(orderRequestDto.getBatchNumber())
                .unitPrice(orderRequestDto.getUnitPrice())
                .loadingDate(Timestamp.valueOf(orderRequestDto.getLoadingDate()))
                .size(orderRequestDto.getSize())
                .quantity(orderRequestDto.getQuantity())
        .build();

        return  repository.save(order);
    }

    public OrderRequest updatePrice(Long id, BigDecimal unitCostUpdate) {
        OrderRequest orderRequest = repository.getReferenceById(id);
        orderRequest.setUnitPriceUpdate(unitCostUpdate);

        return repository.save(orderRequest);
    }
}
