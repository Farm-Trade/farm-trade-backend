package com.farmtrade.repositories;

import com.farmtrade.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByFullName(String username);

}
