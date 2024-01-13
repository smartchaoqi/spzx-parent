package com.aqiu.spzx.manager.service.impl;

import com.aqiu.spzx.manager.mapper.CategoryMapper;
import com.aqiu.spzx.manager.service.CategoryService;
import com.aqiu.spzx.model.entity.product.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> findCategoryList(Long id) {
        List<Category> list = categoryMapper.findCategoryList(id);
        if (!CollectionUtils.isEmpty(list)){
            for (Category category : list) {
                int count = categoryMapper.selectCountByParentId(category.getId());
                if (count>0) {
                    category.setHasChildren(true);
                }
            }
        }
        return list;
    }
}
