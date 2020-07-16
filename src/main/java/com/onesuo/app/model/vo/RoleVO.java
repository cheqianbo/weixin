package com.onesuo.app.model.vo;

import com.onesuo.app.common.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
@Data
@ApiModel(value = "接收编辑角色参数")
public class RoleVO extends BaseModel {
    @ApiModelProperty(value = "ID，自增")
    private Integer id;
    @ApiModelProperty(value = "角色名称")
    private String roleName;
    @ApiModelProperty(value = "父ID")
    private Integer parentId;
    @ApiModelProperty(value = "角色描述")
    private String roleDesc;
    @ApiModelProperty(value = "权限列表")
    private List<PermissionVO> permissionVOList;

}
