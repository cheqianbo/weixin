package com.onesuo.app.model.query;

import com.onesuo.app.common.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "角色 查询对象")
public class RoleQuery extends BaseQuery {
    @ApiModelProperty(value = "角色名称")
    private String roleName;
    @ApiModelProperty(value = "状态")
    private Integer status;
}