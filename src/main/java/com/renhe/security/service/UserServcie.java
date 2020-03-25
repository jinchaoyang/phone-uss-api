package com.renhe.security.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.renhe.security.entity.Resource;
import com.renhe.security.entity.User;
import com.renhe.security.mapper.UserMapper;
import com.renhe.security.vo.UserVo;
import com.renhe.utils.IDUtil;
import com.renhe.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServcie  {

    @Autowired
    UserMapper userMapper;

    @Autowired
    RoleService roleService;

    @Autowired
    ResourceService resourceService;

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
    @Transactional
    public int save(User user){
        user.setId(IDUtil.generate());
        user.setStatus("USE");
        user.setPassword(MD5.encode(user.getUserName()+user.getPassword()));
        int result =  userMapper.save(user);
        this.bindRoles(user.getId(),user.getRoleIds());
        return result;
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
         User u =  userMapper.findById(id);
         if(null!=u){
           List<String> roleIds = userMapper.findRoleIds(id);
           if(null!=roleIds && !roleIds.isEmpty()){
               u.setRoleIds(roleIds);
           }
         }
         return u;
     }

    /**
     * 更新用户信息
     * @param user
     * @return
     */
     public int update(User user){
         int result =  userMapper.update(user);
         this.releaseRoles(user.getId());
         this.bindRoles(user.getId(),user.getRoleIds());
         return result;
     }


     public int bindRoles(String userId,List<String> roleIds){
         Map<String,Object> params = new HashMap<>();
         params.put("userId",userId);
         params.put("roleIds",roleIds);
         return userMapper.bind(params);
     }

     public int releaseRoles(String userId){
         return userMapper.release(userId);
     }


     public List<String> findRoleIds(String userId){
         return userMapper.findRoleIds(userId);
     }

     public Map<Resource,List<Resource>> buildMenus(String userId){
         Map<Resource,List<Resource>> _result = new LinkedHashMap<>();
         List<String> roleIds = this.findRoleIds(userId);
         if(null!=roleIds && !roleIds.isEmpty()){
           List<Resource> resources =  roleService.findByRoleIds(roleIds);
           List<Resource> firstMenus = new ArrayList<>();
           List<Resource> secondMenus = new ArrayList<>();
           Set<String> firstIds = new HashSet<>();
           Map<String,Resource> map = new HashMap<>();
           Map<String,List<Resource>> result = new HashMap<>();
           for(Resource resource : resources){
               int level = resource.getLevel();
               String type = resource.getType();
               if(type.equals("MENU")){
                   map.put(resource.getId(),resource);
                   if(level==1){
                       firstIds.add(resource.getId());
                       firstMenus.add(resource);
                       result.put(resource.getId(),new ArrayList<>());
                   }else if(level==2){
                       secondMenus.add(resource);
                       if(!firstIds.contains(resource.getParentId())){
                           Resource parent = resourceService.findById(resource.getParentId());
                           if(null!=parent){
                               firstMenus.add(parent);
                               firstIds.add(resource.getParentId());

                           }

                       }

                       List<Resource> nodes = result.getOrDefault(resource.getParentId(),new ArrayList<Resource>());
                       nodes.add((resource));
                       result.put(resource.getParentId(),nodes);
                   }
               }

           }

           Collections.sort(firstMenus);

           for(Resource r : firstMenus){
               _result.put(r,result.getOrDefault(r.getId(),new ArrayList<>(0)));
           }

         }
         return _result;
     }

     public List<Resource> getMenusByLevel(List<Resource> resourceList,int level){
         resourceList = null==resourceList?new ArrayList<>(0): resourceList;
         return  resourceList.stream().filter( e -> e.getLevel()==1).collect(Collectors.toList());
     }

}
