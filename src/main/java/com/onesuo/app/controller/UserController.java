package com.onesuo.app.controller;

import com.onesuo.app.common.base.Page;
import com.onesuo.app.common.base.ResultDTO;
import com.onesuo.app.model.User;
import com.onesuo.app.model.query.UserQuery;
import com.onesuo.app.service.UserService;
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
@Api(description = "用户 服务.")
public class UserController {
	@Autowired
	private UserService userService;
	
	@ApiOperation(value = "查询", notes = "根据id获取用户对象")
	@ApiImplicitParam(paramType = "path", name = "id", value = "用户id", required = true, dataType = "Integer")
	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	Object findOne(@PathVariable("id") Integer id) {
	    log.debug("请求/user获取用户对象");
	    User result = userService.findOne(id);
	    return result;
	}
	
	@ApiOperation(value = "查询列表", notes = "根据查询条件获取用户对象列表")
	@ApiImplicitParams({
	        @ApiImplicitParam(paramType = "query", name = "search", value = "关键字", required = false, dataType = "String"),
	        @ApiImplicitParam(paramType = "query", name = "sortColumn", value = "排序列与方向", required = false, dataType = "String"),
	        @ApiImplicitParam(paramType = "query", name = "pgIndex", value = "当前页", required = false, dataType = "Integer"),
	        @ApiImplicitParam(paramType = "query", name = "pgCount", value = "页大小", required = false, dataType = "Integer"),
	        @ApiImplicitParam(paramType = "query", name = "beginTime", value = "开始时间", required = false, dataType = "String"),
	        @ApiImplicitParam(paramType = "query", name = "endTime", value = "结束时间", required = false, dataType = "String")})
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	Object findAll(UserQuery query) {
	    Page<User> result = userService.findAllByPage(query);
	    return result;
	}
	
	@ApiOperation(value = "保存", notes = "保存用户对象并返回保存的用户对象")
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	Object save(@RequestBody User obj) {
	    int id = userService.save(obj);
	    return id;
	}
	
	@ApiOperation(value = "更新", notes = "更新用户对象")
	@RequestMapping(value = "/user", method = RequestMethod.PUT)
	Object update(@RequestBody User obj) {
	    userService.update(obj);
	    return ResultDTO.wrapSuccess(null);
	}
	
	@ApiOperation(value = "删除", notes = "根据id删除用户对象")
	@ApiImplicitParam(paramType = "path", name = "id", value = "用户id", required = true, dataType = "Integer")
	@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
	Object delete(@PathVariable("id") Integer id) {
	    userService.delete(id);
	    return ResultDTO.wrapSuccess(null);
	}
}