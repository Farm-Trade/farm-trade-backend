package com.farmtrade.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

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
    private Float quantity;
    @ColumnDefault(value = "0")
    private Float reservedQuantity;
    private Float size;
    private String img;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_name_id")
    private ProductName productName;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner")
    private User owner;
}
