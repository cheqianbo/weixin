package com.onesuo.app.common.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "父Model")
public class BaseModel extends BaseSerializable {
    @ApiModelProperty(value = "id")
    private transient Integer id;

}