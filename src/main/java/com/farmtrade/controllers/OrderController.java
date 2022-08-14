package com.farmtrade.controllers;

import com.farmtrade.dto.OrderRequestDto;
import com.farmtrade.entities.OrderRequest;
import com.farmtrade.entities.ProductName;
import com.farmtrade.entities.User;
import com.farmtrade.filters.builders.OrderSpecificationBuilder;
import com.farmtrade.services.api.OrderRequestService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.activation.UnsupportedDataTypeException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
@RequestMapping("api/order-request")
@RestController
public class OrderController {
    private final OrderRequestService orderRequestService;

    public OrderController(OrderRequestService orderRequestService) {
        this.orderRequestService = orderRequestService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<OrderRequest> findPageWithRequests(
            @PageableDefault(sort = {"id"}, size = 10, page = 0, direction = Sort.Direction.DESC)
            Pageable pageable,
            @RequestParam(required = false) List<BigDecimal> quantity,
            @RequestParam(required = false) List<BigDecimal> unitPrice,
            @RequestParam(required = false) List<BigDecimal> size,
            @RequestParam(required = false) Long productName,
            @RequestParam(required = false) Timestamp loadingDate,
            @RequestParam(required = false) Timestamp auctionEndDate,
            @RequestParam(required = false) Long owner,
            @RequestParam(required = false) Long batchNumber,
            @RequestParam(required = false,defaultValue = "false") boolean completed
    ) throws UnsupportedDataTypeException {
        Specification<OrderRequest> specification = new OrderSpecificationBuilder(
                quantity, unitPrice, size, productName,loadingDate,auctionEndDate, owner, batchNumber, completed
        ).build();
        return orderRequestService.findPage(specification, pageable);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public OrderRequest createOrder(@RequestBody OrderRequestDto orderRequestDto){
        return orderRequestService.create(orderRequestDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public OrderRequest getDetailsOfOrder(){
        return null;
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public OrderRequest updateOrder(){
        return null;
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/batch")
    public List<OrderRequest> getBatchs(){
        return null;
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}/raisePrice")
    public OrderRequest updatePrice(){
        return null;
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{id}/complete?completeReason=(ULTIMATE_PRICE/SOLD)")
    public boolean finishOrder(){
        return false;
    }
}
