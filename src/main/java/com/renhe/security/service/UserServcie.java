package com.renhe.security.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.renhe.security.entity.User;
import com.renhe.security.mapper.UserMapper;
import com.renhe.security.vo.UserVo;
import com.renhe.utils.IDUtil;
import com.renhe.utils.MD5;
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
     * @param vo
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageInfo<User> queryPager(UserVo vo, int pageNo, int pageSize){
        PageHelper.startPage(pageNo,pageSize);
        List<User> userList = userMapper.queryByParams(vo);
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        return pageInfo;
    }



    public List<User> queryByParams(UserVo vo){
        return userMapper.queryByParams(vo);
    }

    /**
     * 保存用户信息
     * @param user
     * @return
     */
    public int save(User user){
        user.setId(IDUtil.generate());
        user.setStatus("USE");
        user.setPassword(MD5.encode(user.getUserName()+user.getPassword()));
        return userMapper.save(user);
    }

    /**
     * 删除用户信息
     * @param id
     * @return
     */
     public int destroy(String id){
        return userMapper.destroy(id);
     }

    /**
     * 根据ID查询用户信息
     * @param id
     * @return
     */
     public User findById(String id){
         return userMapper.findById(id);
     }

    /**
     * 更新用户信息
     * @param user
     * @return
     */
     public int update(User user){
         return userMapper.update(user);
     }




}
