package com.onesuo.app.model;

import com.onesuo.app.common.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "操作日志 对象")
public class Operlog extends BaseModel {
	@ApiModelProperty(value = "ID，自增")
	private Integer id;
	@ApiModelProperty(value = "是否删除：0 未删除，1 删除")
	private Integer isDeleted;
	@ApiModelProperty(value = "记录创建时间")
	private Date gmtCreate;
	@ApiModelProperty(value = "记录修改时间")
	private Date gmtModified;
	@ApiModelProperty(value = "操作人id")
	private Integer userId;
	@ApiModelProperty(value = "操作人的IP")
	private String userIp;
	@ApiModelProperty(value = "模块名称")
	private String module;
	@ApiModelProperty(value = "类的名称")
	private String className;
	@ApiModelProperty(value = "方法名称")
	private String methodName;
	@ApiModelProperty(value = "是否成功：0 失败，1 成功")
	private Integer flag;
	@ApiModelProperty(value = "请求详情")
	private String detail;
}