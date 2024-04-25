package com.aqiu.spzx.user.mapper;

import com.aqiu.spzx.model.entity.user.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

@Mapper
public interface UserInfoMapper {
    UserInfo findByUsername(@Param("username") String username);

    void save(UserInfo userInfo);
}
