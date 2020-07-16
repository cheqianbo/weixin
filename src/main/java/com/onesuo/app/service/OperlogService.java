package com.onesuo.app.service;


import com.onesuo.app.common.base.BaseService;
import com.onesuo.app.model.Operlog;
import com.onesuo.app.model.query.OperlogQuery;

/**
 * 操作日志 服务.
 **/
public interface OperlogService extends BaseService<Operlog, OperlogQuery> {
    int save(Operlog obj);
}