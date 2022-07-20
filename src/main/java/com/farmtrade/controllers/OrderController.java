package com.farmtrade.controllers;

import com.farmtrade.entities.OrderRequest;
import com.farmtrade.entities.ProductName;
import com.farmtrade.entities.User;
import com.farmtrade.filters.builders.OrderSpecificationBuilder;
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

@RestController
public class OrderController {
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/orderRequests")
    public Page<OrderRequest> findPageWithRequests(
            @PageableDefault(sort = {"id"}, size = 10, page = 0, direction = Sort.Direction.DESC)
            Pageable pageable,
            @RequestParam(required = false) List<BigDecimal> quantity,
            @RequestParam(required = false) List<BigDecimal> unitPrice,
            @RequestParam(required = false) List<BigDecimal> size,
            @RequestParam(required = false) Long productName
    ) throws UnsupportedDataTypeException {
        Specification<OrderRequest> specification = new OrderSpecificationBuilder(
                quantity, unitPrice, size, productName
        ).build();
        return null;
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/orderRequests")
    public OrderRequest createOrder(){
        return null;
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/orderRequests/{id}")
    public OrderRequest getDetailsOfOrder(){
        return null;
    }
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/orderRequests/{id}")
    public OrderRequest updateOrder(){
        return null;
    }
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/orderRequest/batch")
    public List<OrderRequest> getBatchs(){
        return null;
    }
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/orderRequests/{id}/raisePrice")
    public OrderRequest updatePrice(){
        return null;
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/orderRequests/{id}/complete?completeReason=(ULTIMATE_PRICE/SOLD)")
    public boolean finishOrder(){
        return false;
    }
}
