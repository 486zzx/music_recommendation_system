package com.zzx.zzx_music_recommendation_system.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzx.zzx_music_recommendation_system.constant.StringConstants;
import com.zzx.zzx_music_recommendation_system.dao.SongListDao;
import com.zzx.zzx_music_recommendation_system.dao.UserInfoDao;
import com.zzx.zzx_music_recommendation_system.entity.SongList;
import com.zzx.zzx_music_recommendation_system.entity.UserInfo;
import com.zzx.zzx_music_recommendation_system.enums.SongListTypeEnum;
import com.zzx.zzx_music_recommendation_system.login.UserInfoUtil;
import com.zzx.zzx_music_recommendation_system.mapper.SongListMapper;
import com.zzx.zzx_music_recommendation_system.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/4/16 10:56
 */
@Service
public class SongListDaoImpl extends ServiceImpl<SongListMapper, SongList> implements SongListDao {

    @Autowired
    private SongListMapper songListMapper;

    @Override
    public SongList getFromNowPlay() {
        return getOne(new QueryWrapper<SongList>().lambda()
                .eq(SongList::getUserId, UserInfoUtil.getUserId())
                .eq(SongList::getSongListType, SongListTypeEnum.NOW_PLAY.getCode())
                .last(StringConstants.LIMIT_1));
    }
}
