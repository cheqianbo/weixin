package com.onesuo.app.common.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "分页对象")
public class Page<T> implements Serializable {
	/**
	 * 当前页
	 */
	@ApiModelProperty(value = "当前页")
	public Integer index;
	/**
	 * 页大小
	 */
	@ApiModelProperty(value = "页大小")
	public Integer size;
	/**
	 * 总元素数
	 */
	@ApiModelProperty(value = "总元素数")
	public Integer totalElements;
	/**
	 * 元素列表
	 */
	@ApiModelProperty(value = "元素列表")
	public List<T> elements;
}