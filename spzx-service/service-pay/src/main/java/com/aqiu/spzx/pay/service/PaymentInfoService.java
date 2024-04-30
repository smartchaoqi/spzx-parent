package com.aqiu.spzx.pay.service;

import com.aqiu.spzx.model.entity.pay.PaymentInfo;

public interface PaymentInfoService {
    PaymentInfo savePaymentInfo(String orderNo);
}
