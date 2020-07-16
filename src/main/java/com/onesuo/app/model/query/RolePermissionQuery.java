package com.onesuo.app.model.query;

import com.onesuo.app.common.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "角色权限 查询对象")
public class RolePermissionQuery extends BaseQuery {
    @ApiModelProperty(value = "角色id")
    private Integer roleId;
}