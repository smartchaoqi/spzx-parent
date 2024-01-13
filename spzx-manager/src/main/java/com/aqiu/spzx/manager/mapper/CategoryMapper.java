package com.aqiu.spzx.manager.mapper;

import com.aqiu.spzx.model.entity.product.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    List<Category> findCategoryList(Long id);

    int selectCountByParentId(Long id);
}
