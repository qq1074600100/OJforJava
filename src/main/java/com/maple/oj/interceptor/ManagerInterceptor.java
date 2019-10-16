package com.maple.oj.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

public class ManagerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
            Exception {
        System.out.println(request.getRequestURL() + "进入manager拦截器");
        HttpSession session = request.getSession(false);

        //如果session中有user即可继续访问，否则返回
        if (session != null && session.getAttribute("user") != null
            && session.getAttribute("isManager").equals(true)) {
            return true;
        } else {
            response.sendRedirect("/managerIntercept");
            return false;
        }
    }
}