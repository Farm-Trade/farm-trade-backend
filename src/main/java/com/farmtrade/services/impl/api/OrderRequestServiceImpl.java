package com.farmtrade.services.impl.api;

import com.farmtrade.dto.orderrequests.OrderRequestCreateDto;
import com.farmtrade.dto.orderrequests.OrderRequestUpdateDto;
import com.farmtrade.entities.OrderRequest;
import com.farmtrade.entities.PriceUpdateHistory;
import com.farmtrade.entities.ProductName;
import com.farmtrade.entities.User;
import com.farmtrade.entities.enums.Role;
import com.farmtrade.exceptions.BadRequestException;
import com.farmtrade.repositories.OrderRequestRepository;
import com.farmtrade.services.abstracts.BaseCrudService;
import com.farmtrade.services.api.OrderRequestService;
import com.farmtrade.services.api.PriceUpdateHistoryService;
import com.farmtrade.services.api.ProductNameService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class OrderRequestServiceImpl extends BaseCrudService<OrderRequest, Long, OrderRequestUpdateDto> implements OrderRequestService {
    private final ProductNameService productNameService;
    private final PriceUpdateHistoryService priceUpdateHistoryService;

    public OrderRequestServiceImpl(OrderRequestRepository orderRequestRepository, ProductNameService productNameService, PriceUpdateHistoryService priceUpdateHistoryService) {
        super(orderRequestRepository);
        this.productNameService = productNameService;
        this.priceUpdateHistoryService = priceUpdateHistoryService;
    }

    @Override
    public Class<OrderRequest> getClassInstance() {
        return OrderRequest.class;
    }

    @Override
    public OrderRequest fullyUpdate(Long id, OrderRequestUpdateDto orderRequestUpdateDto) {
        OrderRequest orderRequest = findOne(id);
        if (orderRequest.isCompleted()) {
            throw new BadRequestException("Completed order request cannot be updated");
        }
        return super.fullyUpdate(id, orderRequestUpdateDto);
    }

    @Override
    public OrderRequest create(OrderRequestCreateDto orderRequestCreateDto, User user) {
        ProductName productName = productNameService.findOne(orderRequestCreateDto.getProductName());
        final OrderRequest orderRequest = OrderRequest.builder()
                .quantity(orderRequestCreateDto.getQuantity())
                .unitPrice(orderRequestCreateDto.getUnitPrice())
                .unitPriceUpdate(orderRequestCreateDto.getUnitPriceUpdate())
                .ultimatePrice(orderRequestCreateDto.getUltimatePrice())
                .productName(productName)
                .notes(orderRequestCreateDto.getNotes())
                .loadingDate(orderRequestCreateDto.getLoadingDate())
                .auctionEndDate(orderRequestCreateDto.getAuctionEndDate())
                .owner(user)
                .completed(false)
                .build();
        return repository.save(orderRequest);
    }

    @Override
    public OrderRequest updatePrice(Long id, User user) {
        OrderRequest orderRequest = findOne(id);
        if (orderRequest.isCompleted()) {
            throw new BadRequestException("Price cannot be updated on completed order request");
        }
        if (orderRequest.getOwner().getId().equals(user.getId())) {
            throw new BadRequestException("Owner cannot update price of the order request");
        }
        BigDecimal updatedPrice = orderRequest.getUnitPrice().add(orderRequest.getUnitPriceUpdate());
        PriceUpdateHistory priceUpdateHistory = PriceUpdateHistory.builder()
                .orderRequest(orderRequest)
                .updatedFrom(orderRequest.getUnitPrice())
                .updatedTo(updatedPrice)
                .updater(user)
                .build();
        orderRequest.setUnitPrice(updatedPrice);
        priceUpdateHistoryService.save(priceUpdateHistory);
        return repository.save(orderRequest);
    }

    @Override
    @Transactional
    public OrderRequest rejectUpdateUnitPrice(Long id, User user) {
        OrderRequest orderRequest = findOne(id);
        Optional<PriceUpdateHistory> priceUpdateHistory = priceUpdateHistoryService.getLastUpdateByOrderRequestAndUserId(
                orderRequest.getId(),
                user.getId()
        );
        priceUpdateHistory.ifPresent(ph -> {
            orderRequest.setUnitPrice(ph.getUpdatedFrom());
            priceUpdateHistoryService.delete(ph);
        });
        return repository.save(orderRequest);
    }

    @Override
    public void complete(Long id, User user) {
        OrderRequest orderRequest = findOne(id);
        if (!orderRequest.getOwner().getId().equals(user.getId()) && user.getRole().equals(Role.ADMIN)) {
            throw new BadRequestException("Only owners can complete their order request");
        }
        orderRequest.setCompleted(true);
        repository.save(orderRequest);
    }

    @Override
    public void delete(Long id) {
        OrderRequest orderRequest = findOne(id);
        if (orderRequest.isCompleted()) {
            throw new BadRequestException("Completed order request cannot be removed");
        }
        super.delete(orderRequest.getId());
    }
}
