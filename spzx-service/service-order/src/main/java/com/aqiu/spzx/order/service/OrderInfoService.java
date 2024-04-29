package com.aqiu.spzx.order.service;

import com.aqiu.spzx.model.dto.h5.OrderInfoDto;
import com.aqiu.spzx.model.entity.order.OrderInfo;
import com.aqiu.spzx.model.vo.h5.TradeVo;

public interface OrderInfoService {
    TradeVo getTrade();

    Long submitOrder(OrderInfoDto orderInfoDto);

    OrderInfo getOrderInfoById(Long orderId);
}
