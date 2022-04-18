package com.metoo.monitor.core.utils;

import com.metoo.monitor.core.entity.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Component;

@Component
public class ShiroUserHolder {


    public static User currentUser(){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user != null){
            return user;
        }
        return null;
    }

}
