package com.farmtrade.repos;

import com.farmtrade.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Override
    default List<User> findAll() {
        return null;
    }

    User findByUsername(String username);
}
