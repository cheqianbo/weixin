package com.onesuo.app.model.vo;

import com.onesuo.app.common.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
@Data
@ApiModel(value = "权限对象")
public class PermissionVO extends BaseModel {
    @ApiModelProperty(value = "ID，自增")
    private Integer id;
    @ApiModelProperty(value = "父ID")
    private Integer parentId;
    @ApiModelProperty(value = "权限名称")
    private String permName;
    @ApiModelProperty(value = "英文名称")
    private String permission;
    @ApiModelProperty(value = "图标")
    private String icon;
    @ApiModelProperty(value = "类型：0 菜单 1 按钮")
    private Integer type;
    @ApiModelProperty(value = "排序，从小到大")
    private Integer sort;
    @ApiModelProperty(value = "状态")
    private Integer status;
    @ApiModelProperty(value = "子权限")
    private List<PermissionVO> childPermission;
}
