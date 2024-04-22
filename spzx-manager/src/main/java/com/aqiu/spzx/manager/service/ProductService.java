package com.aqiu.spzx.manager.service;

import com.aqiu.spzx.model.dto.product.ProductDto;
import com.aqiu.spzx.model.entity.product.Product;
import com.github.pagehelper.PageInfo;

public interface ProductService {
    PageInfo<Product> list(Integer page, Integer limit, ProductDto productDto);

    void save(Product product);

    Product getById(Long id);

    void updateById(Product product);
}
