package com.aqiu.spzx.cart.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.aqiu.spzx.cart.service.CartService;
import com.aqiu.spzx.feign.product.ProductFeignClient;
import com.aqiu.spzx.model.entity.base.BaseEntity;
import com.aqiu.spzx.model.entity.h5.CartInfo;
import com.aqiu.spzx.model.entity.product.ProductSku;
import com.aqiu.spzx.utils.AuthContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ProductFeignClient productFeignClient;
    @Override
    public void addToCart(Long skuId, Integer skuNum) {
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = getCartKey(userId);
        Object cartInfoObj = redisTemplate.opsForHash().get(cartKey, skuId.toString());
        CartInfo cartInfo=null;
        if (cartInfoObj!=null){
            cartInfo = JSON.parseObject(cartInfoObj.toString(),CartInfo.class);
            cartInfo.setSkuNum(cartInfo.getSkuNum()+skuNum);
            cartInfo.setIsChecked(1);
            cartInfo.setUpdateTime(new Date());
        }else{
            ProductSku productSku = productFeignClient.getBySkuId(skuId);
            cartInfo = new CartInfo();
            cartInfo.setUserId(userId);
            cartInfo.setSkuId(skuId);
            cartInfo.setSkuNum(skuNum);
            cartInfo.setIsChecked(1);
            cartInfo.setCartPrice(productSku.getSalePrice());
            cartInfo.setSkuName(productSku.getSkuName());
            cartInfo.setImgUrl(productSku.getThumbImg());
            cartInfo.setCreateTime(new Date());
            cartInfo.setUpdateTime(new Date());
        }
        redisTemplate.opsForHash().put(cartKey,skuId.toString(),JSON.toJSONString(cartInfo));
    }

    @Override
    public List<CartInfo> cartList() {
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = getCartKey(userId);
        List<Object> values = redisTemplate.opsForHash().values(cartKey);
        if (CollectionUtils.isNotEmpty(values)){
            List<CartInfo> collect = values.stream()
                    .map(o -> JSON.parseObject(o.toString(), CartInfo.class))
                    .sorted((o1, o2) -> o2.getUpdateTime().compareTo(o1.getUpdateTime()))
                    .collect(Collectors.toList());
            return collect;
        }
        return new ArrayList<>();
    }

    @Override
    public void deleteCart(Long skuId) {
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = getCartKey(userId);
        redisTemplate.opsForHash().delete(cartKey,skuId.toString());
    }

    @Override
    public void checkCart(Long skuId, Integer isChecked) {
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = getCartKey(userId);
        Object skuInfoObj = redisTemplate.opsForHash().get(cartKey, skuId.toString());
        if (skuInfoObj!=null){
            CartInfo cartInfo = JSON.parseObject(skuInfoObj.toString(), CartInfo.class);
            cartInfo.setIsChecked(isChecked);
            redisTemplate.opsForHash().put(cartKey,skuId.toString(),JSON.toJSONString(cartInfo));
        }
    }

    private String getCartKey(Long userId) {
        //定义key user:cart:userId
        return "user:cart:" + userId;
    }
}
