package com.onesuo.app.service;

import com.onesuo.app.common.base.BaseService;
import com.onesuo.app.common.base.ResultDTO;
import com.onesuo.app.model.User;
import com.onesuo.app.model.query.UserQuery;

/**
 * 用户 服务.
 **/
public interface UserService extends BaseService<User, UserQuery> {
    User getUser(String userName);
    User getUserByPhone(String phone);
    ResultDTO changePassword(User obj);
    void kickOutLogin(String sessionId, String userId);
    void updateStatus(Integer userId,Integer status);
}