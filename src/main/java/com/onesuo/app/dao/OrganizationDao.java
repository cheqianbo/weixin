package com.onesuo.app.dao;

import com.onesuo.app.common.base.BaseDao;
import com.onesuo.app.model.Organization;

/**
 * 用户机构 数据层数据库操作接口类.
 **/
public interface OrganizationDao extends BaseDao<Organization> {
    int getUserCount(Integer id);
}