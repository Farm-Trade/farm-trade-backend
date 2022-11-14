package com.farmtrade.dto.orderrequests;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class OrderRequestUpdateDto {
    @NotNull
    private BigDecimal quantity;
    @NotNull
    private BigDecimal unitPrice;
    private BigDecimal unitPriceUpdate;
    private BigDecimal ultimatePrice;
    private String notes;
}
