package com.aqiu.spzx.manager.controller;

import com.aqiu.spzx.manager.service.ProductService;
import com.aqiu.spzx.model.dto.product.ProductDto;
import com.aqiu.spzx.model.entity.product.Product;
import com.aqiu.spzx.model.vo.common.Result;
import com.aqiu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/admin/product/product")
public class ProductController {
    @Autowired
    private ProductService productService ;

    @GetMapping(value = "/{page}/{limit}")
    public Result list(@PathVariable Integer page, @PathVariable Integer limit, ProductDto productDto) {
        PageInfo<Product> list = productService.list(page, limit, productDto);
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }
}
