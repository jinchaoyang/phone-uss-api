package com.renhe.security.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.renhe.security.entity.Role;
import com.renhe.security.mapper.RoleMapper;
import com.renhe.security.vo.RoleVo;
import com.renhe.utils.IDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleService {

    @Autowired
    RoleMapper roleMapper;

    /**
     * 分页查询角色信息
     * @param vo
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageInfo<Role> queryPager(RoleVo vo, int pageNo, int pageSize){
        PageHelper.startPage(pageNo,pageSize);
        List<Role> roleList = roleMapper.queryByParams(vo);
        PageInfo<Role> pageInfo = new PageInfo<>(roleList);
        return pageInfo;
    }



    public List<Role> queryByParams(RoleVo vo){
        return roleMapper.queryByParams(vo);
    }

    /**
     * 保存角色信息
     * @param role
     * @return
     */
    public int save(Role role){
        role.setId(IDUtil.generate());
        return roleMapper.save(role);
    }

    /**
     * 删除角色信息
     * @param id
     * @return
     */
    public int destroy(String id){
        return roleMapper.destroy(id);
    }

    /**
     * 根据ID查询角色信息
     * @param id
     * @return
     */
    public Role findById(String id){
        return roleMapper.findById(id);
    }

    /**
     * 更新角色信息
     * @param role
     * @return
     */
    public int update(Role role){
        return roleMapper.update(role);
    }


    public int bindResources(String roleId,List<String> resourceIds){
        Map<String,Object> params = new HashMap<>();
        params.put("roleId",roleId);
        params.put("resourceIds",resourceIds);
        return roleMapper.bind(params);
    }

    public int releaseResources(String roleId){
        return roleMapper.release(roleId);
    }

}
