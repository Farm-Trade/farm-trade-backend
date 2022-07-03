package com.farmtrade.entities;

import com.farmtrade.entities.enums.ProductType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "product_names")
@Data
public class ProductName implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private ProductType type;

    private boolean approved;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "product_name")
    @JsonBackReference
    private Set<OrderRequestPermission> orderRequestPermissions = new HashSet<OrderRequestPermission>();
}
