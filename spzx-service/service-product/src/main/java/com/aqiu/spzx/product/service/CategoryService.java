package com.aqiu.spzx.product.service;

import com.aqiu.spzx.model.entity.product.Category;

import java.util.List;

public interface CategoryService {
    List<Category> selectOneCategory();

    List<Category> findCategoryTree();
}
