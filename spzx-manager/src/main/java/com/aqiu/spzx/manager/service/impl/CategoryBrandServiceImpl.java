package com.aqiu.spzx.manager.service.impl;

import com.aqiu.spzx.manager.mapper.CategoryBrandMapper;
import com.aqiu.spzx.manager.service.CategoryBrandService;
import com.aqiu.spzx.model.dto.product.CategoryBrandDto;
import com.aqiu.spzx.model.entity.product.Brand;
import com.aqiu.spzx.model.entity.product.CategoryBrand;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryBrandServiceImpl implements CategoryBrandService {
    @Autowired
    private CategoryBrandMapper categoryBrandMapper;

    @Override
    public PageInfo<CategoryBrand> findByPage(Integer page, Integer limit, CategoryBrandDto dto) {
        PageHelper.startPage(page,limit);
        List<CategoryBrand> data=categoryBrandMapper.findByPage(dto);
        PageInfo<CategoryBrand> pageInfo = new PageInfo<>(data);
        return pageInfo;
    }

    @Override
    public void save(CategoryBrand categoryBrand) {
        categoryBrandMapper.save(categoryBrand);
    }

    @Override
    public void updateById(CategoryBrand categoryBrand) {
        categoryBrandMapper.updateById(categoryBrand);
    }

    @Override
    public void deleteById(Long id) {
        categoryBrandMapper.deleteById(id);
    }

    @Override
    public List<Brand> findBrandByCategoryId(Long categoryId) {
        return categoryBrandMapper.findBrandByCategoryId(categoryId);
    }
}
