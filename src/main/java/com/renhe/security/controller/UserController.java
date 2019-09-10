package com.renhe.security.controller;


import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.renhe.base.Result;
import com.renhe.security.entity.User;
import com.renhe.security.service.UserServcie;
import com.renhe.security.vo.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



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

    @DeleteMapping(value="/{id}")
    public Result<Integer> destroy(@PathVariable String id){
        Result<Integer> result = new Result<>();
        int rcode = -1;
        try{
            int count = userServcie.destory(id);
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


}
