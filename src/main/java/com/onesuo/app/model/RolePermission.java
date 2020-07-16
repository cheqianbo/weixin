package com.onesuo.app.model;

import com.onesuo.app.common.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "角色权限 对象")
public class RolePermission extends BaseModel {
	@ApiModelProperty(value = "ID，自增")
	private Integer id;
	@ApiModelProperty(value = "是否删除：0 未删除，1 删除")
	private Integer isDeleted;
	@ApiModelProperty(value = "记录创建时间")
	private Date gmtCreate;
	@ApiModelProperty(value = "记录修改时间")
	private Date gmtModified;
	@ApiModelProperty(value = "角色id")
	private Integer roleId;
	@ApiModelProperty(value = "权限id")
	private Integer permissionId;
}