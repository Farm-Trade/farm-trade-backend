package com.farmtrade.dto.orderrequests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequestUpdateCreateDto {
    @NotNull
    private BigDecimal quantity;
    @NotNull
    private BigDecimal unitPrice;
    private BigDecimal unitPriceUpdate;
    private BigDecimal sizeFrom;
    private Long productName;
    private String notes;
    private LocalDateTime loadingDate;
    private LocalDateTime auctionEndDate;
}
