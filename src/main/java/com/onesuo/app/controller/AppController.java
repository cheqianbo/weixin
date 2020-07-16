package com.onesuo.app.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Slf4j
@EnableAutoConfiguration
@Controller
@Api(description = "微信功能")
public class AppController {

    /**
     * 1.返回下载链接地址
     *
     * @return
     */
    @ApiOperation(value = "返回下载链接地址", notes = "返回下载链接地址")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "String", name = "link", value = "下载链接地址", required = true, dataType = "query")
    })
    @RequestMapping(value = "/app/getLinkAddress", method = RequestMethod.GET)
    public Object getLinkAddress(String link) {

        return null;
    }

    /**
     * 2.用户数据生成与更新
     * @return
     */

    /**
     * 3.查询（支持分页与排序）
     * @return
     */

}
