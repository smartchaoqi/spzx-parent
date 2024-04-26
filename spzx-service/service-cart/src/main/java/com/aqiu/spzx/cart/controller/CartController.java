package com.aqiu.spzx.cart.controller;

import com.aqiu.spzx.cart.service.CartService;
import com.aqiu.spzx.model.entity.h5.CartInfo;
import com.aqiu.spzx.model.vo.common.Result;
import com.aqiu.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/order/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("auth/addToCart/{skuId}/{skuNum}")
    public Result addToCart(@PathVariable("skuId") Long skuId, @PathVariable("skuNum") Integer skuNum) {
        cartService.addToCart(skuId, skuNum);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("auth/cartList")
    public Result cartList() {
        List<CartInfo> cartList = cartService.cartList();
        return Result.build(cartList, ResultCodeEnum.SUCCESS);
    }
}
