package com.onesuo.app.model.vo;

import com.onesuo.app.common.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "接收编辑角色参数")
public class UserVO extends BaseModel {
    @ApiModelProperty(value = "用户机构id")
    private Integer userOrgId;
    @ApiModelProperty(value = "角色id")
    private Integer roleId;
    @ApiModelProperty(value = "真实姓名")
    private String realName;
    @ApiModelProperty(value = "是否第一次登录")
    private Integer isFirst;
    @ApiModelProperty(value = "权限列表")
    private List<PermissionVO> permissionList;
}
