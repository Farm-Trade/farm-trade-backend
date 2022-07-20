package com.farmtrade.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private BigDecimal quantity;
    private BigDecimal size;
    private BigDecimal unitPrice;
    private BigDecimal unitPriceUpdate;
    private BigDecimal ultimatePrice;
    @ManyToOne
    private ProductName productName;
    private String notes;
    private Timestamp loadingDate;
    private Timestamp auctionEndDate;
    @ManyToOne
    private User owner;
    private Integer batchNumber;
    private boolean completed;

}
