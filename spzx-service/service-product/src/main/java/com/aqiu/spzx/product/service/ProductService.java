package com.aqiu.spzx.product.service;

import com.aqiu.spzx.model.dto.h5.ProductSkuDto;
import com.aqiu.spzx.model.entity.product.ProductSku;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ProductService {
    List<ProductSku> findProductSkuBySale();

    PageInfo<ProductSku> findByPage(Integer page, Integer limit, ProductSkuDto productSkuDto);
}
