package com.farmtrade.entities;

import com.farmtrade.entities.enums.Role;
import com.farmtrade.entities.enums.ProductType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "product_names")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductName implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String name;

    private ProductType type;

    private boolean approved;

    private Role createRequestPermission;

    @OneToMany(mappedBy = "productName",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<Product> products;
}
