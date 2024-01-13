package com.aqiu.spzx.manager.service;

import com.aqiu.spzx.model.entity.product.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findCategoryList(Long id);
}
