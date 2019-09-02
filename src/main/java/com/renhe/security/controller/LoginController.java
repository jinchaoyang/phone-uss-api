package com.renhe.security.controller;

import com.alibaba.fastjson.JSONObject;
import com.renhe.auth.utils.TokenUtil;
import com.renhe.base.Result;
import com.renhe.security.dto.UserDto;
import com.renhe.security.entity.User;
import com.renhe.security.service.UserServcie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/auth")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UserServcie userServcie;
    /**
     * 用户登录
     * @return
     */
    public Result<JSONObject> login(UserDto userDto){
        Result<JSONObject> result = new Result<>();
        int rcode = -1;

        User user = userServcie.findByUserNameAndPassword(userDto.getUserName(),userDto.getPassword());
        if(null!=user){
            JSONObject obj = new JSONObject();
            obj.put("id",user.getId());
            obj.put("name",user.getName());
            JSONObject json = new JSONObject();
            json.put("id",user.getId());
            obj.put("token", TokenUtil.createToken(json.toString(),3600*2*1000));
        }else{
            result.setMessage("用户名或密码错误");
        }
        result.setCode(rcode);
        return result;
    }

}
