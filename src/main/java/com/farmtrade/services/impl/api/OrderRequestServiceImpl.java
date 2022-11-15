package com.farmtrade.services.impl.api;

import com.farmtrade.dto.orderrequests.OrderRequestCreateDto;
import com.farmtrade.dto.orderrequests.OrderRequestUpdateDto;
import com.farmtrade.entities.OrderRequest;
import com.farmtrade.entities.PriceUpdateHistory;
import com.farmtrade.entities.ProductName;
import com.farmtrade.entities.User;
import com.farmtrade.entities.enums.Role;
import com.farmtrade.exceptions.BadRequestException;
import com.farmtrade.exceptions.ForbiddenException;
import com.farmtrade.repositories.OrderRequestRepository;
import com.farmtrade.services.abstracts.BaseCrudService;
import com.farmtrade.services.api.OrderRequestService;
import com.farmtrade.services.api.PriceUpdateHistoryService;
import com.farmtrade.services.api.ProductNameService;
import com.farmtrade.services.security.AuthService;
import com.farmtrade.utils.BigDecimalUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class OrderRequestServiceImpl extends BaseCrudService<OrderRequest, Long, OrderRequestUpdateDto> implements OrderRequestService {
    private final ProductNameService productNameService;
    private final PriceUpdateHistoryService priceUpdateHistoryService;
    private final AuthService authService;

    public OrderRequestServiceImpl(OrderRequestRepository orderRequestRepository, ProductNameService productNameService, PriceUpdateHistoryService priceUpdateHistoryService, AuthService authService) {
        super(orderRequestRepository);
        this.productNameService = productNameService;
        this.priceUpdateHistoryService = priceUpdateHistoryService;
        this.authService = authService;
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
        if (!isCurrentUser(orderRequest.getOwner())) {
            throw new ForbiddenException("User can update only their own order request");
        }
        return super.fullyUpdate(id, orderRequestUpdateDto);
    }

    @Override
    public OrderRequest create(OrderRequestCreateDto orderRequestCreateDto) {
        ProductName productName = productNameService.findOne(orderRequestCreateDto.getProductName());
        User user = authService.getUserFromContext();
        if (!user.getRole().equals(Role.RESELLER)) {
            throw new ForbiddenException("Only RESELLER can create order request");
        }
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
        validatePrices(orderRequest);
        return repository.save(orderRequest);
    }

    @Override
    @Transactional
    public OrderRequest updatePrice(Long id) {
        OrderRequest orderRequest = findOne(id);
        if (orderRequest.isCompleted()) {
            throw new BadRequestException("Price cannot be updated on completed order request");
        }
        if (isCurrentUser(orderRequest.getOwner())) {
            throw new ForbiddenException("Owner cannot update price of the order request");
        }
        BigDecimal updatedPrice = orderRequest.getUnitPrice().subtract(orderRequest.getUnitPriceUpdate());
        if (BigDecimalUtil.lessThenOrEqual(updatedPrice, BigDecimal.ZERO)) {
            throw new ForbiddenException("Request price cannot be less than 0");
        }
        BigDecimal ultimatePrice = orderRequest.getUltimatePrice();
        if (ultimatePrice != null && BigDecimalUtil.lessThen(ultimatePrice, ultimatePrice)) {
            throw new ForbiddenException("Request price cannot be less than ultimate price");
        }
        validatePrices(orderRequest);
        PriceUpdateHistory priceUpdateHistory = this.buildPriceUpdateHistory(orderRequest, updatedPrice);
        orderRequest.setUnitPrice(updatedPrice);
        priceUpdateHistoryService.save(priceUpdateHistory);
        return repository.save(orderRequest);
    }

    @Override
    @Transactional
    public OrderRequest rejectUpdateUnitPrice(Long id) {
        User user = this.authService.getUserFromContext();
        OrderRequest orderRequest = findOne(id);
        Optional<PriceUpdateHistory> priceUpdateHistory = priceUpdateHistoryService.getLastUpdateByOrderRequestAndUserId(
                orderRequest.getId(),
                user.getId()
        );
        priceUpdateHistory.ifPresent(ph -> {
            if (!isCurrentUser(ph.getUpdater())) {
                throw new ForbiddenException("Price could be rejected only if last offer was provided by current user");
            }
            orderRequest.setUnitPrice(ph.getUpdatedFrom());
            priceUpdateHistoryService.delete(ph);
        });
        return repository.save(orderRequest);
    }

    @Override
    @Transactional
    public OrderRequest applyToUltimatePrice(Long id) {
        OrderRequest orderRequest = findOne(id);
        if (isCurrentUser(orderRequest.getOwner())) {
            throw new ForbiddenException("Owner cannot apply fo ultimate price of the order request");
        }
        if (BigDecimalUtil.lessThenOrEqual(orderRequest.getUnitPrice(), orderRequest.getUltimatePrice())) {
            throw new BadRequestException("Cannot apply fo ultimate price, ultimate price is greater or equal unit price");
        }
        PriceUpdateHistory priceUpdateHistory = this.buildPriceUpdateHistory(orderRequest, orderRequest.getUltimatePrice());
        orderRequest.setUnitPrice(orderRequest.getUltimatePrice());
        priceUpdateHistoryService.save(priceUpdateHistory);
        return repository.save(orderRequest);
    }

    @Override
    public void complete(Long id) {
        OrderRequest orderRequest = findOne(id);
        if (!isCurrentUser(orderRequest.getOwner())) {
            throw new BadRequestException("Only owners can complete their order request");
        }
        orderRequest.setCompleted(true);
        repository.save(orderRequest);
    }

    @Override
    public void delete(Long id) {
        OrderRequest orderRequest = findOne(id);
        if (orderRequest.isCompleted()) {
            throw new ForbiddenException("Completed order request cannot be removed");
        }
        if (!isCurrentUser(orderRequest.getOwner())) {
            throw new ForbiddenException("Only owners can delete their order request");
        }
        super.delete(orderRequest.getId());
    }

    private boolean isCurrentUser(User user) {
        User currentUser = this.authService.getUserFromContext();
        return currentUser.getId().equals(user.getId());
    }
    private void validatePrices(OrderRequest orderRequest) {
        if (BigDecimalUtil.lessThenOrEqual(orderRequest.getUnitPrice(), orderRequest.getUltimatePrice())) {
            throw new BadRequestException("Unit price should be grater than ultimate price");
        }
        BigDecimal expectedNextRate = orderRequest.getUnitPrice().subtract(orderRequest.getUnitPriceUpdate());
        if (BigDecimalUtil.lessThen(expectedNextRate, BigDecimal.ZERO)) {
            throw new BadRequestException("Unit price should be grater than unit update price");
        }
    }

    private PriceUpdateHistory buildPriceUpdateHistory(OrderRequest orderRequest, BigDecimal updatedPrice) {
        return PriceUpdateHistory.builder()
                .orderRequest(orderRequest)
                .updatedFrom(orderRequest.getUnitPrice())
                .updatedTo(updatedPrice)
                .updater(this.authService.getUserFromContext())
                .build();
    }
}
