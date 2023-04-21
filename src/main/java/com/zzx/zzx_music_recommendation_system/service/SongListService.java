package com.zzx.zzx_music_recommendation_system.service;

import com.zzx.zzx_music_recommendation_system.entity.SongList;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzx.zzx_music_recommendation_system.vo.AddOrUpdateSongListReqVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zzx
 * @since 2023-03-30
 */
public interface SongListService extends IService<SongList> {

    void addOrUpdateSongList(AddOrUpdateSongListReqVO reqVO);

    List<SongList> getAllSongList();

    void deleteSongList(Long songListId);

}
