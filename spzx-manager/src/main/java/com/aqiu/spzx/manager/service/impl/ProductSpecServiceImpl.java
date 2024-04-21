package com.aqiu.spzx.manager.service.impl;

import com.aqiu.spzx.manager.mapper.ProductSpecMapper;
import com.aqiu.spzx.manager.service.ProductSpecService;
import com.aqiu.spzx.model.entity.product.ProductSpec;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductSpecServiceImpl implements ProductSpecService {

    @Autowired
    private ProductSpecMapper productSpecMapper;

    @Override
    public PageInfo<ProductSpec> list(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<ProductSpec> list=productSpecMapper.findAll();
        return new PageInfo<>(list);
    }

    @Override
    public void save(ProductSpec productSpec) {
        productSpecMapper.save(productSpec);
    }

    @Override
    public void update(ProductSpec productSpec) {
        productSpecMapper.update(productSpec);
    }

    @Override
    public void delete(Long id) {
        productSpecMapper.delete(id);
    }

    @Override
    public List<ProductSpec> findAll() {
        return productSpecMapper.findAll();
    }
}
