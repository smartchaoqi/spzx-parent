package com.aqiu.spzx.manager.mapper;

import com.aqiu.spzx.model.entity.order.OrderStatistics;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderStatisticsMapper {
    void save(OrderStatistics orderStatistics);
}
