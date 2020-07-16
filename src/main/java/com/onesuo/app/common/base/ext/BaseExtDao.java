package com.onesuo.app.common.base.ext;

import com.onesuo.app.common.base.BaseQuery;
import com.onesuo.app.common.base.BaseSerializable;

import java.util.List;

public interface BaseExtDao<S extends BaseSerializable> {
    /**
     * 查询多个对象
     */
    List<S> findAll(BaseQuery query);

    /**
     * 查询数量
     */
    Integer count(BaseQuery query);
}
