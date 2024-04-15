package com.aqiu.spzx.manager.service.impl;

import com.alibaba.excel.EasyExcel;
import com.aqiu.spzx.manager.mapper.CategoryMapper;
import com.aqiu.spzx.manager.service.CategoryService;
import com.aqiu.spzx.model.entity.product.Category;
import com.aqiu.spzx.model.vo.product.CategoryExcelVo;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
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

    @Override
    public void exportData(HttpServletResponse response) throws IOException {
        // 设置响应结果类型
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");

        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("分类数据", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        //response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        List<Category> data = categoryMapper.findAll();
        List<CategoryExcelVo> excelData = new ArrayList<>();
        for (Category vo : data) {
            CategoryExcelVo excelVo = new CategoryExcelVo();
            BeanUtils.copyProperties(vo,excelVo);
            excelData.add(excelVo);
        }

        EasyExcel.write(response.getOutputStream(), CategoryExcelVo.class)
                .sheet("分类数据").doWrite(excelData);
    }
}
