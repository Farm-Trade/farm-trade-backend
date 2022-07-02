package com.farmtrade.repositories;

import com.farmtrade.entities.ProductName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductNameRepository extends JpaRepository<ProductName, Long> {

}
