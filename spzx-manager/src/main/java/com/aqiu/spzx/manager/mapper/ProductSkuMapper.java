package com.aqiu.spzx.manager.mapper;

import com.aqiu.spzx.model.entity.product.ProductSku;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductSkuMapper {
    void save(ProductSku sku);
}