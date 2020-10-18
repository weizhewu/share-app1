package com.soft1851.contentcenter.feignclient;

import com.soft1851.contentcenter.common.ResponseResult;
import com.soft1851.contentcenter.configuration.UserCenterFeignConfiguration;
import com.soft1851.contentcenter.domain.dto.ResponseDTO;
import com.soft1851.contentcenter.domain.dto.UserAddBonusDTO;
import com.soft1851.contentcenter.domain.dto.UserAddBonusMsgDTO;
import com.soft1851.contentcenter.domain.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "user-center", configuration = UserCenterFeignConfiguration.class)
public interface UserCenterFeignClient {
    /**
     * http://user-center/users/{id}
     *
     * @param id
     * @return UserDTO
     */
    @GetMapping("/users/{id}")
    ResponseDTO findUserById(@PathVariable Integer id);

    /**
     * 添加积分记录
     *
     * @param userAddBonusDTO
     * @return
     */
    @PutMapping("/users/add-bonus")
    UserDTO addBonus(@RequestBody UserAddBonusDTO userAddBonusDTO);

    /**
     * hello测试
     *
     * @return String
     */
    @GetMapping("/user/hello")
    String getHello();
}
