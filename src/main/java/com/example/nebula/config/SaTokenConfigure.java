package com.example.nebula.config;

/**
 * @ClassName: SaTokenConfigure
 */

import cn.dev33.satoken.interceptor.SaAnnotationInterceptor;
import cn.dev33.satoken.interceptor.SaRouteInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Component
public class SaTokenConfigure implements WebMvcConfigurer {


    @Autowired
    AuthInfoInterceptor authInfoInterceptor;

    //放行路径
    private static final String[] EXCLUDE_PATH_PATTERNS = {
        "**/swagger-ui.html",
        "/swagger-resources/**",
        "/webjars/**",
        "/v3/**",
        "/swagger-ui.html/**",
        "/swagger-ui/**",
        "/swagger-ui/index.html#/**",
        "/doc.html/**",
        "/error",
        "/favicon.ico",
        "sso/auth",
        "/csrf",
        "/user/add",
        "/user/reset",
        "/user/edit",
        "/authUserApply/apply",
        "/authCompany/list"
    };
    // 注册Sa-Token的注解拦截器，打开注解式鉴权功能
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 续约token 全局项目id
        registry.addInterceptor(authInfoInterceptor).addPathPatterns("/**")
            .excludePathPatterns("/user/login")
            .excludePathPatterns(EXCLUDE_PATH_PATTERNS);

        // 注册注解拦截器，并排除不需要注解鉴权的接口地址 (与登录拦截器无关)
        registry.addInterceptor(new SaRouteInterceptor((req, res, handler) -> {
            // 登录认证 -- 拦截所有路由，并排除/user/login 用于开放登录
            SaRouter.match("/**", "/user/login", r -> StpUtil.checkLogin());
        })).addPathPatterns("/**").excludePathPatterns(EXCLUDE_PATH_PATTERNS);
        // 开启aop校验权限
        registry.addInterceptor(new SaAnnotationInterceptor()).addPathPatterns("/**");


    }


}
