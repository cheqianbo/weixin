package com.onesuo.app.service;

import com.onesuo.app.common.base.BaseService;
import com.onesuo.app.common.base.ResultDTO;
import com.onesuo.app.model.Role;
import com.onesuo.app.model.query.RoleQuery;
import com.onesuo.app.model.vo.RoleVO;

/**
 * 角色 服务.
 **/
public interface RoleService extends BaseService<Role, RoleQuery> {
    int getUserCount(Integer id);
    void updateStatus(Integer roleId,Integer status);
    ResultDTO addRoleByRoleId(RoleVO roleVO);
}