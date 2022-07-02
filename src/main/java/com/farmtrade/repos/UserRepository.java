package com.farmtrade.repos;

import com.farmtrade.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByFullName(String username);

}
