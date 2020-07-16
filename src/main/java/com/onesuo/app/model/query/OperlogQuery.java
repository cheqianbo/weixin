package com.onesuo.app.model.query;

import com.onesuo.app.common.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "操作日志 查询对象")
public class OperlogQuery extends BaseQuery {
    /**
     * 方法名称
     */
    @ApiModelProperty(value = "方法名称")
    private String userName;
    /**
     * 方法名称
     */
    @ApiModelProperty(value = "方法名称")
    private String className;
    /**
     * 方法名称
     */
    @ApiModelProperty(value = "方法名称")
    private String methodName;
    /**
     * 是否成功：0 失败，1 成功
     */
    @ApiModelProperty(value = "是否成功：0 失败，1 成功")
    private Integer flag;
}