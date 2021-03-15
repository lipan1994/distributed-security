package com.llicat.model;

import lombok.Data;

/**
 * @author: lipan
 * @date: 2021/2/27 17:22
 */
@Data
public class UserDto {

    private String id;
    private String username;
    private String password;
    private String fullname;
    private String mobile;
}
