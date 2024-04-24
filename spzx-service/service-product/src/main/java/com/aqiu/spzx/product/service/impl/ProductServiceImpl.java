package com.aqiu.spzx.product.service.impl;

import com.aqiu.spzx.model.entity.product.ProductSku;
import com.aqiu.spzx.product.mapper.ProductSkuMapper;
import com.aqiu.spzx.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductSkuMapper productSkuMapper;
    @Override
    public List<ProductSku> findProductSkuBySale() {
        return productSkuMapper.findProductSkuBySale();
    }
}
