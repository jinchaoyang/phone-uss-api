package com.renhe.security.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.renhe.security.entity.TenantUser;
import com.renhe.security.mapper.TenantUserMapper;
import com.renhe.security.vo.UserVo;
import com.renhe.utils.IDUtil;
import com.renhe.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;


@Service
public class TenantUserServcie {

    @Autowired
    TenantUserMapper userMapper;


    /**
     * 根据用户名和密码查询用户信息
     * @param userName
     * @param password
     * @return
     */
    public TenantUser findByUserNameAndPassword(String userName, String password,String tenantId){
        Map<String,Object> params = new HashMap<>();
        params.put("userName",userName);
        params.put("password",password);
        params.put("tenantId",tenantId);
        return userMapper.findByUserNameAndPassword(params);
    }


    /**
     * 分页查询用户信息
     * @param vo
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageInfo<TenantUser> queryPager(UserVo vo, int pageNo, int pageSize){
        PageHelper.startPage(pageNo,pageSize);
        List<TenantUser> userList = userMapper.queryByParams(vo);
        PageInfo<TenantUser> pageInfo = new PageInfo<>(userList);
        return pageInfo;
    }



    public List<TenantUser> queryByParams(UserVo vo){
        return userMapper.queryByParams(vo);
    }

    /**
     * 保存用户信息
     * @param user
     * @return
     */
    @Transactional
    public int save(TenantUser user){
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
     public TenantUser findById(String id){
         return  userMapper.findById(id);
     }

    /**
     * 更新用户信息
     * @param user
     * @return
     */
     public int update(TenantUser user){
        return userMapper.update(user);
     }


}
