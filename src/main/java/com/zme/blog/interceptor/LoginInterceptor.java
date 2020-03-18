package com.zme.blog.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @authon timber
 * @description 后台登录拦截器
 * @date 2020/2/10 上午12:42
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
    /**
     * @Author: timber
     * @Description: 请求未到达目的地之前预处理
     * @Date: 2020/2/10 上午12:43
     * @param request
     * @param response
     * @param handler
     * @Return true 通过 ; false 拦截
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        if(request.getSession().getAttribute("user") != null){
            return true;
        }
        response.sendRedirect("/admin");
        return false;
    }
}
