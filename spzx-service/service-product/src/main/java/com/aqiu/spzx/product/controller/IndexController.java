package com.aqiu.spzx.product.controller;

import com.aqiu.spzx.model.entity.product.Category;
import com.aqiu.spzx.model.entity.product.ProductSku;
import com.aqiu.spzx.model.vo.common.Result;
import com.aqiu.spzx.model.vo.common.ResultCodeEnum;
import com.aqiu.spzx.model.vo.h5.IndexVo;
import com.aqiu.spzx.product.service.CategoryService;
import com.aqiu.spzx.product.service.ProductService;
import com.aqiu.spzx.product.service.ProductSkuService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "首页接口管理")
@RestController
@RequestMapping(value="/api/product/index")
public class IndexController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public Result index(){
        List<Category> categoryList = categoryService.selectOneCategory();
        List<ProductSku> productSkuList = productService.findProductSkuBySale();
        IndexVo vo=new IndexVo();
        vo.setCategoryList(categoryList);
        vo.setProductSkuList(productSkuList);
        return Result.build(vo, ResultCodeEnum.SUCCESS);
    }
}
