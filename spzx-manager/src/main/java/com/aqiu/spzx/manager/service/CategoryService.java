package com.aqiu.spzx.manager.service;

import com.aqiu.spzx.model.entity.product.Category;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface CategoryService {
    List<Category> findCategoryList(Long id);

    void exportData(HttpServletResponse response) throws IOException;
}
