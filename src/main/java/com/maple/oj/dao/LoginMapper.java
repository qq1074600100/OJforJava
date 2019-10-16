package com.maple.oj.dao;

import com.maple.oj.beans.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginMapper {
    void insertUser(User user);

    User selectUserByName(String username);
}
