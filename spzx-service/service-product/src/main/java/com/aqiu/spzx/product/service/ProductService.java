package com.aqiu.spzx.product.service;

import com.aqiu.spzx.model.entity.product.ProductSku;

import java.util.List;

public interface ProductService {
    List<ProductSku> findProductSkuBySale();
}
