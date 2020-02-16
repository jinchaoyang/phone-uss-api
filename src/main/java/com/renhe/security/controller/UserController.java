package com.renhe.security.controller;


import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.renhe.auth.utils.TokenUtil;
import com.renhe.base.Result;
import com.renhe.security.entity.User;
import com.renhe.security.service.UserServcie;
import com.renhe.security.vo.UserVo;
import com.renhe.utils.MD5;
import com.renhe.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@CrossOrigin
@RestController
@RequestMapping(value="/user")
public class UserController  {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserServcie userServcie;

    /**
     * 用户信息列表
     * @param query
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping(value="/list")
    public Result<PageInfo<User>> index(UserVo query, @RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "15") int pageSize){
        Result<PageInfo<User>> result = new Result<>();
        int rcode = -1;
        PageInfo<User> userPageInfo = userServcie.queryPager(query,pageNo,pageSize);
        if(null!=userPageInfo){
            result.setData(userPageInfo);
            rcode =  0 ;
        }else{
            result.setMessage("no record");
        }
        result.setCode(rcode);
        return result;
    }

    /**
     * 创建用户信息
     * @param user
     * @return
     */
    @PostMapping("")
    public Result<Integer> save(@RequestBody User user){
        Result<Integer> result = new Result<>();
        int rcode = -1;
        try{
            userServcie.save(user);
            rcode = 0;
        }catch(Exception e){
            result.setMessage(e.getMessage());
            logger.error("[saveException]: user -> {}", JSON.toJSONString(user),e);
        }
        result.setCode(rcode);
        return result;
    }

    /**
     * 获取用户的基本信息
     * @param id
     * @return
     */
    @GetMapping(value="/{id}")
    public Result<User> findById(@PathVariable String id){
        Result<User> result = new Result<>();
        int rcode = -1;
        try{
            User user = userServcie.findById(id);
            if(null!=user){
                rcode = 0;
                result.setData(user);
                result.setMessage("success");
            }else{
                result.setMessage("user not exists.");
            }
        }catch(Exception e){
            logger.error("[findUserException]: id -> {}",id,e);
        }
        result.setCode(rcode);
        return result;
    }


    /**
     * 更新对象信息
     * @param user
     * @return
     */
    @PutMapping(value="/{id}")
    public Result<Integer> update(@PathVariable("id") String id,@RequestBody User user){
        Result<Integer> result = new Result<>();
        int rcode = -1;
        try{
            User tempUser = userServcie.findById(user.getId());
            if(!tempUser.getPassword().equals(user.getPassword())){
                user.setPassword(MD5.encode(user.getUserName()+user.getPassword()));
            }
            int count = userServcie.update(user);
            if(count>0){
                rcode = 0;
                result.setData(count);
                result.setMessage("success");
            }else{
                result.setMessage("user not exists");
            }
        }catch(Exception e){
            logger.error("[updateException]: user -> {}",JSON.toJSONString(user),e);
            result.setMessage(e.getMessage());
        }
        result.setCode(rcode);
        return result;
    }



    /**
     * 删除用户信息
     * @param id
     * @return
     */
    @DeleteMapping(value="/{id}")
    public Result<Integer> destroy(@PathVariable String id){
        Result<Integer> result = new Result<>();
        int rcode = -1;
        try{
            int count = userServcie.destroy(id);
            if(count>0){
                rcode = 0;
            }else{
                result.setMessage("no record found");
            }

        }catch (Exception e){
            result.setMessage(e.getMessage());
            logger.error("[destroyException]: id -> {}", id,e);
        }
        result.setCode(rcode);
        return result;

    }


    /**
     * 验证用户名信息是否已经存在
     *
     * @return
     */
    @GetMapping(value="/userNameCheck")
    public Result<Boolean> userNameCheck(UserVo vo){
            Result<Boolean> result = new Result<>();
            int rcode = -1;
            try{
                List<User> users = userServcie.queryByParams(vo);
                boolean state = false;
                if(null==users || users.isEmpty()){
                    state = true;
                }else{
                    if(StringUtil.isPresent(vo.getId())){
                       User user = users.get(0);
                       if(vo.getId().equals(user.getId())){
                           state = true;
                       }
                    }
                }
                result.setData(state);
                rcode = 0;

            }catch (Exception e){
                result.setMessage(e.getMessage());
                logger.error("[userNameCheckException]: vo -> {}",JSON.toJSONString(vo));
            }
            result.setCode(rcode);
            return result;
    }

    /**
     * 更新个人信息
     * @return
     */
    @PutMapping(value= "/profile")
    public Result<Integer> updateProfile(@RequestBody User user, HttpServletRequest request){

        Result<Integer> result = new Result<>();
        int rcode = -1;
        try{
            String token = request.getHeader("Authorization");
            if(StringUtil.isPresent(token)) {
                String userId = TokenUtil.getUserId(token);
                user.setId(userId);
                User _user = userServcie.findById(userId);
                if(StringUtil.isPresent(user.getPassword())){
                    user.setPassword(MD5.encode(_user.getUserName()+user.getPassword()));
                }
                int data = userServcie.update(user);
                if(data>0){
                    result.setData(data);
                    rcode = 0;
                }else{
                    result.setMessage("no record found");
                }

            }else{
                result.setMessage("token is empty");
            }


        }catch(Exception e){
            result.setMessage(e.getMessage());
            logger.error("[updateProfileException]: vo -> {}",JSON.toJSONString(user));
        }

        result.setCode(rcode);
        return result;

    }


}
