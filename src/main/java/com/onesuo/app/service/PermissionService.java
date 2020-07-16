package com.onesuo.app.service;

import com.onesuo.app.common.base.BaseService;
import com.onesuo.app.model.Permission;
import com.onesuo.app.model.query.PermissionQuery;
import com.onesuo.app.model.vo.PermissionVO;

import java.util.List;

/**
 * 权限 服务.
 **/
public interface PermissionService extends BaseService<Permission, PermissionQuery> {
    List<PermissionVO> getPermission(Integer roleId);
}