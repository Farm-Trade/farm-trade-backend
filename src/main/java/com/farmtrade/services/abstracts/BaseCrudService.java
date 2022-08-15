package com.farmtrade.services.abstracts;

import com.farmtrade.exceptions.EntityNotFoundException;
import com.farmtrade.repositories.BaseJpaAndSpecificationRepository;
import com.farmtrade.services.interfaces.IBaseCrudService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.HashSet;
import java.util.Set;

public abstract class BaseCrudService<T, ID, DTO> implements IBaseCrudService<T, ID, DTO> {
    protected final BaseJpaAndSpecificationRepository<T, ID> repository;
    protected Set<String> ignoreUpdateProperties = new HashSet<>();

    protected BaseCrudService(BaseJpaAndSpecificationRepository<T, ID> repository) {
        this.repository = repository;
    }

    protected abstract Class<T> getClassInstance();

    public Page<T> findPage(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<T> findPage(Specification<T> specification, Pageable pageable) {
        return repository.findAll(specification, pageable);
    }
    
    public T findOne(ID id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(getClassInstance(), String.valueOf(id)));
    }

    public T simpleCreate(T entity) {
        return repository.save(entity);
    }

    public T fullyUpdate(ID id, DTO updateDTO) {
        T entityFromDB = findOne(id);

        String[] preparedIgnoreUpdateProperties = ignoreUpdateProperties
                .toArray(new String[ignoreUpdateProperties.size()]);
        BeanUtils.copyProperties(updateDTO, entityFromDB, preparedIgnoreUpdateProperties);

        return repository.save(entityFromDB);
    }

    public void delete(ID id) {
        T entity = findOne(id);
        repository.delete(entity);
    }
}
