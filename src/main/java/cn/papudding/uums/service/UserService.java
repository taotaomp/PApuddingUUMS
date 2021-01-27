package cn.papudding.uums.service;

import cn.papudding.uums.dao.UserDao;
import cn.papudding.uums.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService,UserManageService {
    @Autowired
    private UserDao userDao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.getUserByUserName(username);
        org.springframework.security.core.userdetails.User
                userDetails = new org.springframework.security.core.userdetails.User(
                        user.getUsername(),user.getPassword(),getGrantedAuthority(username));
        return userDetails;
    }
    private List<SimpleGrantedAuthority> getGrantedAuthority(String username){
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<SimpleGrantedAuthority>();
        List<String> resourceList = userDao.getResourcesByUsername(username);
        for(String resource : resourceList){
            simpleGrantedAuthorities.add(new SimpleGrantedAuthority(resource));
        }
        return simpleGrantedAuthorities;
    }

    @Resource
    private PasswordEncoder passwordEncoder;
    /**
     * @param user
     * @Description: 用户注册
     * @Param: [user]
     * @return: boolean
     * @Author: Hu Songtao
     * @Date: 2021/1/27
     */
    @Override
    public boolean register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDao.insert(user) > 0;
    }
}
