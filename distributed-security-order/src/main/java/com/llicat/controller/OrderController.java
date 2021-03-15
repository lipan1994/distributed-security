package com.llicat.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单访问资源
 * @author: lipan
 * @date: 2021/3/6 16:52
 */
@RestController
public class OrderController {


    /**
     * 拥有p1权限访问资源
     * @return
     */
    @GetMapping(value = "/r1")
    @PreAuthorize("hasAnyAuthority('p1')")
    public String r1() {
        return  SecurityContextHolder.getContext().getAuthentication().getPrincipal()+"访问资源1";
    }

}
