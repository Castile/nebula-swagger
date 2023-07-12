package com.example.nebula.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.example.nebula.dto.UserDto;
import com.example.nebula.exception.GraphExecuteException;
import com.example.nebula.service.AuthUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户信息(AuthUser)表服务实现类
 *
 * @author makejava
 * @since 2022-07-18 08:43:23
 */
@Service("authUserService")
@Slf4j
public class AuthUserServiceImpl implements AuthUserService {

    /**
     * 模拟去数据库查询
     * @param username 用户名
     * @param password 密码
     * @return 封装返回对象
     */
    @Override
    public Map doLogin(String username, String password) {
        if (ObjectUtil.isNull(username)) {
            throw new GraphExecuteException("用户不存在!");
        }

        if (ObjectUtil.notEqual("test", password)) {
            throw new GraphExecuteException("用户名密码不正确！");
        }
        StpUtil.login(RandomUtil.randomInt());
        SaTokenInfo saTokenInfo = StpUtil.getTokenInfo();
        String token = saTokenInfo.getTokenValue();
        long tokenTimeout = saTokenInfo.getTokenTimeout();
        HashMap<Object, Object> res = new HashMap<>();
        res.put("token", token);
        res.put("tokenTimeout", tokenTimeout);
        res.put("info", new UserDto(username, password));
        return res;
    }
}
