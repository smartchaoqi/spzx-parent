package com.aqiu.spzx.cart.service;

import com.aqiu.spzx.model.entity.h5.CartInfo;

import java.util.List;

public interface CartService {
    void addToCart(Long skuId, Integer skuNum);

    List<CartInfo> cartList();
}
