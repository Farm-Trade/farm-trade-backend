package com.farmtrade.controllers;

import com.farmtrade.dto.orderrequests.OrderRequestCreateDto;
import com.farmtrade.dto.orderrequests.OrderRequestUpdateDto;
import com.farmtrade.entities.OrderRequest;
import com.farmtrade.entities.User;
import com.farmtrade.filters.builders.OrderRequestSpecificationsBuilder;
import com.farmtrade.services.api.OrderRequestService;
import com.farmtrade.services.security.AuthService;
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
import java.time.LocalDate;
import java.util.List;

import static com.farmtrade.constants.SwaggerConstants.SECURITY_SCHEME_NAME;

@RestController
@RequestMapping("api/order-requests")
@SecurityRequirement(name = SECURITY_SCHEME_NAME)
@Tag(name = "Order Request Controller", description = "The Order Request API")
public class OrderRequestController {
    private final OrderRequestService orderRequestService;
    private final AuthService authService;

    public OrderRequestController(OrderRequestService orderRequestService, AuthService authService) {
        this.orderRequestService = orderRequestService;
        this.authService = authService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    @Operation(summary = "Get order request page")
    public Page<OrderRequest> findPage(
            @ParameterObject @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false) List<BigDecimal> quantity,
            @RequestParam(required = false) List<BigDecimal> unitPrice,
            @RequestParam(required = false) LocalDate loadingDate,
            @RequestParam(required = false) LocalDate auctionEndDate,
            @RequestParam(required = false) Long productName,
            @RequestParam(required = false) Long owner,
            @RequestParam(required = false, defaultValue = "false") boolean completed
    ) throws UnsupportedDataTypeException {
        Specification<OrderRequest> specification = new OrderRequestSpecificationsBuilder(
                quantity,
                unitPrice,
                loadingDate,
                auctionEndDate,
                productName,
                owner,
                completed
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
    public OrderRequest create(@RequestBody OrderRequestCreateDto orderRequest) {
        User user = authService.getUserFromContext();
        return orderRequestService.create(orderRequest, user);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    @Operation(summary = "Update order request")
    public OrderRequest update(@PathVariable Long id, @RequestBody OrderRequestUpdateDto entity) {
        return orderRequestService.fullyUpdate(id, entity);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}/update-unit-price")
    @Operation(summary = "Update unit price")
    public OrderRequest updateUnitPrice(@PathVariable Long id) {
        User user = authService.getUserFromContext();
        return orderRequestService.updatePrice(id, user);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}/reject-unit-price")
    @Operation(summary = "Reject last user's unit price update")
    public OrderRequest rejectUpdateUnitPrice(@PathVariable Long id) {
        User user = authService.getUserFromContext();
        return orderRequestService.rejectUpdateUnitPrice(id, user);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product")
    public void delete(@PathVariable Long id) {
        orderRequestService.delete(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}/complete")
    @Operation(summary = "Complete order request")
    public void complete(@PathVariable Long id) {
        User user = authService.getUserFromContext();
        orderRequestService.complete(id, user);
    }
}
