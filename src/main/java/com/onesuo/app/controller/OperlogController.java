package com.onesuo.app.controller;

import com.onesuo.app.common.base.Page;
import com.onesuo.app.common.base.ResultDTO;
import com.onesuo.app.model.Operlog;
import com.onesuo.app.model.query.OperlogQuery;
import com.onesuo.app.service.OperlogService;
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
@Api(description = "操作日志 服务.")
public class OperlogController  {
	@Autowired
	private OperlogService operlogService;
	
	@ApiOperation(value = "查询", notes = "根据id获取操作日志对象")
	@ApiImplicitParam(paramType = "path", name = "id", value = "操作日志id", required = true, dataType = "Integer")
	@RequestMapping(value = "/operlog/{id}", method = RequestMethod.GET)
	Object findOne(@PathVariable("id") Integer id) {
	    log.debug("请求/operlog获取操作日志对象");
	    Operlog result = operlogService.findOne(id);
	    return result;
	}
	
	@ApiOperation(value = "查询列表", notes = "根据查询条件获取操作日志对象列表")
	@ApiImplicitParams({
	        @ApiImplicitParam(paramType = "query", name = "search", value = "关键字", required = false, dataType = "String"),
	        @ApiImplicitParam(paramType = "query", name = "sortColumn", value = "排序列与方向", required = false, dataType = "String"),
	        @ApiImplicitParam(paramType = "query", name = "pgIndex", value = "当前页", required = false, dataType = "Integer"),
	        @ApiImplicitParam(paramType = "query", name = "pgCount", value = "页大小", required = false, dataType = "Integer"),
	        @ApiImplicitParam(paramType = "query", name = "beginTime", value = "开始时间", required = false, dataType = "String"),
	        @ApiImplicitParam(paramType = "query", name = "endTime", value = "结束时间", required = false, dataType = "String")})
	@RequestMapping(value = "/operlogs", method = RequestMethod.GET)
	Object findAll(OperlogQuery query) {
	    Page<Operlog> result = operlogService.findAllByPage(query);
	    return result;
	}
	
	@ApiOperation(value = "保存", notes = "保存操作日志对象并返回保存的操作日志对象")
	@RequestMapping(value = "/operlog", method = RequestMethod.POST)
	Object save(@RequestBody Operlog obj) {
	    int id = operlogService.save(obj);
	    return id;
	}
	
	@ApiOperation(value = "更新", notes = "更新操作日志对象")
	@RequestMapping(value = "/operlog", method = RequestMethod.PUT)
	Object update(@RequestBody Operlog obj) {
	    operlogService.update(obj);
	    return ResultDTO.wrapSuccess(null);
	}
	
	@ApiOperation(value = "删除", notes = "根据id删除操作日志对象")
	@ApiImplicitParam(paramType = "path", name = "id", value = "操作日志id", required = true, dataType = "Integer")
	@RequestMapping(value = "/operlog/{id}", method = RequestMethod.DELETE)
	Object delete(@PathVariable("id") Integer id) {
	    operlogService.delete(id);
	    return ResultDTO.wrapSuccess(null);
	}
}