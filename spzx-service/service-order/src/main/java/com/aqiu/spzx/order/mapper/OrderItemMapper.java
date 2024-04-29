package com.aqiu.spzx.order.mapper;

import com.aqiu.spzx.model.entity.order.OrderItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderItemMapper {
    void save(OrderItem orderItem);

    List<OrderItem> findOrderItemList(Long orderInfoId);
}
