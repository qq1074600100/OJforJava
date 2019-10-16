package com.maple.oj.configure;

import com.maple.oj.interceptor.LoginInterceptor;
import com.maple.oj.interceptor.ManagerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class InterceptConfigure extends WebMvcConfigurationSupport {
    //配置添加拦截器，防止未登录的访问
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/login", "/register", "/", "/error", "/loginIntercept");
        registry.addInterceptor(new ManagerInterceptor())
                .addPathPatterns("/questionForm", "/managerForm", "/addQuestion",
                                 "/delete/*", "/addManager");
    }
}
