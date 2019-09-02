package com.renhe.security.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.renhe.base.Pager;
import com.renhe.base.PagerRS;
import com.renhe.security.entity.User;
import com.renhe.security.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServcie  {

    @Autowired
    UserMapper userMapper;

    /**
     * 根据用户名和密码查询用户信息
     * @param userName
     * @param password
     * @return
     */
    public User findByUserNameAndPassword(String userName, String password){
        Map<String,Object> params = new HashMap<>();
        params.put("userName",userName);
        params.put("password",password);
        return userMapper.findByUserNameAndPassword(params);
    }


    /**
     * 分页查询用户信息
     * @param params
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageInfo<User> queryPager(Map<String,Object> params,int pageNo,int pageSize){
        PageHelper.startPage(pageNo,pageSize);
        List<User> userList = userMapper.queryByParams(params);
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        return pageInfo;

    }



}
