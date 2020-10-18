package com.soft1851.usercenter.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 返回结果中的Jwt数据对象
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class JwtTokenRespDTO {
    /**
     * token
     */
    private String token;
    /**
     *过期时间
     */
    private Long expirationTime;
}
