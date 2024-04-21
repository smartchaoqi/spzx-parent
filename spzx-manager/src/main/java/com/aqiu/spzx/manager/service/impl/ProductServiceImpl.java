package com.aqiu.spzx.manager.service.impl;

import com.aqiu.spzx.manager.mapper.ProductMapper;
import com.aqiu.spzx.manager.service.ProductService;
import com.aqiu.spzx.model.dto.product.ProductDto;
import com.aqiu.spzx.model.entity.product.Product;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;

    @Override
    public PageInfo<Product> list(Integer page, Integer limit, ProductDto productDto) {
        PageHelper.startPage(page, limit);
        List<Product> list=productMapper.findByPage(productDto);
        return new PageInfo<>(list);
    }
}
