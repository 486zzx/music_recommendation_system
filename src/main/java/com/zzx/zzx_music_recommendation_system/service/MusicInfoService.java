package com.zzx.zzx_music_recommendation_system.service;

import com.zzx.zzx_music_recommendation_system.entity.MusicInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzx.zzx_music_recommendation_system.vo.MusicDetailResVO;
import com.zzx.zzx_music_recommendation_system.vo.RankResVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zzx
 * @since 2023-03-30
 */
public interface MusicInfoService extends IService<MusicInfo> {

    List<RankResVO> getRank(List<Long> musicIds);

    MusicDetailResVO musicDetail(Long musicId);
}
