package com.aqiu.spzx.order.mapper;

import com.aqiu.spzx.model.entity.order.OrderItem;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderItemMapper {
    void save(OrderItem orderItem);
}
