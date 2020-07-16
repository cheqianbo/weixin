package com.onesuo.app.service.impl;

import com.onesuo.app.common.base.BaseServiceImpl;
import com.onesuo.app.dao.PermissionDao;
import com.onesuo.app.model.Permission;
import com.onesuo.app.model.query.PermissionQuery;
import com.onesuo.app.model.vo.PermissionVO;
import com.onesuo.app.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 权限 服务实现.
 **/
@Slf4j
@Service
public class PermissionServiceImpl extends BaseServiceImpl<PermissionDao, Permission, PermissionQuery> implements PermissionService {
    @Override
    public List<PermissionVO> getPermission(Integer roleId) {
        return null;
    }
}