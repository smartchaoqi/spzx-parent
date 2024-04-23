package com.aqiu.spzx.manager.controller;

import com.aqiu.spzx.common.log.annotation.Log;
import com.aqiu.spzx.common.log.enums.OperatorType;
import com.aqiu.spzx.manager.service.BrandService;
import com.aqiu.spzx.model.entity.product.Brand;
import com.aqiu.spzx.model.vo.common.Result;
import com.aqiu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/admin/product/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    //列表查询
    @GetMapping("/{page}/{limit}")
    @Log(title = "品牌管理:列表",businessType = 0,operatorType = OperatorType.MANAGE)
    public Result list(@PathVariable Integer page, @PathVariable Integer limit) {
        PageInfo<Brand> pageInfo = brandService.findByPage(page, limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @PostMapping("/save")
    public Result save(@RequestBody Brand brand) {
        brandService.save(brand);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    @PutMapping("/updateById")
    public Result updateById(@RequestBody Brand brand) {
        brandService.updateById(brand);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Long id) {
        brandService.deleteById(id);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/findAll")
    public Result findAll(){
        List<Brand> list= brandService.findAll();
        return Result.build(list,ResultCodeEnum.SUCCESS);
    }
}
