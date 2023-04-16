package com.zzx.zzx_music_recommendation_system.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzx.zzx_music_recommendation_system.constant.StringConstants;
import com.zzx.zzx_music_recommendation_system.dao.UserInfoDao;
import com.zzx.zzx_music_recommendation_system.entity.UserInfo;
import com.zzx.zzx_music_recommendation_system.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/4/14 20:04
 */
@Service
public class UserInfoDaoImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoDao {

    @Autowired
    private UserInfoMapper userInfoMapper;


    @Override
    public boolean isEmailExist(String email) {
        UserInfo userInfo = userInfoMapper.selectOne(new QueryWrapper<UserInfo>().lambda()
                .eq(UserInfo::getUserEmail, email)
                .apply(StringConstants.LIMIT_1));
        return Objects.nonNull(userInfo);
    }

    @Override
    public UserInfo isUserExist(String userEmail, String userPassword) {
        UserInfo userInfo = userInfoMapper.selectOne(new QueryWrapper<UserInfo>().lambda()
                .eq(UserInfo::getUserEmail, userEmail)
                .eq(UserInfo::getUserPassword, userPassword)
                .apply(StringConstants.LIMIT_1));
        return userInfo;
    }
}
