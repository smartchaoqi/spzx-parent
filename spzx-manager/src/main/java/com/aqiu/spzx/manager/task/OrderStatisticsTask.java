package com.aqiu.spzx.manager.task;

import com.aqiu.spzx.manager.mapper.OrderInfoMapper;
import com.aqiu.spzx.manager.mapper.OrderStatisticsMapper;
import com.aqiu.spzx.model.entity.order.OrderStatistics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Slf4j
public class OrderStatisticsTask {

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private OrderStatisticsMapper orderStatisticsMapper;

    @Scheduled(cron = "0 0 2 * * ?")
    public void orderTotalAmountStatistics() {
        Date date = new Date(new Date().getTime() - 24 * 60 * 60 * 1000);
        String dateFormat = new SimpleDateFormat("yyyy-MM-dd").format(date);
        OrderStatistics orderStatistics = orderInfoMapper.selectOrderStatistics(dateFormat);
        if (orderStatistics != null) {
            orderStatisticsMapper.save(orderStatistics);
        }
    }
}
