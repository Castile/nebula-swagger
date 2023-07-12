package com.example.nebula.config;

import cn.dev33.satoken.stp.StpInterface;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * @ClassName: StpInterfaceImp
 */
@Component
@Configuration
public class StpInterfaceConfig implements StpInterface {

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        //AuthUser authUser = this.authUserDao.queryById(Integer.parseInt(loginId.toString()));
        //if (ObjectUtil.isNull(authUser)) {
        //    throw new GraphExecuteException("用户不存在");
        //}
        //AuthRole authRole = authRoleService.queryById(authUser.getRoleId());
        //if (ObjectUtil.isNull(authRole)) {
        //    throw new GraphExecuteException("角色不存在");
        //}

        // 所有操作权限
        //List<AuthMenu> menuList = authMenuService.queryAll();
        //List<String> menuCodeList;
        //if (RoleEnum.SYSTEM.name().equals(authRole.getType())) {
        //    menuCodeList = menuList.stream().map(AuthMenu::getMenuCode).collect(Collectors.toList());
        //} else {
        //    menuCodeList = authRoleMenuService.queryByRoleId(authRole.getId());
        //}
        return Collections.emptyList();
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        //AuthUser authUser = this.authUserDao.queryById(Integer.parseInt(loginId.toString()));
        //if (ObjectUtil.isNull(authUser)) {
        //    throw new GraphExecuteException("用户不存在");
        //}
        //AuthRole authRole = authRoleService.queryById(authUser.getRoleId());
        //if (ObjectUtil.isNull(authRole)) {
        //    throw new GraphExecuteException("角色不存在");
        //}
        return Collections.emptyList();
    }
}
