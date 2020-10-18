package com.soft1851.contentcenter.service;


import com.github.pagehelper.PageInfo;
import com.soft1851.contentcenter.domain.dto.ExchangeDTO;
import com.soft1851.contentcenter.domain.dto.ShareAuditDTO;
import com.soft1851.contentcenter.domain.dto.ShareContributeDto;
import com.soft1851.contentcenter.domain.dto.ShareDTO;
import com.soft1851.contentcenter.domain.entity.Share;

import java.util.List;


public interface ShareService {
    /**
     * 获得分享详情
     *
     * @param id
     * @return ShareDTO
     */
    ShareDTO findById(Integer id);


    /**
     * 根据变体模糊查询
     *
     * @param title
     * @param pageNo
     * @param pageSize
     * @param userId
     * @return PageInfo<Share>
     */
    PageInfo<Share> query(String title, Integer pageNo, Integer pageSize, Integer userId);

    /**
     * 投稿
     *
     * @param shareContributeDto
     * @return
     */
    int contribute(ShareContributeDto shareContributeDto);

    /**
     * 审核投稿
     *
     * @param id
     * @param shareAuditDTO
     * @return
     */
    Share auditById(Integer id, ShareAuditDTO shareAuditDTO);

    /**
     * 积分兑换资源
     *
     * @param exchangeDTO
     * @return Share
     */
    Share exchange(ExchangeDTO exchangeDTO);

    List<Share> contributions(Integer userId);
}
