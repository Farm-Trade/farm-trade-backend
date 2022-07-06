package com.farmtrade.repositories;

import com.farmtrade.entities.ProductName;
import com.farmtrade.entities.enums.ProductType;
import com.farmtrade.entities.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductNameRepository extends JpaRepository<ProductName, Long>, CrudRepository<ProductName, Long> {
}
