package com.onesuo.app.model;

import com.onesuo.app.common.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "权限 对象")
public class Permission extends BaseModel {
	@ApiModelProperty(value = "ID，自增")
	private Integer id;
	@ApiModelProperty(value = "是否删除：0 未删除，1 删除")
	private Integer isDeleted;
	@ApiModelProperty(value = "记录创建时间")
	private Date gmtCreate;
	@ApiModelProperty(value = "记录修改时间")
	private Date gmtModified;
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
}