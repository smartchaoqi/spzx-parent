package com.aqiu.spzx.manager.controller;

import com.aqiu.spzx.manager.service.OrderInfoService;
import com.aqiu.spzx.model.dto.order.OrderStatisticsDto;
import com.aqiu.spzx.model.vo.common.Result;
import com.aqiu.spzx.model.vo.common.ResultCodeEnum;
import com.aqiu.spzx.model.vo.order.OrderStatisticsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/admin/order/orderInfo")
public class OrderInfoController {

    @Autowired
    private OrderInfoService orderInfoService ;

    @GetMapping("/getOrderStatisticsData")
    public Result<OrderStatisticsVo> getOrderStatisticsData(OrderStatisticsDto orderStatisticsDto) {
        OrderStatisticsVo vo = orderInfoService.getOrderStatisticsData(orderStatisticsDto);
        return Result.build(vo, ResultCodeEnum.SUCCESS);
    }
}
