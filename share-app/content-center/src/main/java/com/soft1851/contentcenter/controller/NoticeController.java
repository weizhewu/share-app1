package com.soft1851.contentcenter.controller;

import com.soft1851.contentcenter.domain.entity.Notice;
import com.soft1851.contentcenter.service.NoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping(value = "/notices")
@Api(tags = "公共接口", value = "提供公共相关的Rest API")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NoticeController {
    private final NoticeService noticeService;

    @GetMapping(value = "/latest")
    @ApiOperation(value = "查询最新一条公告", notes = "查询最新一条公告")
    public Notice getTopNotice(HttpServletResponse httpServletResponse) {
        httpServletResponse.setHeader("Access-Control-Allow-Origin","*");
        httpServletResponse.setHeader("Access-Control-Allow-Methods","POST");
        httpServletResponse.setHeader("Access-Control-Allow-Headers","Access-Control");
        httpServletResponse.setHeader("Allow","POST");
        return this.noticeService.getLatest();
    }

}
