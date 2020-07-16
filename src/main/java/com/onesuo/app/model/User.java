package com.onesuo.app.model;

import com.onesuo.app.common.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "用户 对象")
public class User extends BaseModel {
	@ApiModelProperty(value = "ID，自增")
	private Integer id;
	@ApiModelProperty(value = "是否删除：0 未删除，1 删除")
	private Integer isDeleted;
	@ApiModelProperty(value = "记录创建时间")
	private Date gmtCreate;
	@ApiModelProperty(value = "记录修改时间")
	private Date gmtModified;
	@ApiModelProperty(value = "用户机构id")
	private Integer userOrgId;
	@ApiModelProperty(value = "角色id")
	private Integer roleId;
	@ApiModelProperty(value = "用户账号")
	private String userName;
	@ApiModelProperty(value = "真实姓名")
	private String realName;
	@ApiModelProperty(value = "密码：md5")
	private String password;
	@ApiModelProperty(value = "联系电话")
	private String phone;
	@ApiModelProperty(value = "状态：0 在用 1 停用")
	private Integer status;
}