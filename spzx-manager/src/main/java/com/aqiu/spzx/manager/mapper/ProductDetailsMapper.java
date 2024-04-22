package com.aqiu.spzx.manager.mapper;

import com.aqiu.spzx.model.entity.product.ProductDetails;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductDetailsMapper {
    void save(ProductDetails details);

    ProductDetails getByProductId(Long productId);

    void updateById(ProductDetails details);

    void deleteByProductId(Long id);
}
