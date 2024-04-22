package com.aqiu.spzx.manager.service.impl;

import com.aqiu.spzx.manager.mapper.OrderStatisticsMapper;
import com.aqiu.spzx.manager.service.OrderInfoService;
import com.aqiu.spzx.model.dto.order.OrderStatisticsDto;
import com.aqiu.spzx.model.entity.order.OrderStatistics;
import com.aqiu.spzx.model.vo.order.OrderStatisticsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderInfoServiceImpl implements OrderInfoService {

    @Autowired
    private OrderStatisticsMapper orderStatisticsMapper;
    @Override
    public OrderStatisticsVo getOrderStatisticsData(OrderStatisticsDto orderStatisticsDto) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<OrderStatistics> orderStatisticsList = orderStatisticsMapper.selectList(orderStatisticsDto);
        List<String> dateList=new ArrayList<>();
        List<BigDecimal> amountList=new ArrayList<>();
        for (OrderStatistics orderStatistics : orderStatisticsList) {
            dateList.add(sdf.format(orderStatistics.getOrderDate()));
            amountList.add(orderStatistics.getTotalAmount());
        }
        OrderStatisticsVo orderStatisticsVo = new OrderStatisticsVo();
        orderStatisticsVo.setDateList(dateList);
        orderStatisticsVo.setAmountList(amountList);
        return orderStatisticsVo;
    }
}
