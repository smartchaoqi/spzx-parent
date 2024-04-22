package com.aqiu.spzx.manager.service.impl;

import com.aqiu.spzx.manager.mapper.ProductDetailsMapper;
import com.aqiu.spzx.manager.mapper.ProductMapper;
import com.aqiu.spzx.manager.mapper.ProductSkuMapper;
import com.aqiu.spzx.manager.service.ProductService;
import com.aqiu.spzx.model.dto.product.ProductDto;
import com.aqiu.spzx.model.entity.product.Product;
import com.aqiu.spzx.model.entity.product.ProductDetails;
import com.aqiu.spzx.model.entity.product.ProductSku;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductSkuMapper productSkuMapper;
    @Autowired
    private ProductDetailsMapper productDetailsMapper;

    @Override
    public PageInfo<Product> list(Integer page, Integer limit, ProductDto productDto) {
        PageHelper.startPage(page, limit);
        List<Product> list=productMapper.findByPage(productDto);
        return new PageInfo<>(list);
    }

    @Override
    @Transactional
    public void save(Product product) {
        // 保存商品数据
        product.setStatus(0);              // 设置上架状态为0
        product.setAuditStatus(0);         // 设置审核状态为0
        productMapper.save(product);

        List<ProductSku> productSkuList = product.getProductSkuList();
        for(int i=0;i<productSkuList.size();i++){
            ProductSku sku = productSkuList.get(i);
            sku.setProductId(product.getId());
            sku.setSkuCode(product.getId()+"_"+i);
            sku.setSkuName(product.getName() + " " + sku.getSkuSpec());
            sku.setSaleNum(0);                               // 设置销量
            sku.setStatus(0);
            productSkuMapper.save(sku);
        }

        ProductDetails details = new ProductDetails();
        details.setProductId(product.getId());
        details.setImageUrls(product.getDetailsImageUrls());
        productDetailsMapper.save(details);
    }

    @Override
    public Product getById(Long id) {
        Product product = productMapper.getById(id);

        List<ProductSku> productSkuList = productSkuMapper.findByProductId(id);
        product.setProductSkuList(productSkuList);

        ProductDetails productDetails = productDetailsMapper.getByProductId(id);
        product.setDetailsImageUrls(productDetails.getImageUrls());
        return product;
    }

    @Override
    @Transactional
    public void updateById(Product product) {
        //修改product
        productMapper.updateById(product);
        //修改productSku
        List<ProductSku> productSkuList = product.getProductSkuList();
        for(ProductSku sku:productSkuList){
            productSkuMapper.updateById(sku);
        }
        //修改productDetails
        ProductDetails details = productDetailsMapper.getByProductId(product.getId());
        details.setImageUrls(product.getDetailsImageUrls());
        productDetailsMapper.updateById(details);
    }
}
