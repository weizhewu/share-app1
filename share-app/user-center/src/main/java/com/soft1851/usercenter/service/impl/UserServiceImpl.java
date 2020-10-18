package com.soft1851.usercenter.service.impl;

import com.soft1851.usercenter.dao.BonusEventLogMapper;
import com.soft1851.usercenter.dao.ShareMapper;
import com.soft1851.usercenter.domain.dto.LoginDTO;
import com.soft1851.usercenter.domain.dto.ResponseDTO;
import com.soft1851.usercenter.domain.dto.UserAddBonusMsgDTO;
import com.soft1851.usercenter.domain.dto.UserSignInDTO;
import com.soft1851.usercenter.domain.entity.BonusEventLog;
import com.soft1851.usercenter.domain.entity.Share;
import com.soft1851.usercenter.domain.entity.User;
import com.soft1851.usercenter.dao.UserMapper;
import com.soft1851.usercenter.service.UserService;
import com.soft1851.usercenter.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final BonusEventLogMapper bonusEventLogMapper;
    private final ShareMapper shareMapper;


    @Override
    public ResponseDTO findById(Integer id) {
        return new ResponseDTO(true,"200","查询成功",this.userMapper.selectByPrimaryKey(id),1L);

    }

    @Override
    public int addBonus(UserAddBonusMsgDTO userAddBonusMsgDTO) {
        // 1. 为用户加积分
        Integer userId = userAddBonusMsgDTO.getUserId();
        Integer bonus = userAddBonusMsgDTO.getBonus();
        User user = this.userMapper.selectByPrimaryKey(userId);
        user.setBonus(user.getBonus() + bonus);
        this.userMapper.updateByPrimaryKeySelective(user);
        // 2. 记录日志到bonus_event_log表里面
        return this.bonusEventLogMapper.insert(BonusEventLog.builder()
                .userId(userId)
                .value(userAddBonusMsgDTO.getBonus())
                .event("CONTRIBUTE")
                .description("投稿加积分")
                .createTime(Timestamp.valueOf(LocalDateTime.now()))
                .build());
    }

    @Override
    public User login(LoginDTO loginDTO, String openId) {
        //先根据openId查找用户
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo( "wxId",openId);
        List<User> users = this.userMapper.selectByExample(example);
        //没找到，是新用户，直接注册
        if (users.size() ==0){
            User saveUser = User.builder()
                    .wxId(openId)
                    .avatarUrl(loginDTO.getAvatarUrl())
                    .wxNickname(loginDTO.getWxNickname())
                    .roles("user")
                    .bonus(100)
                    .createTime(new Date())
                    .updateTime(new Date())
                    .build();
            this.userMapper.insertSelective(saveUser);
            return saveUser;
        }
        return users.get(0);
    }

    @Override
    public ResponseDTO signIn(UserSignInDTO signInDTO) {
        User user = this.userMapper.selectByPrimaryKey(signInDTO.getUserId());
        if (user == null){
            throw new IllegalArgumentException("该用户不存在!");
        }
        Example example = new Example(BonusEventLog.class);
        Example.Criteria criteria = example.createCriteria();
        example.setOrderByClause("id DESC");
        criteria.andEqualTo("userId",signInDTO.getUserId());
        log.info(String.valueOf(signInDTO.getUserId()));
        criteria.andEqualTo("event","SIGN_IN");
        List<BonusEventLog> bonusEventLog = this.bonusEventLogMapper.selectByExample(example);
        BonusEventLog bonusEventLog1 = bonusEventLog.get(0);
        Date date = bonusEventLog1.getCreateTime();
        try {
            if (DateUtil.checkAllotSigin(date) == 0){
                this.bonusEventLogMapper.insert(BonusEventLog.builder()
                        .userId(signInDTO.getUserId())
                        .event("SIGN_IN")
                        .value(20)
                        .description("签到加积分")
                        .createTime(new Date())
                        .build());
                user.setBonus(user.getBonus()+20);
                this.userMapper.updateByPrimaryKey(user);
                return new ResponseDTO(true,"200","签到成功",user.getWxNickname()+"用户签到成功",1L);
            }
            else if (DateUtil.checkAllotSigin(date) == 1){
                return new ResponseDTO(false,"201","签到失败",user.getWxNickname()+"今天已经签到过了",1L);
            }
            else if (DateUtil.checkAllotSigin(date) == 2){
                return new ResponseDTO(false,"202","签到失败",user.getWxNickname()+"用户，今天数据错乱了",1L);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseDTO(true,"200","签到成功",user.getWxNickname()+"签到成功",1L);
    }

    @Override
    public Share contributions(Integer userId) {
        Example example = new Example(Share.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",userId);
        return this.shareMapper.selectByPrimaryKey(example);
    }
}
