package com.onesuo.app.controller;

import com.onesuo.app.common.base.Page;
import com.onesuo.app.common.base.ResultDTO;
import com.onesuo.app.model.Organization;
import com.onesuo.app.model.query.OrganizationQuery;
import com.onesuo.app.service.OrganizationService;
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
@Api(description = "用户机构 服务.")
public class OrganizationController {
	@Autowired
	private OrganizationService organizationService;
	
	@ApiOperation(value = "查询", notes = "根据id获取用户机构对象")
	@ApiImplicitParam(paramType = "path", name = "id", value = "用户机构id", required = true, dataType = "Integer")
	@RequestMapping(value = "/organization/{id}", method = RequestMethod.GET)
	Object findOne(@PathVariable("id") Integer id) {
	    log.debug("请求/organization获取用户机构对象");
	    Organization result = organizationService.findOne(id);
	    return result;
	}
	
	@ApiOperation(value = "查询列表", notes = "根据查询条件获取用户机构对象列表")
	@ApiImplicitParams({
	        @ApiImplicitParam(paramType = "query", name = "search", value = "关键字", required = false, dataType = "String"),
	        @ApiImplicitParam(paramType = "query", name = "sortColumn", value = "排序列与方向", required = false, dataType = "String"),
	        @ApiImplicitParam(paramType = "query", name = "pgIndex", value = "当前页", required = false, dataType = "Integer"),
	        @ApiImplicitParam(paramType = "query", name = "pgCount", value = "页大小", required = false, dataType = "Integer"),
	        @ApiImplicitParam(paramType = "query", name = "beginTime", value = "开始时间", required = false, dataType = "String"),
	        @ApiImplicitParam(paramType = "query", name = "endTime", value = "结束时间", required = false, dataType = "String")})
	@RequestMapping(value = "/organizations", method = RequestMethod.GET)
	Object findAll(OrganizationQuery query) {
	    Page<Organization> result = organizationService.findAllByPage(query);
	    return result;
	}
	
	@ApiOperation(value = "保存", notes = "保存用户机构对象并返回保存的用户机构对象")
	@RequestMapping(value = "/organization", method = RequestMethod.POST)
	Object save(@RequestBody Organization obj) {
	    int id = organizationService.save(obj);
	    return id;
	}
	
	@ApiOperation(value = "更新", notes = "更新用户机构对象")
	@RequestMapping(value = "/organization", method = RequestMethod.PUT)
	Object update(@RequestBody Organization obj) {
	    organizationService.update(obj);
	    return ResultDTO.wrapSuccess(null);
	}
	
	@ApiOperation(value = "删除", notes = "根据id删除用户机构对象")
	@ApiImplicitParam(paramType = "path", name = "id", value = "用户机构id", required = true, dataType = "Integer")
	@RequestMapping(value = "/organization/{id}", method = RequestMethod.DELETE)
	Object delete(@PathVariable("id") Integer id) {
	    organizationService.delete(id);
	    return ResultDTO.wrapSuccess(null);
	}
}