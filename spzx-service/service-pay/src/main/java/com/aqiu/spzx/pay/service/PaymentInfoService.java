package com.aqiu.spzx.pay.service;

import com.aqiu.spzx.model.entity.pay.PaymentInfo;

import java.util.Map;

public interface PaymentInfoService {
    PaymentInfo savePaymentInfo(String orderNo);

    void updatePaymentStatus(Map<String, String> paramMap, int payType);
}
