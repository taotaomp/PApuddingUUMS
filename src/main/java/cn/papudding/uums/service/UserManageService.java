package cn.papudding.uums.service;

import cn.papudding.uums.entity.User;

public interface UserManageService {
    /**
    * @Description: 用户注册
    * @Param: [user]
    * @return: boolean
    * @Author: Hu Songtao
    * @Date: 2021/1/27
    */
    boolean register(User user);
}
