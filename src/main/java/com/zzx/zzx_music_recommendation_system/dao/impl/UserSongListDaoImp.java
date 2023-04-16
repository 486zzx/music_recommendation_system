package com.zzx.zzx_music_recommendation_system.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzx.zzx_music_recommendation_system.dao.UserInfoDao;
import com.zzx.zzx_music_recommendation_system.dao.UserSongListDao;
import com.zzx.zzx_music_recommendation_system.entity.UserInfo;
import com.zzx.zzx_music_recommendation_system.entity.UserSongList;
import com.zzx.zzx_music_recommendation_system.mapper.UserInfoMapper;
import com.zzx.zzx_music_recommendation_system.mapper.UserSongListMapper;
import org.springframework.stereotype.Service;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/4/16 10:57
 */
@Service
public class UserSongListDaoImp extends ServiceImpl<UserSongListMapper, UserSongList> implements UserSongListDao {
}
