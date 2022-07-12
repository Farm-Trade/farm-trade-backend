package com.farmtrade.repositories;

import com.farmtrade.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    User findByFullName(String username);
    Optional<User> findByActivationCode(String activationCode);
    Optional<User> findByPhone(String phone);
}
