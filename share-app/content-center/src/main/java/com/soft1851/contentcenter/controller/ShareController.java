package com.soft1851.contentcenter.controller;

import com.soft1851.contentcenter.auth.CheckLogin;
import com.soft1851.contentcenter.domain.dto.ExchangeDTO;
import com.soft1851.contentcenter.domain.dto.ShareContributeDto;
import com.soft1851.contentcenter.domain.dto.ShareDTO;
import com.soft1851.contentcenter.domain.entity.Share;
import com.soft1851.contentcenter.feignclient.TestUserCenterFeignClient;
import com.soft1851.contentcenter.service.ShareService;
import com.soft1851.contentcenter.util.JwtOperator;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/shares")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(tags = "分享接口", value = "提供分享相关的Rest API")
@Slf4j
public class ShareController {
    private final ShareService shareService;
    private final JwtOperator jwtOperator;

    private final TestUserCenterFeignClient testUserCenterFeignClient;

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "查询指定id的分享详情", notes = "查询指定id的分享详情")
    public ShareDTO findById(@PathVariable Integer id) {
        return this.shareService.findById(id);
    }


    @PostMapping("/contribute")
    public int contribute(@RequestBody ShareContributeDto shareContributeDto) {
        return shareService.contribute(shareContributeDto);
    }

    @GetMapping( value = "/query")
    @ApiOperation(value ="分享列表",notes ="分享列表")
    public List<Share> query(
            @RequestParam(required = false) String title,
            @RequestParam(required = false,defaultValue = "1") Integer pageNo,
            @RequestParam(required = false,defaultValue = "10")Integer pageSize,
            @RequestHeader(value = "X-Token",required = false) String token){
        if (pageSize > 100) {
            pageSize = 100;
        }
        Integer userId = null;
        if (token!=null) {
//            Claims claims = this.jwtOperator.getClaimsFromToken("eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MywiaWF0IjoxNjAyNjA0ODkyLCJleHAiOjE2MDM4MTQ0OTJ9.4CWnj6dakAFBXi4UVCJf4KwB06jVa6i9sEEj1Fk5qdg");
            Claims claims = this.jwtOperator.getClaimsFromToken(token);

            userId = (Integer) claims.get("id");
        } else {
                log.info("没有token");
        }
            return this.shareService.query(title,pageNo,pageSize,userId).getList();
    }

    @PostMapping(value = "/exchange")
//    @CheckLogin
    public Share exchange(@RequestBody ExchangeDTO exchangeDTO) {
        log.info(">>>>>>>>>>>>");
        log.info("exchangeDTO:"+exchangeDTO);
        return this.shareService.exchange(exchangeDTO);
    }

    @GetMapping(value = "/contributions/{userId}")
    public List<Share> contributions(@PathVariable Integer userId){
        return shareService.contributions(userId);
    }
}