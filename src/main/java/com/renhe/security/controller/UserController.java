package com.renhe.security.controller;

import com.renhe.base.Result;
import com.renhe.security.entity.User;
import com.renhe.base.PagerRS;
import com.renhe.security.service.UserServcie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value="/user")
public class UserController  {

    @Autowired
    UserServcie userServcie;

    /**
     * 用户信息列表
     * @param params
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Result<PagerRS<User>> index(Map<String,Object> params, int pageNo, int pageSize){
        return null;
    }

}
