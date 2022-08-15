package com.farmtrade.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface IBaseCrudService<T, ID, DTO> {
    Page<T> findPage(Pageable pageable);

    Page<T> findPage(Specification<T> specification, Pageable pageable);

    T findOne(ID id);

    T simpleCreate(T entity);

    T fullyUpdate(ID id, DTO updateDTO);

    void delete(ID id);
}
