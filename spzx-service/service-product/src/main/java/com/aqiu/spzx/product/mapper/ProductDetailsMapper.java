package com.aqiu.spzx.product.mapper;

import com.aqiu.spzx.model.entity.product.ProductDetails;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductDetailsMapper {
    ProductDetails findByProductId(Long productId);
}
