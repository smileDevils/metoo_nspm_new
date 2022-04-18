package com.metoo.monitor.shiro.filter;

import com.alibaba.fastjson.JSONObject;
import com.metoo.monitor.core.entity.User;
import com.metoo.monitor.core.service.IUserService;
import com.metoo.monitor.core.utils.ApplicationContextUtils;
import com.metoo.monitor.core.vo.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class MyAccessControlFilter extends AccessControlFilter {


    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        //判断用户是通过记住我功能自动登录,此时session失效
        Subject subject = SecurityUtils.getSubject();
        System.out.println(subject.isAuthenticated());
        System.out.println(subject.isRemembered());
        if(!subject.isAuthenticated() && subject.isRemembered()){// 如果未认证并且未IsreMenmberMe(Session失效问题)
            try {
                String principals = subject.getPrincipals().toString();
                IUserService userService = (IUserService) ApplicationContextUtils.getBean("userServiceImpl");
                User user = userService.findByUserName(principals);
                //对密码进行加密后验证
                UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), "123456", true);
                //把当前用户放入session
                subject.login(token);
                Session session = subject.getSession();
                session.setTimeout(2678400);
                //设置会话的过期时间--ms,默认是30分钟，设置负数表示永不过期
//                session.setTimeout(-1000l);
            }catch (Exception e){
                e.printStackTrace();
                //自动登录失败,跳转到登录页面
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().print(JSONObject.toJSONString(new Result(401,"认证失败")));
                return false;
            }
            if(!subject.isAuthenticated()){
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().print(JSONObject.toJSONString(new Result(401,"认证失败")));
                //自动登录失败,跳转到登录页面
//                response.sendRedirect(request.getContextPath()+"/login");

                return false;
            }
        }
        return true;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        return false;
    }
}
