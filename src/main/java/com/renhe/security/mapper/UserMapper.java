package com.renhe.security.mapper;


import com.renhe.security.entity.User;
import com.renhe.base.PagerRS;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.Map;

@Mapper
@Component
public interface UserMapper {

    User findByUserNameAndPassword(String userName, String password);

    User findById(String id);

    User queryByParams(Map<String,Object> params);

    int save(User user);

    int update(User user);

    int destroy(String id);

    PagerRS<User> queryPager(Map<String,Object> params,int pageNo,int pageSize);

}
