package com.aqiu.spzx.order.controller;

import com.aqiu.spzx.model.dto.h5.OrderInfoDto;
import com.aqiu.spzx.model.entity.order.OrderInfo;
import com.aqiu.spzx.model.vo.common.Result;
import com.aqiu.spzx.model.vo.common.ResultCodeEnum;
import com.aqiu.spzx.model.vo.h5.TradeVo;
import com.aqiu.spzx.order.service.OrderInfoService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("auth/submitOrder")
    public Result submitOrder(@RequestBody OrderInfoDto orderInfoDto) {
        Long orderId = orderInfoService.submitOrder(orderInfoDto);
        return Result.build(orderId, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("auth/{orderId}")
    public Result getOrderInfo(@PathVariable Long orderId) {
        OrderInfo orderInfo = orderInfoService.getOrderInfoById(orderId);
        return Result.build(orderInfo, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("auth/buy/{skuId}")
    public Result buy(@PathVariable Long skuId) {
        TradeVo tradeVo = orderInfoService.getTradeBySkuId(skuId);
        return Result.build(tradeVo, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("auth/{page}/{limit}")
    public Result list(
            @PathVariable Integer page,
            @PathVariable Integer limit,
            @RequestParam(required = false, defaultValue = "") Integer orderStatus) {
        PageInfo<OrderInfo> pageInfo = orderInfoService.findUserPage(page, limit, orderStatus);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }
}
