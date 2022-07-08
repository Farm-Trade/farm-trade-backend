package com.farmtrade.abstracts;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

public abstract class BaseCrudController<ET, ID, DTO, ST extends BaseCrudService<ET, ID, DTO>> {
    protected final ST service;

    protected BaseCrudController(ST service) {
        this.service = service;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<ET> findPage(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return service.findPage(pageable);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public ET findOne(@PathVariable ID id) {
        return service.findOne(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ET create(@RequestBody ET entity) {
        return service.simpleCreate(entity);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}")
    public ET update(@PathVariable ID id, @RequestBody DTO entity) {
        return service.fullyUpdate(id, entity);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable ID id) {
        service.delete(id);
    }
}
