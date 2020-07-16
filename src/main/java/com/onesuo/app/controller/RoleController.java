package com.onesuo.app.controller;

import com.onesuo.app.common.base.Page;
import com.onesuo.app.common.base.ResultDTO;
import com.onesuo.app.model.Role;
import com.onesuo.app.model.query.RoleQuery;
import com.onesuo.app.service.RoleService;
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
@Api(description = "角色 服务.")
public class RoleController  {
	@Autowired
	private RoleService roleService;
	
	@ApiOperation(value = "查询", notes = "根据id获取角色对象")
	@ApiImplicitParam(paramType = "path", name = "id", value = "角色id", required = true, dataType = "Integer")
	@RequestMapping(value = "/role/{id}", method = RequestMethod.GET)
	Object findOne(@PathVariable("id") Integer id) {
	    log.debug("请求/role获取角色对象");
	    Role result = roleService.findOne(id);
	    return result;
	}
	
	@ApiOperation(value = "查询列表", notes = "根据查询条件获取角色对象列表")
	@ApiImplicitParams({
	        @ApiImplicitParam(paramType = "query", name = "search", value = "关键字", required = false, dataType = "String"),
	        @ApiImplicitParam(paramType = "query", name = "sortColumn", value = "排序列与方向", required = false, dataType = "String"),
	        @ApiImplicitParam(paramType = "query", name = "pgIndex", value = "当前页", required = false, dataType = "Integer"),
	        @ApiImplicitParam(paramType = "query", name = "pgCount", value = "页大小", required = false, dataType = "Integer"),
	        @ApiImplicitParam(paramType = "query", name = "beginTime", value = "开始时间", required = false, dataType = "String"),
	        @ApiImplicitParam(paramType = "query", name = "endTime", value = "结束时间", required = false, dataType = "String")})
	@RequestMapping(value = "/roles", method = RequestMethod.GET)
	Object findAll(RoleQuery query) {
	    Page<Role> result = roleService.findAllByPage(query);
	    return result;
	}
	
	@ApiOperation(value = "保存", notes = "保存角色对象并返回保存的角色对象")
	@RequestMapping(value = "/role", method = RequestMethod.POST)
	Object save(@RequestBody Role obj) {
	    int id = roleService.save(obj);
	    return id;
	}
	
	@ApiOperation(value = "更新", notes = "更新角色对象")
	@RequestMapping(value = "/role", method = RequestMethod.PUT)
	Object update(@RequestBody Role obj) {
	    roleService.update(obj);
	    return ResultDTO.wrapSuccess(null);
	}
	
	@ApiOperation(value = "删除", notes = "根据id删除角色对象")
	@ApiImplicitParam(paramType = "path", name = "id", value = "角色id", required = true, dataType = "Integer")
	@RequestMapping(value = "/role/{id}", method = RequestMethod.DELETE)
	Object delete(@PathVariable("id") Integer id) {
	    roleService.delete(id);
	    return ResultDTO.wrapSuccess(null);
	}
}