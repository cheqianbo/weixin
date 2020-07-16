package com.onesuo.app.dao;

import com.onesuo.app.common.base.BaseDao;
import com.onesuo.app.model.User;
import org.apache.ibatis.annotations.Param;

/**
 * 用户 数据层数据库操作接口类.
 **/
public interface UserDao extends BaseDao<User> {
    User selectByUserName(@Param("userName") String userName);
    User selectByPhone(@Param("phone") String phone);
}