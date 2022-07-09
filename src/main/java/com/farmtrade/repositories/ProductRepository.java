package com.farmtrade.repositories;

import com.farmtrade.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends JpaRepository<Product, Long>, CrudRepository<Product, Long> {
}
