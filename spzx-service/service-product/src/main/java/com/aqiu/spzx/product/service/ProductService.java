package com.aqiu.spzx.product.service;

import com.aqiu.spzx.model.dto.h5.ProductSkuDto;
import com.aqiu.spzx.model.entity.product.ProductSku;
import com.aqiu.spzx.model.vo.h5.ProductItemVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ProductService {
    List<ProductSku> findProductSkuBySale();

    PageInfo<ProductSku> findByPage(Integer page, Integer limit, ProductSkuDto productSkuDto);

    ProductItemVo item(Long skuId);

    ProductSku getBySkuId(Long skuId);
}
