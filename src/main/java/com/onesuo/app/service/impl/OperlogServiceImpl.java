package com.onesuo.app.service.impl;

import com.onesuo.app.common.base.BaseServiceImpl;
import com.onesuo.app.dao.OperlogDao;
import com.onesuo.app.model.Operlog;
import com.onesuo.app.model.query.OperlogQuery;
import com.onesuo.app.service.OperlogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 操作日志 服务实现.
 **/
@Slf4j
@Service
public class OperlogServiceImpl extends BaseServiceImpl<OperlogDao, Operlog, OperlogQuery> implements OperlogService {
}