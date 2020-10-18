package com.soft1851.usercenter.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录返回结果
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRespDTO {
    /**
     * 用户信息
     */
    private UserRespDTO user;
    /*火
     * token数据
     */
    private JwtTokenRespDTO token;
}
