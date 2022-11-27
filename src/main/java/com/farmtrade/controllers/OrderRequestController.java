package com.farmtrade.controllers;

import com.farmtrade.dto.orderrequests.OrderRequestUpdateCreateDto;
import com.farmtrade.entities.OrderRequest;
import com.farmtrade.entities.enums.OrderRequestStatus;
import com.farmtrade.filters.builders.OrderRequestSpecificationsBuilder;
import com.farmtrade.services.api.OrderRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.activation.UnsupportedDataTypeException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.farmtrade.constants.SwaggerConstants.SECURITY_SCHEME_NAME;

@RestController
@RequestMapping("api/order-requests")
@SecurityRequirement(name = SECURITY_SCHEME_NAME)
@Tag(name = "Order Request Controller", description = "The Order Request API")
public class OrderRequestController {
    private final OrderRequestService orderRequestService;

    public OrderRequestController(OrderRequestService orderRequestService) {
        this.orderRequestService = orderRequestService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    @Operation(summary = "Get order request page")
    public Page<OrderRequest> findPage(
            @ParameterObject @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false) List<BigDecimal> quantity,
            @RequestParam(required = false) List<BigDecimal> unitPrice,
            @RequestParam(required = false) LocalDateTime loadingDate,
            @RequestParam(required = false) LocalDateTime auctionEndDate,
            @RequestParam(required = false) Long productName,
            @RequestParam(required = false) Long owner,
            @RequestParam(required = false) List<OrderRequestStatus> status
    ) throws UnsupportedDataTypeException {
        if (status == null) {
            status = List.of(OrderRequestStatus.PENDING_INFORMATION, OrderRequestStatus.PUBLISHED);
        }
        Specification<OrderRequest> specification = new OrderRequestSpecificationsBuilder(
                quantity,
                unitPrice,
                loadingDate,
                auctionEndDate,
                productName,
                owner,
                status
        ).build();
        return orderRequestService.findPage(specification, pageable);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    @Operation(summary = "Get order request")
    public OrderRequest findOne(@PathVariable Long id) {
        return orderRequestService.findOne(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @Operation(summary = "Create order request")
    public OrderRequest create(@RequestBody OrderRequestUpdateCreateDto orderRequest) {
        return orderRequestService.create(orderRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    @Operation(summary = "Update order request")
    public OrderRequest update(@PathVariable Long id, @RequestBody OrderRequestUpdateCreateDto entity) {
        return orderRequestService.fullyUpdate(id, entity);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}/update-unit-price")
    @Operation(summary = "Update unit price")
    public OrderRequest updateUnitPrice(@PathVariable Long id) {
        return orderRequestService.updatePrice(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}/reject-unit-price")
    @Operation(summary = "Reject last user's unit price update")
    public OrderRequest rejectUpdateUnitPrice(@PathVariable Long id) {
        return orderRequestService.rejectUpdateUnitPrice(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product")
    public void delete(@PathVariable Long id) {
        orderRequestService.delete(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/{id}/complete")
    @Operation(summary = "Complete order request")
    public void complete(@PathVariable Long id) {
        orderRequestService.complete(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/{id}/publish")
    @Operation(summary = "Publish order request")
    public void publish(@PathVariable Long id) {
        orderRequestService.publish(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/match-requests")
    @Operation(summary = "Get all order request related to user's prodcut")
    public List<OrderRequest> findAllOrderRequestMatchToCurrentUser() {
        return orderRequestService.findAllOrderRequestMatchToCurrentUser();
    }
}
