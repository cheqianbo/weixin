package com.onesuo.app.service;


import com.onesuo.app.common.base.BaseService;
import com.onesuo.app.model.Organization;
import com.onesuo.app.model.query.OrganizationQuery;

/**
 * 用户机构 服务.
 **/
public interface OrganizationService extends BaseService<Organization, OrganizationQuery> {
    int getUserCount(Integer id);
    int delete(Integer id);
}