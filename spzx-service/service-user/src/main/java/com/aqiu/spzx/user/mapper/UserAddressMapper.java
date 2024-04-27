package com.aqiu.spzx.user.mapper;

import com.aqiu.spzx.model.entity.user.UserAddress;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserAddressMapper {
    List<UserAddress> findUserAddressByUserId(Long userId);

    UserAddress getById(Long id);
}
