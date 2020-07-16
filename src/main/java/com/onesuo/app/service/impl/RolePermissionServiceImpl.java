package com.onesuo.app.service.impl;

import com.onesuo.app.common.base.BaseServiceImpl;
import com.onesuo.app.dao.RolePermissionDao;
import com.onesuo.app.model.RolePermission;
import com.onesuo.app.model.query.RolePermissionQuery;
import com.onesuo.app.service.RolePermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色权限 服务实现.
 **/
@Slf4j
@Service
public class RolePermissionServiceImpl extends BaseServiceImpl<RolePermissionDao, RolePermission, RolePermissionQuery> implements RolePermissionService {
    @Override
    public List<RolePermission> getRolePermissions(Integer roleId) {
        return null;
    }

    @Override
    public int deleteByRoleIdPermissionId(Integer roleId, Integer permissioId) {
        return 0;
    }
}