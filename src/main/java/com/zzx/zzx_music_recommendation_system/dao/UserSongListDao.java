package com.zzx.zzx_music_recommendation_system.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzx.zzx_music_recommendation_system.entity.UserSongList;

import java.util.Map;

public interface UserSongListDao extends IService<UserSongList> {

    Map<Long, Long> getHotSongList();
}
