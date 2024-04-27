package com.aqiu.spzx.order.service.impl;

import com.aqiu.spzx.common.exception.GuiguException;
import com.aqiu.spzx.feign.cart.CartFeignClient;
import com.aqiu.spzx.feign.product.ProductFeignClient;
import com.aqiu.spzx.feign.user.UserFeignClient;
import com.aqiu.spzx.model.dto.h5.OrderInfoDto;
import com.aqiu.spzx.model.entity.h5.CartInfo;
import com.aqiu.spzx.model.entity.order.OrderInfo;
import com.aqiu.spzx.model.entity.order.OrderItem;
import com.aqiu.spzx.model.entity.order.OrderLog;
import com.aqiu.spzx.model.entity.product.ProductSku;
import com.aqiu.spzx.model.entity.user.UserAddress;
import com.aqiu.spzx.model.entity.user.UserInfo;
import com.aqiu.spzx.model.vo.common.ResultCodeEnum;
import com.aqiu.spzx.model.vo.h5.TradeVo;
import com.aqiu.spzx.order.mapper.OrderInfoMapper;
import com.aqiu.spzx.order.mapper.OrderItemMapper;
import com.aqiu.spzx.order.mapper.OrderLogMapper;
import com.aqiu.spzx.order.service.OrderInfoService;
import com.aqiu.spzx.utils.AuthContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderInfoServiceImpl implements OrderInfoService {
    @Autowired
    private ProductFeignClient productFeignClient;
    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private CartFeignClient cartFeignClient;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private OrderLogMapper orderLogMapper;
    @Autowired
    private UserFeignClient userFeignClient;

    @Override
    public TradeVo getTrade() {
        List<CartInfo> cartInfoList = cartFeignClient.getAllChecked();
        List<OrderItem> orderItemList = new ArrayList<>();
        for (CartInfo cartInfo : cartInfoList) {
            OrderItem orderItem = new OrderItem();
            orderItem.setSkuId(cartInfo.getSkuId());
            orderItem.setSkuName(cartInfo.getSkuName());
            orderItem.setThumbImg(cartInfo.getImgUrl());
            orderItem.setSkuPrice(cartInfo.getCartPrice());
            orderItem.setSkuNum(cartInfo.getSkuNum());
            orderItemList.add(orderItem);
        }
        BigDecimal totalPrice = new BigDecimal(0);
        for (OrderItem orderItem : orderItemList) {
            totalPrice = totalPrice.add(orderItem.getSkuPrice().multiply(new BigDecimal(orderItem.getSkuNum())));
        }
        TradeVo tradeVo=new TradeVo();
        tradeVo.setOrderItemList(orderItemList);
        tradeVo.setTotalAmount(totalPrice);
        return tradeVo;
    }

    @Override
    public Long submitOrder(OrderInfoDto orderInfoDto) {
        List<OrderItem> orderItemList = orderInfoDto.getOrderItemList();
        if (CollectionUtils.isEmpty(orderItemList)){
            throw new GuiguException(ResultCodeEnum.DATA_ERROR);
        }
//        校验库存
        for (OrderItem orderItem : orderItemList) {
            Long skuId = orderItem.getSkuId();
            ProductSku productSku = productFeignClient.getBySkuId(skuId);
            if (productSku==null){
                throw new GuiguException(ResultCodeEnum.DATA_ERROR);
            }
            if (productSku.getStockNum() < orderItem.getSkuNum()){
                throw new GuiguException(ResultCodeEnum.STOCK_LESS);
            }
        }
//        封装orderInfo
        OrderInfo orderInfo=new OrderInfo();
        UserInfo userInfo = AuthContextUtil.getUserInfo();
        //订单编号
        orderInfo.setOrderNo(String.valueOf(System.currentTimeMillis()));
        //用户id
        orderInfo.setUserId(userInfo.getId());
        //用户昵称
        orderInfo.setNickName(userInfo.getNickName());
//        收获地址
        Long userAddressId = orderInfoDto.getUserAddressId();
        UserAddress userAddress=userFeignClient.getUserAddress(userAddressId);
        orderInfo.setReceiverName(userAddress.getName());
        orderInfo.setReceiverPhone(userAddress.getPhone());
        orderInfo.setReceiverTagName(userAddress.getTagName());
        orderInfo.setReceiverProvince(userAddress.getProvinceCode());
        orderInfo.setReceiverCity(userAddress.getCityCode());
        orderInfo.setReceiverDistrict(userAddress.getDistrictCode());
        orderInfo.setReceiverAddress(userAddress.getFullAddress());
        BigDecimal totalAmount = new BigDecimal(0);
        for (OrderItem orderItem : orderItemList) {
            totalAmount = totalAmount.add(orderItem.getSkuPrice().multiply(new BigDecimal(orderItem.getSkuNum())));
        }
        orderInfo.setTotalAmount(totalAmount);
        orderInfo.setCouponAmount(new BigDecimal(0));
        orderInfo.setOriginalTotalAmount(totalAmount);
        orderInfo.setFeightFee(orderInfoDto.getFeightFee());
        orderInfo.setPayType(2);
        orderInfo.setOrderStatus(0);
        orderInfoMapper.save(orderInfo);

        for (OrderItem orderItem : orderItemList) {
            orderItem.setOrderId(orderInfo.getId());
            orderItemMapper.save(orderItem);
        }

        //记录日志
        OrderLog orderLog = new OrderLog();
        orderLog.setOrderId(orderInfo.getId());
        orderLog.setProcessStatus(0);
        orderLog.setNote("提交订单");
        orderLogMapper.save(orderLog);

        //删除购物车
        cartFeignClient.deleteChecked();
        return orderInfo.getId();
    }
}
