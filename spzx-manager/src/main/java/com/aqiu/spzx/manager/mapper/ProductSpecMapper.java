package com.aqiu.spzx.manager.mapper;

import com.aqiu.spzx.model.entity.product.ProductSpec;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductSpecMapper {
    List<ProductSpec> findAll();

    void save(ProductSpec productSpec);

    void update(ProductSpec productSpec);

    void delete(Long id);
}
