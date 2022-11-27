package com.farmtrade.entities;

import com.farmtrade.entities.enums.OrderRequestStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    @Min(value = 1, message = "Quantity should be equal or greater 1")
    private BigDecimal quantity;
    @Column(nullable = false)
    @Min(value = 0, message = "Unit price should be equal or greater 0")
    private BigDecimal unitPrice;
    @Min(value = 0, message = "Unit price update should be equal or greater 0")
    private BigDecimal unitPriceUpdate;
    @Min(value = 1, message = "Size from should be equal or greater 1")
    private BigDecimal sizeFrom;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_name_id", updatable = false)
    private ProductName productName;
    private String notes;
    @Future
    @DateTimeFormat(pattern = "mm/dd/yyyy HH:MM")
    private LocalDateTime loadingDate;
    @Future
    @DateTimeFormat(pattern = "mm/dd/yyyy HH:MM")
    private LocalDateTime auctionEndDate;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner", updatable = false)
    private User owner;
    @Enumerated(EnumType.STRING)
    private OrderRequestStatus status;
    @OneToMany(mappedBy = "orderRequest",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<PriceUpdateHistory> priceUpdateHistory = new HashSet<>();
}
