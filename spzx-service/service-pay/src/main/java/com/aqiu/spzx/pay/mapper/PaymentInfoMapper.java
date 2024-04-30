package com.aqiu.spzx.pay.mapper;

import com.aqiu.spzx.model.entity.pay.PaymentInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentInfoMapper {
    PaymentInfo getByOrderNo(String orderNo);

    void save(PaymentInfo paymentInfo);
}
