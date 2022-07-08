package com.farmtrade.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@Table(name = "products")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Min(value = 1, message = "Size should be equal or greater 1")
    private Float quantity;
    @ColumnDefault(value = "0")
    private Float reservedQuantity;
    @Min(value = 1, message = "Size should be equal or greater 1")
    private Float size;
    private String img;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_name_id", updatable = false)
    private ProductName productName;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner", updatable = false)
    private User owner;
}
