package com.farmtrade.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal quantity;
    @Column(nullable = false)
    private BigDecimal reservedQuantity;
    private BigDecimal size;
    private String img;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_name_id", updatable = false)
    private ProductName productName;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner", updatable = false)
    private User owner;
}
