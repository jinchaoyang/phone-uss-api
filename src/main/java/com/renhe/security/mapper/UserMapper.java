package com.renhe.security.mapper;


import com.renhe.security.entity.User;
import com.renhe.base.PagerRS;
import com.renhe.security.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface UserMapper {

    User findByUserNameAndPassword(Map<String,Object> params);

    User findById(String id);

    List<User> queryByParams(UserVo vo);

    int save(User user);

    int update(User user);

    int destroy(String id);

    int bind(Map<String,Object> params);

    int release(String userId);


}
