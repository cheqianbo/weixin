package com.onesuo.app.model.query;

import com.onesuo.app.common.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "用户 查询对象")
public class UserQuery extends BaseQuery {
    @ApiModelProperty(value = "角色id")
    private Integer roleId;
    @ApiModelProperty(value = "用户机构id")
    private Integer userOrgId;
}