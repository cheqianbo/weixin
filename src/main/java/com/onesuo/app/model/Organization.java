package com.onesuo.app.model;

import com.onesuo.app.common.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "用户机构 对象")
public class Organization extends BaseModel {
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
	@ApiModelProperty(value = "机构路径：001 001001")
	private String orgPath;
	@ApiModelProperty(value = "机构名称")
	private String orgName;
	@ApiModelProperty(value = "机构地址")
	private String address;
	@ApiModelProperty(value = "联系人")
	private String contact;
	@ApiModelProperty(value = "联系电话")
	private String phone;
}