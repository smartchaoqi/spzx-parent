package com.aqiu.spzx.manager.mapper;

import com.aqiu.spzx.model.entity.product.ProductSku;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductSkuMapper {
    void save(ProductSku sku);

    List<ProductSku> findByProductId(Long productId);

    void updateById(ProductSku sku);
}
