package com.aqiu.spzx.manager.service;

import com.aqiu.spzx.model.dto.product.CategoryBrandDto;
import com.aqiu.spzx.model.entity.product.Brand;
import com.aqiu.spzx.model.entity.product.CategoryBrand;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface CategoryBrandService {
    PageInfo<CategoryBrand> findByPage(Integer page, Integer limit, CategoryBrandDto dto);

    void save(CategoryBrand categoryBrand);

    void updateById(CategoryBrand categoryBrand);

    void deleteById(Long id);

    List<Brand> findBrandByCategoryId(Long categoryId);
}
