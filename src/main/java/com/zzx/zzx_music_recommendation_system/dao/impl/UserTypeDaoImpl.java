package com.zzx.zzx_music_recommendation_system.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzx.zzx_music_recommendation_system.dao.UserTypeDao;
import com.zzx.zzx_music_recommendation_system.entity.UserType;
import com.zzx.zzx_music_recommendation_system.mapper.UserTypeMapper;
import org.springframework.stereotype.Service;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/4/18 15:46
 */
@Service
public class UserTypeDaoImpl extends ServiceImpl<UserTypeMapper, UserType> implements UserTypeDao {
}
