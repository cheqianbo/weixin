package com.onesuo.app.dao;

import com.onesuo.app.common.base.BaseDao;
import com.onesuo.app.model.RolePermission;
import org.apache.ibatis.annotations.Param;

/**
 * 角色权限 数据层数据库操作接口类.
 **/
public interface RolePermissionDao extends BaseDao<RolePermission> {
    int deleteByRoleIdPermissionId(@Param("roleId") Integer roleId, @Param("permissionId") Integer permissionId);
}