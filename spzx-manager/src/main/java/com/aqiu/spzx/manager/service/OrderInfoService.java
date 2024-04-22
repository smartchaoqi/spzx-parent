package com.aqiu.spzx.manager.service;

import com.aqiu.spzx.model.dto.order.OrderStatisticsDto;
import com.aqiu.spzx.model.vo.order.OrderStatisticsVo;

public interface OrderInfoService {
    OrderStatisticsVo getOrderStatisticsData(OrderStatisticsDto orderStatisticsDto);
}
