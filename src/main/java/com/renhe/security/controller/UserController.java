package com.renhe.security.controller;

import com.github.pagehelper.PageInfo;
import com.renhe.base.Result;
import com.renhe.security.entity.User;
import com.renhe.base.PagerRS;
import com.renhe.security.service.UserServcie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @GetMapping(value="")
    public Result<PageInfo<User>> index(Map<String,Object> params, @RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "15") int pageSize){
        Result<PageInfo<User>> result = new Result<>();
        int rcode = -1;
        PageInfo<User> userPageInfo = userServcie.queryPager(params,pageNo,pageSize);
        if(null!=userPageInfo){
            result.setData(userPageInfo);
            rcode =  0 ;
        }else{
            result.setMessage("no record");
        }
        result.setCode(rcode);
        return result;
    }

}
