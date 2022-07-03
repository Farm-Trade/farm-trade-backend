package com.farmtrade.entities;

import com.farmtrade.entities.enums.CommercialRole;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "order_request_permissions")
@Data
public class OrderRequestPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    @JsonManagedReference
    private ProductName productName;

    private CommercialRole commercialRole;
}
