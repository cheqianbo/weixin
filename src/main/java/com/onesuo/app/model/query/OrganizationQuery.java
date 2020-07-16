package com.onesuo.app.model.query;

import com.onesuo.app.common.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "用户机构 查询对象")
public class OrganizationQuery extends BaseQuery {
}