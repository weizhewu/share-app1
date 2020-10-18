package com.soft1851.usercenter.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.soft1851.usercenter.common.ResponseResult;
import com.soft1851.usercenter.configuration.WxConfiguration;
import com.soft1851.usercenter.domain.dto.*;
import com.soft1851.usercenter.domain.entity.Share;
import com.soft1851.usercenter.domain.entity.User;
import com.soft1851.usercenter.service.UserService;
import com.soft1851.usercenter.util.JwtOperator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class UserController {
    private final UserService userService;
    private final JwtOperator jwtOperator;
    private final WxMaService wxMaService = new WxConfiguration().wxMaService(new WxConfiguration().wxMaConfig());
//    private final WxMaService wxMaService = this.wxConfiguration.wxMaService(this.wxConfiguration.wxMaConfig());

    @GetMapping(value = "/hello")
    public String getHello() {
        log.info("我被调用了");
        return "hello user-center!";
    }

    @GetMapping(value = "/{id}")
    public ResponseDTO findUserById(@PathVariable Integer id) {
        log.info("我被请求了...");
        return this.userService.findById(id);
    }


    @PostMapping(value = "/login")
    public LoginRespDTO login(@RequestBody LoginDTO loginDTO) throws WxErrorException {
        String openId;
        //微信小程序登录，需要根据code请求openId
        if (loginDTO.getLoginCode() != null) {
            //微信服务端校验是否已经登录的结果
            WxMaJscode2SessionResult result = this.wxMaService.getUserService()
                    .getSessionInfo(loginDTO.getLoginCode());
            log.info(result.toString());
            //微信的openId，用户在微信这边的唯一标识
            openId = result.getOpenid();
        }else {
                openId = loginDTO.getOpenId();
        }
        //看用户是否注册，如果没有注册就（插入），如果已经注册就返回其信息
        User user = userService.login(loginDTO,openId);
        //颁发token
        Map<String,Object> userInfo = new HashMap<>(3);
        userInfo.put("id",user.getId());
        userInfo.put("wxNickname" ,user.getWxNickname());
        userInfo.put("role",user.getRoles());
        String token = jwtOperator.generateToken(userInfo);
        log.info("userinfo:"+userInfo);
        log.info("token:"+token);
        log.info(
                "登录成功，生成的token = "+token,"，有效期到:0",
                user.getWxNickname(),
                        token,
                        jwtOperator.getExpirationTime()
        );
        //构造返回结果
        return LoginRespDTO.builder()
                .user(UserRespDTO.builder()
                    .id(user.getId())
                    .wxNickname(user.getWxNickname())
                    .avatarUrl(user.getAvatarUrl())
                    .bonus(user.getBonus())
                    .build())
                .token(JwtTokenRespDTO
                        .builder()
                        .token(token)
                        .expirationTime(jwtOperator.getExpirationTime().getTime())
                        .build())
                .build();
    }

    @PutMapping(value = "/add-bonus")
    public User addBonus(@RequestBody UserAddBonusDTO userAddBonusDTO) {
        log.info("增减积分接口被请求了...");
        Integer userId = userAddBonusDTO.getUserId();
        userService.addBonus(
                UserAddBonusMsgDTO.builder()
                        .userId(userId)
                        .bonus(userAddBonusDTO.getBonus())
                        .description("兑换分享...")
                        .event("BUY")
                        .build()
        );
        return (User) this.userService.findById(userId).getData();
    }

    @PostMapping(value = "/signIn")
    public ResponseDTO signIn(@RequestBody UserSignInDTO userSignInDTO){
        return userService.signIn(userSignInDTO);
    }

    @GetMapping(value = "/contributions/{userId}")
    public Share contributions(@PathVariable Integer userId){
        return userService.contributions(userId);
    }
}
