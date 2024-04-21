package com.aqiu.spzx.manager.service;

import com.aqiu.spzx.model.entity.product.ProductSpec;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ProductSpecService {
    PageInfo<ProductSpec> list(Integer page, Integer limit);

    void save(ProductSpec productSpec);

    void update(ProductSpec productSpec);

    void delete(Long id);

    List<ProductSpec> findAll();
}
