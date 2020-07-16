package com.onesuo.app.common.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
@ApiModel(value="分页属性")
public class BaseQuery extends BaseSerializable {
    @ApiModelProperty(value="排序列与方向")
    private String sortColumn;
    @ApiModelProperty(value="当前页码")
    private Integer pgIndex;
    @ApiModelProperty(value="页大小")
    private Integer pgCount;
    @ApiModelProperty(value="开始时间")
    private String beginTime;
    @ApiModelProperty(value="结束时间")
    private String endTime;
    @ApiModelProperty(value="搜索")
    private String search;
    public void setPgIndex(Integer pgIndex){this.pgIndex=pgIndex>0? pgIndex-1:0;}
    public void setSortColumnc(String sortColumn){
        this.sortColumn=sortColumn;
        if(StringUtils.isNotBlank(sortColumn)){
            sortColumn=sortColumn.toLowerCase();
            String[] arrStr=sortColumn.split(" ");
            sortColumn="";
            int end,c=0;
            for(String s:arrStr){
                if(!s.equals("")){
                    sortColumn +=s+" ";
                    c++;
                }
                if(c>=2){
                    break;
                }
            }
            if(sortColumn.contains("asc")){
                end=sortColumn.indexOf("asc")+3;
            }else if(sortColumn.contains("desc")){
                end=sortColumn.indexOf("desc")+4;
            }else{
                this.sortColumn=null;
                return;
            }
            this.sortColumn=sortColumn.substring(0,end);
        }

    }
}
