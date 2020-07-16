package com.onesuo.app.controller;

import com.onesuo.app.common.base.Page;
import com.onesuo.app.common.base.ResultDTO;
import com.onesuo.app.model.PermissionFilter;
import com.onesuo.app.model.query.PermissionFilterQuery;
import com.onesuo.app.service.PermissionFilterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping
@Api(description = "shiro配置 服务.")
public class PermissionFilterController  {
	@Autowired
	private PermissionFilterService permissionFilterService;
	
	@ApiOperation(value = "查询", notes = "根据id获取shiro配置对象")
	@ApiImplicitParam(paramType = "path", name = "id", value = "shiro配置id", required = true, dataType = "Integer")
	@RequestMapping(value = "/permissionFilter/{id}", method = RequestMethod.GET)
	Object findOne(@PathVariable("id") Integer id) {
	    log.debug("请求/permissionFilter获取shiro配置对象");
	    PermissionFilter result = permissionFilterService.findOne(id);
	    return result;
	}
	
	@ApiOperation(value = "查询列表", notes = "根据查询条件获取shiro配置对象列表")
	@ApiImplicitParams({
	        @ApiImplicitParam(paramType = "query", name = "search", value = "关键字", required = false, dataType = "String"),
	        @ApiImplicitParam(paramType = "query", name = "sortColumn", value = "排序列与方向", required = false, dataType = "String"),
	        @ApiImplicitParam(paramType = "query", name = "pgIndex", value = "当前页", required = false, dataType = "Integer"),
	        @ApiImplicitParam(paramType = "query", name = "pgCount", value = "页大小", required = false, dataType = "Integer"),
	        @ApiImplicitParam(paramType = "query", name = "beginTime", value = "开始时间", required = false, dataType = "String"),
	        @ApiImplicitParam(paramType = "query", name = "endTime", value = "结束时间", required = false, dataType = "String")})
	@RequestMapping(value = "/permissionFilters", method = RequestMethod.GET)
	Object findAll(PermissionFilterQuery query) {
	    Page<PermissionFilter> result = permissionFilterService.findAllByPage(query);
	    return result;
	}
	
	@ApiOperation(value = "保存", notes = "保存shiro配置对象并返回保存的shiro配置对象")
	@RequestMapping(value = "/permissionFilter", method = RequestMethod.POST)
	Object save(@RequestBody PermissionFilter obj) {
	    int id = permissionFilterService.save(obj);
	    return id;
	}
	
	@ApiOperation(value = "更新", notes = "更新shiro配置对象")
	@RequestMapping(value = "/permissionFilter", method = RequestMethod.PUT)
	Object update(@RequestBody PermissionFilter obj) {
	    permissionFilterService.update(obj);
	    return ResultDTO.wrapSuccess(null);
	}
	
	@ApiOperation(value = "删除", notes = "根据id删除shiro配置对象")
	@ApiImplicitParam(paramType = "path", name = "id", value = "shiro配置id", required = true, dataType = "Integer")
	@RequestMapping(value = "/permissionFilter/{id}", method = RequestMethod.DELETE)
	Object delete(@PathVariable("id") Integer id) {
	    permissionFilterService.delete(id);
	    return ResultDTO.wrapSuccess(null);
	}
}