package com.farmtrade.entities;

import com.farmtrade.entities.enums.ProductType;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "product_names")
@Data
public class ProductName implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private ProductType type;
}
