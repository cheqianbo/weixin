package com.onesuo.app.model.query;

import com.onesuo.app.common.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "权限 查询对象")
public class PermissionQuery extends BaseQuery {
    @ApiModelProperty(value = "是否删除：0 未删除，1 删除")
    private Integer isDeleted;
    @ApiModelProperty(value = "角色id")
    private Integer roleId;
}