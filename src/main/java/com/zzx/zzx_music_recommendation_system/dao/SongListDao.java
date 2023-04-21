package com.zzx.zzx_music_recommendation_system.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzx.zzx_music_recommendation_system.entity.SongList;
import com.zzx.zzx_music_recommendation_system.entity.UserInfo;

public interface SongListDao extends IService<SongList> {

    /**
     * 取当前播放歌单的songListId
     * @return
     */
    SongList getFromNowPlay();
}
