package com.example.nebula.config;

import cn.dev33.satoken.stp.StpUtil;
import com.example.nebula.service.AuthUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: AuthInfoInterceptor
 */
@Slf4j
@Component
public class AuthInfoInterceptor implements HandlerInterceptor {


    @Autowired
    AuthUserService authUserService;

    @Value("${sa-token.timeout}")
    private long timeout;// 单位s

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //String tenantId = request.getHeader(TenantEnum.SYSTEM_TENANT_ID.getName());
        //log.info(">>>>>>>拦截到api相关请求头<<<<<<<<" + tenantId);
        //if (!StringUtils.isEmpty(tenantId)) {
        //    //直接搂下来，放到ThreadLocal中 后续直接从中获取
        //    CurrentLocal.set(TenantEnum.SYSTEM_TENANT_ID.getName(), tenantId);
        //}

        log.info("token剩余过期时间:{}", StpUtil.getTokenTimeout());
        log.info("临时token剩余过期时间:{}", StpUtil.getTokenActivityTimeout());
        // 续约token
        if (StpUtil.getTokenTimeout() <= StpUtil.getTokenActivityTimeout()) {
            StpUtil.renewTimeout(timeout);
            log.info("token时间重置:{}", StpUtil.getTokenTimeout());
        }

        //Object loginId = StpUtil.getLoginId();
        //AuthUserVo authUserVo = authUserService.queryById(Integer.parseInt(loginId.toString()));
        //CurrentLocal.setUse(authUserVo);
        return true;//注意 这里必须是true否则请求将就此终止。
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //移除app-user
        //CurrentLocal.remove(TenantEnum.SYSTEM_TENANT_ID.getName());
        //CurrentLocal.removeUser();
    }

}
