package com.aqiu.spzx.manager.controller;

import com.aqiu.spzx.manager.service.CategoryService;
import com.aqiu.spzx.model.entity.product.Category;
import com.aqiu.spzx.model.vo.common.Result;
import com.aqiu.spzx.model.vo.common.ResultCodeEnum;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping(value = "/admin/product/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/findCategoryList/{id}")
    public Result findCategoryList(@PathVariable Long id){
        List<Category> list = categoryService.findCategoryList(id);
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/exportData")
    public void exportData(HttpServletResponse response) throws IOException {
        categoryService.exportData(response);
    }
}
