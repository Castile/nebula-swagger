package com.example.nebula.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.example.nebula.dto.UserDto;
import com.example.nebula.service.AuthUserService;
import com.example.nebula.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 用户信息(AuthUser)表控制层
 *
 * @author makejava
 * @since 2022-07-18 08:43:22
 */
@Slf4j
@RestController
@RequestMapping("user")
@Api(tags = "用户管理控制器")
public class AuthUserController {
    /**
     * 服务对象
     */
    @Resource
    private AuthUserService authUserService;

    @PostMapping("login")
    @ApiOperation("用户登录")
    public R doLogin(@RequestBody UserDto authUser) {
        return R.data(authUserService.doLogin(authUser.getUsername(), authUser.getPassword()));
    }

    @PostMapping("logout")
    @ApiOperation("用户退出")
    public R logout() {
        StpUtil.logoutByTokenValue(StpUtil.getTokenValue());
        return R.success("退出成功");
    }
}

