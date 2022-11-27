package com.farmtrade.services.impl.api;

import com.farmtrade.dto.orderrequests.OrderRequestUpdateCreateDto;
import com.farmtrade.entities.*;
import com.farmtrade.entities.enums.OrderRequestStatus;
import com.farmtrade.entities.enums.Role;
import com.farmtrade.exceptions.BadRequestException;
import com.farmtrade.exceptions.ForbiddenException;
import com.farmtrade.repositories.OrderRequestRepository;
import com.farmtrade.services.abstracts.BaseCrudService;
import com.farmtrade.services.api.OrderRequestService;
import com.farmtrade.services.api.PriceUpdateHistoryService;
import com.farmtrade.services.api.ProductNameService;
import com.farmtrade.services.api.ProductService;
import com.farmtrade.services.security.AuthService;
import com.farmtrade.utils.BigDecimalUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderRequestServiceImpl extends BaseCrudService<OrderRequest, Long, OrderRequestUpdateCreateDto> implements OrderRequestService {
    private final ProductNameService productNameService;
    private final PriceUpdateHistoryService priceUpdateHistoryService;
    private final ProductService productService;
    private final AuthService authService;
    private final OrderRequestRepository orderRequestRepository;

    public OrderRequestServiceImpl(OrderRequestRepository orderRequestRepository, ProductNameService productNameService, PriceUpdateHistoryService priceUpdateHistoryService, ProductService productService, AuthService authService) {
        super(orderRequestRepository);
        this.productNameService = productNameService;
        this.priceUpdateHistoryService = priceUpdateHistoryService;
        this.productService = productService;
        this.authService = authService;
        this.orderRequestRepository = orderRequestRepository;
    }

    @Override
    public Class<OrderRequest> getClassInstance() {
        return OrderRequest.class;
    }

    @Override
    public OrderRequest fullyUpdate(Long id, OrderRequestUpdateCreateDto orderRequestUpdateDto) {
        OrderRequest orderRequest = findOne(id);
        if (orderRequest.getStatus().equals(OrderRequestStatus.COMPLETED)) {
            throw new BadRequestException("Completed order request cannot be updated");
        }
        if (!isCurrentUser(orderRequest.getOwner())) {
            throw new ForbiddenException("User can update only their own order request");
        }
        if (orderRequest.getStatus().equals(OrderRequestStatus.PUBLISHED)) {
            throw new ForbiddenException("User can update order request which is published");
        }
        return super.fullyUpdate(id, orderRequestUpdateDto);
    }

    @Override
    public OrderRequest create(OrderRequestUpdateCreateDto orderRequestCreateDto) {
        ProductName productName = productNameService.findOne(orderRequestCreateDto.getProductName());
        User user = authService.getUserFromContext();
        if (!user.getRole().equals(Role.RESELLER)) {
            throw new ForbiddenException("Only RESELLER can create order request");
        }
        final OrderRequest orderRequest = OrderRequest.builder()
                .quantity(orderRequestCreateDto.getQuantity())
                .unitPrice(orderRequestCreateDto.getUnitPrice())
                .unitPriceUpdate(orderRequestCreateDto.getUnitPriceUpdate())
                .sizeFrom(orderRequestCreateDto.getSizeFrom())
                .productName(productName)
                .notes(orderRequestCreateDto.getNotes())
                .loadingDate(orderRequestCreateDto.getLoadingDate())
                .auctionEndDate(orderRequestCreateDto.getAuctionEndDate())
                .owner(user)
                .status(OrderRequestStatus.PENDING_INFORMATION)
                .build();
        validatePrices(orderRequest);
        return repository.save(orderRequest);
    }

    @Override
    @Transactional
    public OrderRequest updatePrice(Long id) {
        OrderRequest orderRequest = findOne(id);
        if (orderRequest.getStatus().equals(OrderRequestStatus.PENDING_INFORMATION)) {
            throw new BadRequestException("Price cannot be updated on pending information order request");
        }
        if (orderRequest.getStatus().equals(OrderRequestStatus.COMPLETED)) {
            throw new BadRequestException("Price cannot be updated on completed order request");
        }
        if (isCurrentUser(orderRequest.getOwner())) {
            throw new ForbiddenException("Owner cannot update price of the order request");
        }
        BigDecimal updatedPrice = orderRequest.getUnitPrice().subtract(orderRequest.getUnitPriceUpdate());
        validatePrices(orderRequest);
        Product product = getProductMatchToOrderRequest(orderRequest);
        product.setReservedQuantity(orderRequest.getQuantity());
        productService.save(product);
        PriceUpdateHistory priceUpdateHistory = this.buildPriceUpdateHistory(orderRequest, updatedPrice, product);
        orderRequest.setUnitPrice(updatedPrice);
        orderRequest.getPriceUpdateHistory().add(priceUpdateHistory);
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
            boolean hasOnlyOneRate = priceUpdateHistoryService.getAllByUserIdAndOrderRequestId(
                    user.getId(),
                    orderRequest.getId()
            ).size() == 1;
            if (hasOnlyOneRate) {
                Product product = ph.getProduct();
                product.setReservedQuantity(product.getReservedQuantity().subtract(orderRequest.getQuantity()).abs());
                productService.save(product);
            }
            priceUpdateHistoryService.delete(ph);
        });
        return repository.save(orderRequest);
    }

    @Override
    public void complete(Long id) {
        OrderRequest orderRequest = findOne(id);
        if (!isCurrentUser(orderRequest.getOwner())) {
            throw new BadRequestException("Only owners can complete their order request");
        }
        orderRequest.setStatus(OrderRequestStatus.COMPLETED);
        repository.save(orderRequest);
    }

    @Override
    public void publish(Long id) {
        OrderRequest orderRequest = findOne(id);
        if (orderRequest.getStatus().equals(OrderRequestStatus.COMPLETED)) {
            throw new ForbiddenException("Completed order request cannot be published");
        }
        if (!isCurrentUser(orderRequest.getOwner())) {
            throw new BadRequestException("Only owners can complete their order request");
        }
        orderRequest.setStatus(OrderRequestStatus.PUBLISHED);
        repository.save(orderRequest);
    }

    @Override
    public void delete(Long id) {
        OrderRequest orderRequest = findOne(id);
        if (orderRequest.getStatus().equals(OrderRequestStatus.COMPLETED)) {
            throw new ForbiddenException("Completed order request cannot be removed");
        }
        if (!isCurrentUser(orderRequest.getOwner())) {
            throw new ForbiddenException("Only owners can delete their order request");
        }
        super.delete(orderRequest.getId());
    }

    @Override
    public Page<OrderRequest> findAllOrderRequestMatchToCurrentUser(Pageable pageable) {
        User currentUser = this.authService.getUserFromContext();
        if (currentUser.getRole().equals(Role.RESELLER) || currentUser.getRole().equals(Role.ADMIN)) {
            throw new ForbiddenException("Only Farmer can check order requests related to them");
        }
        Set<Product> products = currentUser.getProducts();
        List<ProductName> productNames = products.stream().map(Product::getProductName).distinct().collect(Collectors.toList());

        List<OrderRequest> relatedOrderRequest = orderRequestRepository.findAllByProductNameInAndStatus(productNames, OrderRequestStatus.PUBLISHED);
        List<OrderRequest> orderRequests = relatedOrderRequest.stream().filter(orderRequest -> products.stream().anyMatch(product -> {
            BigDecimal freeQuantity = product.getQuantity().subtract(product.getReservedQuantity()).abs();
            return  orderRequest.getProductName().getId().equals(product.getProductName().getId()) &&
                    BigDecimalUtil.lessThenOrEqual(orderRequest.getQuantity(), freeQuantity) &&
                    BigDecimalUtil.greaterThenOrEqual(product.getSize(), orderRequest.getSizeFrom());
        })).collect(Collectors.toList());
        return new PageImpl<>(orderRequests, pageable, orderRequests.size());
    }

    @Override
    public Page<OrderRequest> findAllOrderRequestRatedByUser(Pageable pageable) {
        User user = authService.getUserFromContext();
        Set<PriceUpdateHistory> priceUpdateHistories = priceUpdateHistoryService.findAllLastUpdatesByUserId(user.getId());
        return orderRequestRepository.findAllByPriceUpdateHistoryIn(pageable, priceUpdateHistories);
    }

    private Product getProductMatchToOrderRequest(OrderRequest orderRequest) throws BadRequestException {
        User currentUser = this.authService.getUserFromContext();
        List<Product> products = productService.allProductsByUserAndProductNameAndSizeFrom(
                currentUser.getId(),
                orderRequest.getProductName().getId(),
                orderRequest.getSizeFrom()
        );
        if (products.size() == 0) {
            throw new BadRequestException("You have no products that match the conditions of the order request");
        }
        Optional<Product> availableProduct = products.stream().filter(product -> {
            BigDecimal freeQuantity = product.getQuantity().subtract(product.getReservedQuantity());
            return BigDecimalUtil.greaterThenOrEqual(freeQuantity, orderRequest.getQuantity());
        }).findFirst();

        if (availableProduct.isEmpty()) {
            throw new BadRequestException("You have no free quantity of the product related to the order request");
        }

        return availableProduct.get();
    }

    private boolean isCurrentUser(User user) {
        User currentUser = this.authService.getUserFromContext();
        return currentUser.getId().equals(user.getId());
    }

    private void validatePrices(OrderRequest orderRequest) {
        BigDecimal expectedNextRate = orderRequest.getUnitPrice().subtract(orderRequest.getUnitPriceUpdate());
        if (BigDecimalUtil.lessThen(expectedNextRate, BigDecimal.ZERO)) {
            throw new BadRequestException("Unit price should be grater than unit update price");
        }
    }

    private PriceUpdateHistory buildPriceUpdateHistory(
            OrderRequest orderRequest,
            BigDecimal updatedPrice,
            Product product
    ) {
        return PriceUpdateHistory.builder()
                .orderRequest(orderRequest)
                .updatedFrom(orderRequest.getUnitPrice())
                .updatedTo(updatedPrice)
                .product(product)
                .updater(this.authService.getUserFromContext())
                .build();
    }
}
