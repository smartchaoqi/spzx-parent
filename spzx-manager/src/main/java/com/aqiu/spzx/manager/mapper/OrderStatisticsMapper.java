package com.aqiu.spzx.manager.mapper;

import com.aqiu.spzx.model.dto.order.OrderStatisticsDto;
import com.aqiu.spzx.model.entity.order.OrderStatistics;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderStatisticsMapper {
    void save(OrderStatistics orderStatistics);

    List<OrderStatistics> selectList(OrderStatisticsDto orderStatisticsDto);
}
