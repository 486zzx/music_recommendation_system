package com.zzx.zzx_music_recommendation_system.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzx.zzx_music_recommendation_system.entity.MusicSongList;

import java.util.List;

public interface MusicSongListDao extends IService<MusicSongList> {
    /**
     * 查看已经存在的歌
     * @param songListId
     * @return
     */
    List<Long> getExistSongs(Long songListId);


}
