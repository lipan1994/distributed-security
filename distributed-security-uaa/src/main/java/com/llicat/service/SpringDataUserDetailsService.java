package com.llicat.service;

import com.llicat.dao.UserDao;
import com.llicat.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: lipan
 * @date: 2021/2/27 17:34
 */
@Service
public class SpringDataUserDetailsService implements UserDetailsService {

    @Autowired
    UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDto userDto = userDao.getUserByName(username);
        if(null== userDto){

            return null;
        }

        //查询权限
        List<String> permissions = userDao.findPermissionsByUserId(userDto.getId());

        //添加ROLE_TELLER权限 支持调用com.llicat.service.BankService.post方法
        permissions.add("ROLE_TELLER");

        return User.withUsername(userDto.getUsername()).password(userDto.getPassword()).authorities(permissions.toArray(new String[]{})).build();

    }
}
