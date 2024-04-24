package com.aqiu.spzx.product.mapper;

import com.aqiu.spzx.model.entity.product.ProductSku;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductSkuMapper {
    List<ProductSku> findProductSkuBySale();
}