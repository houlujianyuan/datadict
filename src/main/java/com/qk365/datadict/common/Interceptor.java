package com.qk365.datadict.common;

import com.qk365.datadict.po.Users;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Interceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        Users users = (Users) request.getSession().getAttribute("user");
        if ( null == users ) {
            response.sendRedirect(request.getContextPath() + "/");
            return false;
        }else {
            return true;
        }

    }
}
