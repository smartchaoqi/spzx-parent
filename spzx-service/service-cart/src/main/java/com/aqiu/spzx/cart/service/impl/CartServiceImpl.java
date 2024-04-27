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

import java.util.*;
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

    @Override
    public void allCheckCart(Integer isChecked) {
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = getCartKey(userId);
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(cartKey);
        if(entries.size()>0){
            entries.forEach((k,v)->{
                CartInfo cartInfo = JSON.parseObject(v.toString(), CartInfo.class);
                cartInfo.setIsChecked(isChecked);
                redisTemplate.opsForHash().put(cartKey,k.toString(),JSON.toJSONString(cartInfo));
            });
        }
    }

    @Override
    public void clearCart() {
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = getCartKey(userId);
        redisTemplate.delete(cartKey);
    }

    @Override
    public List<CartInfo> getAllChecked() {
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = getCartKey(userId);
        List<Object> values = redisTemplate.opsForHash().values(cartKey);
        if (CollectionUtils.isNotEmpty(values)){
            return values.stream()
                    .map(o -> JSON.parseObject(o.toString(), CartInfo.class))
                    .filter(cartInfo -> cartInfo.getIsChecked() == 1)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public void deleteChecked() {
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = getCartKey(userId);
        List<Object> objectList = redisTemplate.opsForHash().values(cartKey);       // 删除选中的购物项数据
        if(!CollectionUtils.isEmpty(objectList)) {
            objectList.stream().map(cartInfoJSON -> JSON.parseObject(cartInfoJSON.toString(), CartInfo.class))
                    .filter(cartInfo -> cartInfo.getIsChecked() == 1)
                    .forEach(cartInfo -> redisTemplate.opsForHash().delete(cartKey , String.valueOf(cartInfo.getSkuId())));
        }
    }

    private String getCartKey(Long userId) {
        //定义key user:cart:userId
        return "user:cart:" + userId;
    }
}
