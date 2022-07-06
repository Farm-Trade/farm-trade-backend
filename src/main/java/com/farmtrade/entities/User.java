package com.farmtrade.entities;

import com.farmtrade.entities.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;


import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;

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
    private String email = null;
    private String password;
    private boolean isActive = false;
    private Role role;
    private String activationCode = null;



   /* @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }*/


}
