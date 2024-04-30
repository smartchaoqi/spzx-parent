package com.aqiu.spzx.pay.service.impl;

import com.alibaba.fastjson.JSON;
import com.aqiu.spzx.feign.order.OrderFeignClient;
import com.aqiu.spzx.model.entity.order.OrderInfo;
import com.aqiu.spzx.model.entity.order.OrderItem;
import com.aqiu.spzx.model.entity.pay.PaymentInfo;
import com.aqiu.spzx.pay.mapper.PaymentInfoMapper;
import com.aqiu.spzx.pay.service.PaymentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class PaymentInfoServiceImpl implements PaymentInfoService {
    @Autowired
    private PaymentInfoMapper paymentInfoMapper;

    @Autowired
    private OrderFeignClient orderFeignClient;

    @Override
    public PaymentInfo savePaymentInfo(String orderNo) {
        PaymentInfo paymentInfo = paymentInfoMapper.getByOrderNo(orderNo);
        if (paymentInfo==null){
            OrderInfo orderInfo=orderFeignClient.getOrderInfoByOrderNo(orderNo);
            paymentInfo=new PaymentInfo();
            paymentInfo.setUserId(orderInfo.getUserId());
            paymentInfo.setPayType(orderInfo.getPayType());
            StringBuilder content = new StringBuilder();
            for(OrderItem item : orderInfo.getOrderItemList()) {
                content.append(item.getSkuName()).append(" ");
            }
            paymentInfo.setContent(content.toString());
            paymentInfo.setAmount(orderInfo.getTotalAmount());
            paymentInfo.setOrderNo(orderNo);
            paymentInfo.setPaymentStatus(0);
            paymentInfoMapper.save(paymentInfo);
        }
        return paymentInfo;
    }

    @Override
    public void updatePaymentStatus(Map<String, String> map, int payType) {
        PaymentInfo paymentInfo = paymentInfoMapper.getByOrderNo(map.get("out_trade_no"));
        if (paymentInfo.getPaymentStatus()==1){
            return;
        }
        paymentInfo.setPaymentStatus(1);
        paymentInfo.setOutTradeNo(map.get("trade_no"));
        paymentInfo.setCallbackTime(new Date());
        paymentInfo.setCallbackContent(JSON.toJSONString(map));
        paymentInfoMapper.updateById(paymentInfo);

        //TODO 更新订单状态,库存
    }
}
