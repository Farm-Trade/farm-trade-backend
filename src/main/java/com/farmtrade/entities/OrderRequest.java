package com.farmtrade.entities;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "order_requests")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class OrderRequest implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @Min(value = 1, message = "Size should be equal or greater 1")
    private BigDecimal quantity;
    @Column(nullable = false)
    @Min(value = 0, message = "Size should be equal or greater 0")
    private BigDecimal unitPrice;
    @Min(value = 0, message = "Size should be equal or greater 0")
    private BigDecimal unitPriceUpdate;
    @Min(value = 0, message = "Size should be equal or greater 0")
    private BigDecimal ultimatePrice;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_name_id", updatable = false)
    private ProductName productName;
    private String notes;
    @Future
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate loadingDate;
    @Future
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate auctionEndDate;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner", updatable = false)
    private User owner;
    private boolean completed;
    @OneToMany(mappedBy = "orderRequest",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<PriceUpdateHistory> priceUpdateHistory = new HashSet<>();
}
