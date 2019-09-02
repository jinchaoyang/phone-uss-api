package com.renhe.security.mapper;


import com.renhe.security.entity.User;
import com.renhe.base.PagerRS;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface UserMapper {

    User findByUserNameAndPassword(Map<String,Object> params);

    User findById(String id);

    List<User> queryByParams(Map<String,Object> params);

    int save(User user);

    int update(User user);

    int destroy(String id);



}
