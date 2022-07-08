package com.farmtrade.services.abstracts;

import com.farmtrade.exceptions.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public abstract class BaseCrudService<T, ID, DTO> {
    private final JpaRepository<T, ID> repository;
    private final Set<String> ignoreUpdateProperties;

    protected BaseCrudService(JpaRepository<T, ID> repository, Set<String> ignoreUpdateProperties) {
        this.repository = repository;
        this.ignoreUpdateProperties = ignoreUpdateProperties;
    }

    public abstract Class<T> getClassInstance();

    public Page<T> findPage(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public T findOne(ID id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(getClassInstance(), String.valueOf(id)));
    }

    public T create(T entity) {
        return repository.save(entity);
    }

    public T fullyUpdate(ID id, DTO updateDTO) {
        T entityFromDB = findOne(id);

        String[] preparedIgnoreUpdateProperties = ignoreUpdateProperties
                .toArray(new String[ignoreUpdateProperties.size()]);
        BeanUtils.copyProperties(updateDTO, entityFromDB, preparedIgnoreUpdateProperties);

        return repository.save(entityFromDB);
    }

    //
//    public T partialUpdate(ID id, DTO updateDTO) {
//        T entityFromDB = findOne(id);
//        // TODO Add partial update
//        return repository.save(entityFromDB);
//    }
    public void delete(ID id) {
        T entity = findOne(id);
        repository.delete(entity);
    }
}
