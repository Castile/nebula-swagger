package com.example.nebula.service;

import java.util.Map;

/**
 * 用户信息(AuthUser)表服务接口
 *
 * @author makejava
 * @since 2022-07-18 08:43:23
 */
public interface AuthUserService {
    Map doLogin(String username, String password);

}
