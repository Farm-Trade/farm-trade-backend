package com.farmtrade.entities;

import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "price_update_history")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PriceUpdateHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(updatable = false)
    private BigDecimal updatedFrom;
    @Column(updatable = false)
    private BigDecimal updatedTo;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "updater", updatable = false)
    private User updater;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "orderRequest", updatable = false)
    @JsonIgnore
    private OrderRequest orderRequest;
    @CreationTimestamp
    private Timestamp createdAt;
}
