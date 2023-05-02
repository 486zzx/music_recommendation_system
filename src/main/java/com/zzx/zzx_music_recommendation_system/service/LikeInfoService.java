package com.zzx.zzx_music_recommendation_system.service;

import com.zzx.zzx_music_recommendation_system.entity.LikeInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzx.zzx_music_recommendation_system.entity.MusicInfo;
import com.zzx.zzx_music_recommendation_system.enums.SongListTypeEnum;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zzx
 * @since 2023-03-30
 */
public interface LikeInfoService extends IService<LikeInfo> {

    boolean updateMouthRank();

    boolean updateDayRank();

    MusicInfo saveLikeInfo(Long musicId, SongListTypeEnum songListTypeEnum);

    List<MusicInfo> saveLikeInfoList(List<Long> musicIds, SongListTypeEnum songListTypeEnum);

    MusicInfo saveDownloadLikeInfo(Long musicId, HttpServletResponse response);

    void deleteLikeInfo(Long musicId);

}
