package com.soft1851.contentcenter.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 兑换数据传输对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExchangeDTO {
    private Integer userId;
    private Integer shareId;
}
