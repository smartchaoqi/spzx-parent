package com.aqiu.spzx.product.service.impl;

import com.aqiu.spzx.model.entity.product.Category;
import com.aqiu.spzx.product.mapper.CategoryMapper;
import com.aqiu.spzx.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> selectOneCategory() {
        return categoryMapper.selectOneCategory();
    }

    @Override
    public List<Category> findCategoryTree() {
        List<Category> allData = categoryMapper.findAll();
        List<Category> oneCategory = allData.stream().filter(category -> category.getParentId() == 0).collect(Collectors.toList());
        if (oneCategory.size() > 0) {
            oneCategory.forEach(one -> {
                List<Category> two = findChildren(allData, one.getId());
                if(two!=null&&two.size()>0){
                    one.setHasChildren(true);
                    one.setChildren(two);
                    two.forEach(twoCategory -> {
                        List<Category> three = findChildren(allData, twoCategory.getId());
                        if(three!=null&&three.size()>0){
                            twoCategory.setHasChildren(true);
                            twoCategory.setChildren(three);
                        }else{
                            twoCategory.setHasChildren(false);
                        }
                    });
                }else{
                    one.setHasChildren(false);
                }
            });
        }
        return oneCategory;
    }

    private List<Category> findChildren(List<Category> allData,long parentId){
        return allData.stream().filter(category -> category.getParentId() == parentId).collect(Collectors.toList());
    }
}
