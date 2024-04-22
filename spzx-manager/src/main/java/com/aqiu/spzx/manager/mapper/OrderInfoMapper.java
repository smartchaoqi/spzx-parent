package com.aqiu.spzx.manager.mapper;

import com.aqiu.spzx.model.entity.order.OrderStatistics;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderInfoMapper {
    OrderStatistics selectOrderStatistics(String date);
}
