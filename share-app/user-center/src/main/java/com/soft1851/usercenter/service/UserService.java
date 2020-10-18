package com.soft1851.usercenter.service;

import com.soft1851.usercenter.domain.dto.LoginDTO;
import com.soft1851.usercenter.domain.dto.ResponseDTO;
import com.soft1851.usercenter.domain.dto.UserAddBonusMsgDTO;
import com.soft1851.usercenter.domain.dto.UserSignInDTO;
import com.soft1851.usercenter.domain.entity.Share;
import com.soft1851.usercenter.domain.entity.User;


public interface UserService {
    /**
     * 根据id获得用户详情
     *
     * @param id
     * @return User
     */
    ResponseDTO findById(Integer id);

    /**
     * 添加一条积分记录
     *
     * @param userAddBonusMsgDTO
     */
    int addBonus(UserAddBonusMsgDTO userAddBonusMsgDTO);

    /**
     * 用户登录
     * @param loginDTO
     * @param openId
     * @return
     */
    User login(LoginDTO loginDTO,String openId);

    /**
     * 用户签到
     * @param signInDTO
     * @return
     */

    ResponseDTO signIn(UserSignInDTO signInDTO);

    Share contributions(Integer userId);
}
