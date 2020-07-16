package com.onesuo.app.service.impl;

import com.onesuo.app.common.base.BaseServiceImpl;
import com.onesuo.app.common.base.ResultDTO;
import com.onesuo.app.dao.RoleDao;
import com.onesuo.app.model.Role;
import com.onesuo.app.model.query.RoleQuery;
import com.onesuo.app.model.vo.RoleVO;
import com.onesuo.app.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 角色 服务实现.
 **/
@Slf4j
@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleDao, Role, RoleQuery> implements RoleService {
    @Override
    public int getUserCount(Integer id) {
        return 0;
    }

    @Override
    public void updateStatus(Integer roleId, Integer status) {

    }

    @Override
    public ResultDTO addRoleByRoleId(RoleVO roleVO) {
        return null;
    }
}