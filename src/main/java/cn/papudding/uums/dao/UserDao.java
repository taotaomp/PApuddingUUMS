package cn.papudding.uums.dao;

import cn.papudding.uums.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface UserDao {
    @Select("SELECT * FROM user WHERE username = '${username}' ")
    User getUserByUserName(String username);

    @Select("SELECT resource_name FROM resources WHERE id = " +
            "(SELECT resource_id FROM role_r_resource WHERE role_id = " +
            "(SELECT role_id FROM user_r_role WHERE user_id = " +
            "(SELECT id FROM user where username = '${username}')));")
    List<String> getResourcesByUsername(String username);

    Integer insert(@Param("user") User user);
}
