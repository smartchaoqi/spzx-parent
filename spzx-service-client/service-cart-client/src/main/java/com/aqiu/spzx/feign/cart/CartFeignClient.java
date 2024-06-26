package com.aqiu.spzx.feign.cart;

import com.aqiu.spzx.model.entity.h5.CartInfo;
import com.aqiu.spzx.model.vo.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "service-cart")
public interface CartFeignClient {
    @GetMapping(value = "/api/order/cart/auth/getAllChecked")
    public List<CartInfo> getAllChecked();

    @GetMapping(value = "/api/order/cart/auth/deleteChecked")
    Result deleteChecked() ;
}
