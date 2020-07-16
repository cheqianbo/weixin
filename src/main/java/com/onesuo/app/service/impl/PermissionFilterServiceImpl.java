package com.onesuo.app.service.impl;

import com.onesuo.app.common.base.BaseServiceImpl;
import com.onesuo.app.dao.PermissionFilterDao;
import com.onesuo.app.model.PermissionFilter;
import com.onesuo.app.model.query.PermissionFilterQuery;
import com.onesuo.app.service.PermissionFilterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * shiro配置 服务实现.
 **/
@Slf4j
@Service
public class PermissionFilterServiceImpl extends BaseServiceImpl<PermissionFilterDao, PermissionFilter, PermissionFilterQuery> implements PermissionFilterService {
}