package com.farmtrade.repositories;

import com.farmtrade.entities.ProductName;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductNameRepository extends JpaRepository<ProductName, Long>, CrudRepository<ProductName, Long> {
    // To gat page filter by approved
    Page<ProductName> findAllByApproved(boolean approved, Pageable pageable);
    // To get all
    List<ProductName> findAllByApproved(boolean approved);
    // To get one
    Optional<ProductName> findById(Long id);
    // To update and create one
    ProductName save(ProductName entity);
    // To delete one
    void delete(ProductName entity);
}
