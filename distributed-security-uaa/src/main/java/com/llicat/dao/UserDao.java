package com.llicat.dao;

import com.llicat.model.PermissionDto;
import com.llicat.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: lipan
 * @date: 2021/2/27 17:24
 */
@Repository
public class UserDao {

    @Autowired
    JdbcTemplate jdbcTemplate;


    public UserDto getUserByName(String userName){

        if(StringUtils.isEmpty(userName)){

            return null;
        }

        String sql=" select * from t_user where username=? ";
        List<UserDto> users = jdbcTemplate.query(sql, new Object[]{userName}, new BeanPropertyRowMapper<>(UserDto.class));
        if(null==users || users.isEmpty()){

            return null;
        }

        return users.get(0);
    }

    /**
     * 查询用户权限接口
     * @param userId
     * @return
     */
    public List<String> findPermissionsByUserId(String userId){

        //通过用户id 查询用户拥有的角色 通过角色查询权限。

        String sql=" SELECT\n" +
                "\t*\n" +
                "FROM\n" +
                "\tt_permission p \n" +
                "WHERE\n" +
                "\tp.id IN (\n" +
                "\tSELECT\n" +
                "\t\trp.permission_id \n" +
                "\tFROM\n" +
                "\t\tt_role_permission rp \n" +
                "\tWHERE\n" +
                "\trp.role_id IN ( SELECT t.role_id FROM t_user_role t WHERE t.user_id = ? ) \n" +
                "\t) ";

        List<PermissionDto> permissionDtos = jdbcTemplate.query(sql, new Object[]{userId}, new BeanPropertyRowMapper<>(PermissionDto.class));

       return permissionDtos.stream().map(permissionDto -> permissionDto.getCode()).collect(Collectors.toList());
    }
}
