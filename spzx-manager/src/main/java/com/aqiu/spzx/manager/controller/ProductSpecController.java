package com.aqiu.spzx.manager.controller;

import com.aqiu.spzx.manager.mapper.ProductSpecMapper;
import com.aqiu.spzx.manager.service.ProductSpecService;
import com.aqiu.spzx.model.entity.product.ProductSpec;
import com.aqiu.spzx.model.vo.common.Result;
import com.aqiu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/admin/product/productSpec")
public class ProductSpecController {
    @Autowired
    private ProductSpecService productSpecService;

    @GetMapping("/{page}/{limit}")
    public Result list(@PathVariable("page") Integer page, @PathVariable("limit") Integer limit) {
        PageInfo<ProductSpec> list=productSpecService.list(page, limit);
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }

    @PostMapping("/save")
    public Result save(@RequestBody ProductSpec productSpec) {
        productSpecService.save(productSpec);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    @PutMapping("/update")
    public Result update(@RequestBody ProductSpec productSpec) {
        productSpecService.update(productSpec);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Long id) {
        productSpecService.delete(id);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    @GetMapping("findAll")
    public Result findAll() {
        List<ProductSpec> list = productSpecService.findAll();
        return Result.build(list , ResultCodeEnum.SUCCESS) ;
    }
}
