package com.aqiu.spzx.product.mapper;

import com.aqiu.spzx.model.entity.product.Product;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper {
    Product findById(Long productId);
}
