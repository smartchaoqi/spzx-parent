package com.aqiu.spzx.user.service.impl;

import com.aqiu.spzx.model.entity.user.UserAddress;
import com.aqiu.spzx.user.mapper.UserAddressMapper;
import com.aqiu.spzx.user.service.UserAddressService;
import com.aqiu.spzx.utils.AuthContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAddressServiceImpl implements UserAddressService {

    @Autowired
    private UserAddressMapper userAddressMapper;

    @Override
    public List<UserAddress> findUserAddressList() {
        Long userId = AuthContextUtil.getUserInfo().getId();
        return userAddressMapper.findUserAddressByUserId(userId);
    }

    @Override
    public UserAddress getById(Long id) {
        return userAddressMapper.getById(id);
    }
}
