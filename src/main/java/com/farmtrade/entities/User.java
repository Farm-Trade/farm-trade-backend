package com.farmtrade.entities;

import com.farmtrade.entities.enums.Role;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;


@Entity
@Table(name = "users")
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    @CreationTimestamp
    private Timestamp createdAt;
    @Column(unique = true)
    private String phone;
    @Column(unique = true)
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @Column(columnDefinition = "boolean default false")
    private boolean isActive;
    @Enumerated(EnumType.STRING)
    private Role role;
    @JsonIgnore
    private String activationCode;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "business_details_id", referencedColumnName = "id")
    @JsonBackReference
    private BusinessDetails businessDetails;
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<Product> products;
}
