package com.aqiu.spzx.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.aqiu.spzx.model.dto.h5.ProductSkuDto;
import com.aqiu.spzx.model.entity.product.Product;
import com.aqiu.spzx.model.entity.product.ProductDetails;
import com.aqiu.spzx.model.entity.product.ProductSku;
import com.aqiu.spzx.model.vo.h5.ProductItemVo;
import com.aqiu.spzx.product.mapper.ProductDetailsMapper;
import com.aqiu.spzx.product.mapper.ProductMapper;
import com.aqiu.spzx.product.mapper.ProductSkuMapper;
import com.aqiu.spzx.product.service.ProductService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductSkuMapper productSkuMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductDetailsMapper productDetailsMapper;

    @Override
    public List<ProductSku> findProductSkuBySale() {
        return productSkuMapper.findProductSkuBySale();
    }

    @Override
    public PageInfo<ProductSku> findByPage(Integer page, Integer limit, ProductSkuDto productSkuDto) {
        PageHelper.startPage(page,limit);
        List<ProductSku> productSkuList = productSkuMapper.findByPage(productSkuDto);
        return new PageInfo<>(productSkuList);
    }

    @Override
    public ProductItemVo item(Long skuId) {
        ProductSku productSku = productSkuMapper.findById(skuId);
        Product product=productMapper.findById(productSku.getProductId());
        ProductDetails productDetails=productDetailsMapper.findByProductId(productSku.getProductId());
        List<ProductSku> productSkuList = productSkuMapper.findByProductId(productSku.getProductId());
        Map<String,Object> skuSpecValueMap=new HashMap<>();
        for (ProductSku sku : productSkuList) {
            skuSpecValueMap.put(sku.getSkuSpec(),sku.getId());
        }

        ProductItemVo vo=new ProductItemVo();
        vo.setProduct(product);
        vo.setProductSku(productSku);
        String[] sliderUrlsSplit = product.getSliderUrls().split(",");
        vo.setSliderUrlList(Arrays.asList(sliderUrlsSplit));
        String[] detailsImageUrlsSplit = productDetails.getImageUrls().split(",");
        vo.setDetailsImageUrlList(Arrays.asList(detailsImageUrlsSplit));
        String specValue = product.getSpecValue();
        vo.setSpecValueList(JSON.parseArray(specValue));
        vo.setSkuSpecValueMap(skuSpecValueMap);
        return vo;
    }

    @Override
    public ProductSku getBySkuId(Long skuId) {
        return productSkuMapper.findById(skuId);
    }
}
