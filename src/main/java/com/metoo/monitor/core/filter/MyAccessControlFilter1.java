package com.metoo.monitor.core.filter;


import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class MyAccessControlFilter1 extends AccessControlFilter {


    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object o) throws Exception {
        System.out.println("isAccessAllowed");
        Subject subject = getSubject(request, response);
        // 判断是否为登录请求
        if (isLoginRequest(request, response)) {
            System.out.println("通过IsRememberme登录");
            return true;
        }
        if (subject.isAuthenticated() == true) {
            System.out.println("通过登录认证登录");
        }
        // 判断是否为RememberMe 判断Session过期时间，如果快过期则重新设置Session过期时间
        if (subject.isRemembered()) {
            return true;
        } else {
            return subject.getPrincipal() != null && (subject.isRemembered() || subject.isAuthenticated());
        }

    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        System.out.println("onAccessDenied");
        redirectToLogin(request, response);
        return true;
    }
}