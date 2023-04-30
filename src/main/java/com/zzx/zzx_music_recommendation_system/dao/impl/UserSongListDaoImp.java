package com.zzx.zzx_music_recommendation_system.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzx.zzx_music_recommendation_system.dao.UserSongListDao;
import com.zzx.zzx_music_recommendation_system.entity.UserSongList;
import com.zzx.zzx_music_recommendation_system.mapper.UserSongListMapper;
import com.zzx.zzx_music_recommendation_system.vo.GetHotSongListResVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/4/16 10:57
 */
@Service
public class UserSongListDaoImp extends ServiceImpl<UserSongListMapper, UserSongList> implements UserSongListDao {
    @Autowired
    private UserSongListMapper userSongListMapper;

    @Override
    public Map<Long, Long> getHotSongList() {
        return userSongListMapper.getHotSongList().stream()
                .collect(Collectors.toMap(GetHotSongListResVO::getSongListId, GetHotSongListResVO::getCollectNum));
    }
}
