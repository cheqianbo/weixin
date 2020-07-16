package com.onesuo.app.dao;

import com.onesuo.app.common.base.BaseDao;
import com.onesuo.app.model.Permission;
import com.onesuo.app.model.query.PermissionQuery;

import java.util.List;

/**
 * 权限 数据层数据库操作接口类.
 **/
public interface PermissionDao extends BaseDao<Permission> {
    List<Permission> findAllByQuery(PermissionQuery query);
}