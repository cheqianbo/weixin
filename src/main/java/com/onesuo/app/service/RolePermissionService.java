package com.onesuo.app.service;

import com.onesuo.app.common.base.BaseService;
import com.onesuo.app.model.RolePermission;
import com.onesuo.app.model.query.RolePermissionQuery;

import java.util.List;

/**
 * 角色权限 服务.
 **/
public interface RolePermissionService extends BaseService<RolePermission, RolePermissionQuery> {
    List<RolePermission> getRolePermissions(Integer roleId);

    int deleteByRoleIdPermissionId(Integer roleId, Integer permissioId);
}