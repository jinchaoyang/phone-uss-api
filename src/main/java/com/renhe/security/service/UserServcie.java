package com.renhe.security.service;

import com.renhe.security.entity.User;
import com.renhe.security.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServcie  {

    @Autowired
    UserMapper userMapper;

    public User findByUserNameAndPassword(String userName, String password){
        return userMapper.findByUserNameAndPassword(userName,password);
    }




}
