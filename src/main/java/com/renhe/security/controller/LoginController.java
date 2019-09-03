package com.renhe.security.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.renhe.auth.utils.TokenUtil;
import com.renhe.base.Result;
import com.renhe.security.dto.UserDto;
import com.renhe.security.entity.User;
import com.renhe.security.service.UserServcie;
import com.renhe.utils.MD5;
import com.renhe.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
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
    @PostMapping(value="/login")
    public Result<JSONObject> login(@RequestBody UserDto userDto){
        Result<JSONObject> result = new Result<>();
        int rcode = -1;
        try {
            String password = MD5.encode(userDto.getUserName() + userDto.getPassword());
            User user = userServcie.findByUserNameAndPassword(userDto.getUserName(), password);
            if (null != user) {
                JSONObject obj = new JSONObject();
                obj.put("id", user.getId());
                obj.put("name", user.getName());
                JSONObject json = new JSONObject();
                json.put("id", user.getId());
                obj.put("token", TokenUtil.createToken(json.toString(), 3600 * 2 * 1000));
                rcode = 0;
                result.setData(obj);
            } else {
                result.setMessage("用户名或密码错误");
            }
        }catch (Exception e){
            logger.error("[Login]: userDto -> {} ",JSON.toJSONString(userDto),e);
            result.setMessage(e.getMessage());
        }
        result.setCode(rcode);
        logger.info("[login]: userDto -> {}, result -> {}", JSON.toJSONString(userDto),JSON.toJSONString(result));
        return result;
    }


    @PostMapping(value="/logout")
    public Result<String> logout(HttpServletRequest request){
        Result<String> result = new Result<>();
        int rcode = -1;
        String token = null;
        try{
            token = request.getHeader("Authorization");
            if(StringUtil.isPresent(token)){
                String userId = TokenUtil.getUserId(token);
                if(StringUtil.isPresent(userId)) {
                    rcode = 0;
                }else{
                    result.setMessage("token is invalid");
                }
            }else{
                result.setMessage("token parameter can't be null");
            }

        }catch(Exception e){
            logger.error("[Logout]: token -> {}",token,e);
            result.setMessage(e.getMessage());
        }
        result.setCode(rcode);
        logger.info("[logout]: token -> {}, result -> {}", token,JSON.toJSONString(result));
        return result;
    }

}
