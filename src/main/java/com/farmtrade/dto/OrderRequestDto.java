package com.farmtrade.dto;

import com.farmtrade.entities.ProductName;
import com.farmtrade.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.sql.Timestamp;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {
    private BigDecimal quantity;
    private BigDecimal size;
    private BigDecimal unitPrice;
    private Long productName;
    private String loadingDate;
    private String auctionEndDate;
    private Long owner;
    private Long batchNumber;
}
