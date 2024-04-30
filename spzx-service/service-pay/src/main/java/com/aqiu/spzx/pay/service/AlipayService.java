package com.aqiu.spzx.pay.service;

import com.alipay.api.AlipayApiException;

public interface AlipayService {
    String submitAlipay(String orderNo) throws AlipayApiException;
}
