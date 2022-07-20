package com.farmtrade.services.api;

import com.farmtrade.dto.OrderRequestDto;
import com.farmtrade.entities.OrderRequest;
import com.farmtrade.repositories.BaseJpaAndSpecificationRepository;
import com.farmtrade.services.abstracts.BaseCrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;


public class orderService extends BaseCrudService<OrderRequest, Long, OrderRequestDto> {
    @Override
    public Class<OrderRequest> getClassInstance() {
        return null;
    }

    protected orderService(BaseJpaAndSpecificationRepository<OrderRequest, Long> repository) {
        super(repository);
    }

    @Override
    public Page<OrderRequest> findPage(Pageable pageable) {
        return super.findPage(pageable);
    }

    @Override
    public Page<OrderRequest> findPage(Specification<OrderRequest> specification, Pageable pageable) {
        return super.findPage(specification, pageable);
    }

    @Override
    public OrderRequest findOne(Long aLong) {
        return super.findOne(aLong);
    }

    @Override
    public OrderRequest simpleCreate(OrderRequest entity) {
        return super.simpleCreate(entity);
    }

    @Override
    public OrderRequest fullyUpdate(Long aLong, OrderRequestDto updateDTO) {
        return super.fullyUpdate(aLong, updateDTO);
    }

    @Override
    public void delete(Long aLong) {
        super.delete(aLong);
    }
}
