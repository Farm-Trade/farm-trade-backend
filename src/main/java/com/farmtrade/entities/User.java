package com.farmtrade.entities;

import com.farmtrade.entities.enums.Role;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;


import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Set;

@Entity(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class User implements Serializable /*implements UserDetails*/ {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String fullName;
    @CreationTimestamp
    private Timestamp createdAt;
    @Column(unique = true)
    private String phone;
    @Column(unique = true)
    private String email;
    @JsonIgnore
    private String password;
    @Column(columnDefinition = "boolean default false")
    private boolean isActive;
    private Role role;
    @JsonIgnore
    private String activationCode;
    @OneToMany(mappedBy = "owner",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<Product> products;



   /* @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }*/


}
