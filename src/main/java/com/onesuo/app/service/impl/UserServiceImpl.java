package com.onesuo.app.service.impl;

import com.onesuo.app.common.base.BaseServiceImpl;
import com.onesuo.app.common.base.ResultDTO;
import com.onesuo.app.dao.UserDao;
import com.onesuo.app.model.User;
import com.onesuo.app.model.query.UserQuery;
import com.onesuo.app.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 用户 服务实现.
 **/
@Slf4j
@Service
public class UserServiceImpl extends BaseServiceImpl<UserDao, User, UserQuery> implements UserService {
    @Override
    public User getUser(String userName) {
        return null;
    }

    @Override
    public User getUserByPhone(String phone) {
        return null;
    }

    @Override
    public ResultDTO changePassword(User obj) {
        return null;
    }

    @Override
    public void kickOutLogin(String sessionId, String userId) {

    }

    @Override
    public void updateStatus(Integer userId, Integer status) {

    }
}