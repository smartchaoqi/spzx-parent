package com.aqiu.spzx.order.controller;

import com.aqiu.spzx.model.vo.common.Result;
import com.aqiu.spzx.model.vo.common.ResultCodeEnum;
import com.aqiu.spzx.model.vo.h5.TradeVo;
import com.aqiu.spzx.order.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/api/order/orderInfo")
public class OrderInfoController {
    @Autowired
    private OrderInfoService orderInfoService;

    @GetMapping("auth/trade")
    public Result trade() {
        TradeVo tradeVo = orderInfoService.getTrade();
        return Result.build(tradeVo, ResultCodeEnum.SUCCESS);
    }
}
