package com.aqiu.spzx.manager.controller;

import com.aqiu.spzx.manager.mapper.ProductDetailsMapper;
import com.aqiu.spzx.manager.mapper.ProductSkuMapper;
import com.aqiu.spzx.manager.service.ProductService;
import com.aqiu.spzx.model.dto.product.ProductDto;
import com.aqiu.spzx.model.entity.product.Product;
import com.aqiu.spzx.model.vo.common.Result;
import com.aqiu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/save")
    public Result save(@RequestBody Product product) {
        productService.save(product);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
}
