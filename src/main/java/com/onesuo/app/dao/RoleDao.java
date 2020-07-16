package com.onesuo.app.dao;

import com.onesuo.app.common.base.BaseDao;
import com.onesuo.app.model.Role;

/**
 * 角色 数据层数据库操作接口类.
 **/
public interface RoleDao extends BaseDao<Role> {
    int getUserCount(Integer id);
}