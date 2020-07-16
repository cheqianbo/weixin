package com.onesuo.app.controller;

import com.onesuo.app.common.base.Page;
import com.onesuo.app.common.base.ResultDTO;
import com.onesuo.app.model.Permission;
import com.onesuo.app.model.query.PermissionQuery;
import com.onesuo.app.service.PermissionService;
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
@Api(description = "权限 服务.")
public class PermissionController {
	@Autowired
	private PermissionService permissionService;
	
	@ApiOperation(value = "查询", notes = "根据id获取权限对象")
	@ApiImplicitParam(paramType = "path", name = "id", value = "权限id", required = true, dataType = "Integer")
	@RequestMapping(value = "/permission/{id}", method = RequestMethod.GET)
	Object findOne(@PathVariable("id") Integer id) {
	    log.debug("请求/permission获取权限对象");
	    Permission result = permissionService.findOne(id);
	    return result;
	}
	
	@ApiOperation(value = "查询列表", notes = "根据查询条件获取权限对象列表")
	@ApiImplicitParams({
	        @ApiImplicitParam(paramType = "query", name = "search", value = "关键字", required = false, dataType = "String"),
	        @ApiImplicitParam(paramType = "query", name = "sortColumn", value = "排序列与方向", required = false, dataType = "String"),
	        @ApiImplicitParam(paramType = "query", name = "pgIndex", value = "当前页", required = false, dataType = "Integer"),
	        @ApiImplicitParam(paramType = "query", name = "pgCount", value = "页大小", required = false, dataType = "Integer"),
	        @ApiImplicitParam(paramType = "query", name = "beginTime", value = "开始时间", required = false, dataType = "String"),
	        @ApiImplicitParam(paramType = "query", name = "endTime", value = "结束时间", required = false, dataType = "String")})
	@RequestMapping(value = "/permissions", method = RequestMethod.GET)
	Object findAll(PermissionQuery query) {
	    Page<Permission> result = permissionService.findAllByPage(query);
	    return result;
	}
	
	@ApiOperation(value = "保存", notes = "保存权限对象并返回保存的权限对象")
	@RequestMapping(value = "/permission", method = RequestMethod.POST)
	Object save(@RequestBody Permission obj) {
	    int id = permissionService.save(obj);
	    return id;
	}
	
	@ApiOperation(value = "更新", notes = "更新权限对象")
	@RequestMapping(value = "/permission", method = RequestMethod.PUT)
	Object update(@RequestBody Permission obj) {
	    permissionService.update(obj);
	    return ResultDTO.wrapSuccess(null);
	}
	
	@ApiOperation(value = "删除", notes = "根据id删除权限对象")
	@ApiImplicitParam(paramType = "path", name = "id", value = "权限id", required = true, dataType = "Integer")
	@RequestMapping(value = "/permission/{id}", method = RequestMethod.DELETE)
	Object delete(@PathVariable("id") Integer id) {
	    permissionService.delete(id);
	    return ResultDTO.wrapSuccess(null);
	}
}