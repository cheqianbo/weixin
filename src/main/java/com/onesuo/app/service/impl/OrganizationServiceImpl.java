package com.onesuo.app.service.impl;

import com.onesuo.app.common.base.BaseServiceImpl;
import com.onesuo.app.dao.OrganizationDao;
import com.onesuo.app.model.Organization;
import com.onesuo.app.model.query.OrganizationQuery;
import com.onesuo.app.service.OrganizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 用户机构 服务实现.
 **/
@Slf4j
@Service
public class OrganizationServiceImpl extends BaseServiceImpl<OrganizationDao, Organization, OrganizationQuery> implements OrganizationService {
    @Override
    public int getUserCount(Integer id) {
        return 0;
    }
}